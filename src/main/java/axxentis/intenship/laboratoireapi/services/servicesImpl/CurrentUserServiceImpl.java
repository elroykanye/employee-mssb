package axxentis.intenship.laboratoireapi.services.servicesImpl;


import axxentis.intenship.laboratoireapi.entities.Employee;
import axxentis.intenship.laboratoireapi.entities.RefreshToken;
import axxentis.intenship.laboratoireapi.repositories.EmployeeRepository;
import axxentis.intenship.laboratoireapi.security.jwt.JwtUtils;
import axxentis.intenship.laboratoireapi.security.services.EmployeeDetailsImpl;
import axxentis.intenship.laboratoireapi.security.services.RefreshTokenService;
import axxentis.intenship.laboratoireapi.services.CurrentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Transactional
@Service
public class CurrentUserServiceImpl implements CurrentUserService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    RefreshTokenService refreshTokenService;

    @Override
    public Employee information() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmployeeDetailsImpl employeeDetails = (EmployeeDetailsImpl) authentication.getPrincipal();
        return employeeRepository.findById(employeeDetails.getId()).orElse(new Employee());
    }

    @Override
    public String getToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmployeeDetailsImpl employeeDetails = (EmployeeDetailsImpl) authentication.getPrincipal();
        return jwtUtils.generateJwtToken(employeeDetails);
    }

    @Override
    public String getRefreshToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmployeeDetailsImpl employeeDetails = (EmployeeDetailsImpl) authentication.getPrincipal();
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(employeeDetails.getId());
        return refreshToken.getToken();
    }

    @Override
    public List<String> getAuthorizations() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmployeeDetailsImpl employeeDetails = (EmployeeDetailsImpl) authentication.getPrincipal();
        return employeeDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }
}
