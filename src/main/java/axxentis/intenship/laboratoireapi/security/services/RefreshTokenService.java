package axxentis.intenship.laboratoireapi.security.services;


import axxentis.intenship.laboratoireapi.entities.Employee;
import axxentis.intenship.laboratoireapi.entities.RefreshToken;
import axxentis.intenship.laboratoireapi.repositories.EmployeeRepository;
import axxentis.intenship.laboratoireapi.repositories.RefreshTokenRepository;
import axxentis.intenship.laboratoireapi.security.jwt.exception.TokenRefreshException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@Service
public class RefreshTokenService {
    @Value("${sh21.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Long employeeId) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        RefreshToken refreshToken = null;
        if(optionalEmployee.isPresent()){
            Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findRefreshTokenByEmployee(optionalEmployee.get());
            if(optionalRefreshToken.isPresent()){
                optionalRefreshToken.get().setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
                optionalRefreshToken.get().setToken(UUID.randomUUID().toString());
                refreshToken = optionalRefreshToken.get();
            }else {
                refreshToken = new RefreshToken();
                refreshToken.setEmployee(optionalEmployee.get());
                refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
                refreshToken.setToken(UUID.randomUUID().toString());
                refreshToken = refreshTokenRepository.save(refreshToken);
            }
        }
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @Transactional
    public void deleteByEmployeeId(Long employeeId) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if(optionalEmployee.isPresent()){
            optionalEmployee.get().setOnLine(false);
            refreshTokenRepository.deleteByEmployee(optionalEmployee.get());
        }
    }

    public void employeeTokenExpiration(Employee employee){
        employee.setOnLine(false);
        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findRefreshTokenByEmployee(employee);
        optionalRefreshToken.ifPresent(refreshToken -> {
            refreshToken.setExpiryDate(Instant.now());
            refreshToken.setUpdateAt(LocalDateTime.now());
        });
    }
}

