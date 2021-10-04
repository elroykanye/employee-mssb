package axxentis.intenship.laboratoireapi.serviceImplementations;

import axxentis.intenship.laboratoireapi.entities.Employee;
import axxentis.intenship.laboratoireapi.entities.VerificationToken;
import axxentis.intenship.laboratoireapi.payload.dto.form.LoginForm;
import axxentis.intenship.laboratoireapi.repositories.EmployeeRepository;
import axxentis.intenship.laboratoireapi.repositories.VerificationTokenRepository;
import axxentis.intenship.laboratoireapi.security.JwtResponse;
import axxentis.intenship.laboratoireapi.security.JwtTokenUtil;
import axxentis.intenship.laboratoireapi.services.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@AllArgsConstructor
@Slf4j
public class AuthServiceImplementation implements AuthService {

    private PasswordEncoder passwordEncoder;
    private EmployeeRepository employeeRepository;
    private VerificationTokenRepository verificationTokenRepository;
    private AuthenticationManager authenticationManager;

    private final String BASE_URL = "http://localhost:8008/";
    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;

    /**
     * implementation for user verification
     * @param verificationTokenValue: token value
     * @param email: username concerned
     * @return: logic state of user verification
     */

    @Override
    @Transactional
    public boolean verifyAccount(String verificationTokenValue, String email) {
        AtomicBoolean verTokenValid = new AtomicBoolean(false);

        // extract optional verification token from the db
        Optional<VerificationToken> verificationTokenOpt = verificationTokenRepository.findByTokenValue(verificationTokenValue);

        verificationTokenOpt.ifPresent(verificationToken -> {
            // set the account valid value based on the verification
            verTokenValid.set(verificationToken.getEmail().equals(email));

            // get the Optional value for the employee from the userRep object
            Employee employeeToBeVerified = employeeRepository.findByEmail(verificationToken.getEmail());

            // if employee is present, perform the action declared,
            // pass call to update the employee's verification
            employeeToBeVerified.getId(employee -> {
                boolean employeeUpdated = false;
                if(verTokenValid.get()) {
                    employeeUpdated = updateEmployeeVerification(employee, verTokenValid.get());
                }
                // delete the verification token if the operation was successful

                if(employeeUpdated) verificationTokenRepository.deleteById(verificationToken.getTokenId());
            });
        });
        return verTokenValid.get();
    }

    /**
     * implementation for user authentication
     * @param loginForm: request body for this method
     * @return: response entity containing status and jwtResponse body
     * @throws Exception: in case authentication is unsuccessful
     */
    public ResponseEntity<?> loginEmployee(LoginForm loginForm) throws Exception {

        authenticateEmployee(loginForm);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginForm.getEmail());

        final String token = jwtTokenUtil.generateToke(userDetails);
        return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
    }

    /* ************* helper methods for this class ************* */
    private void authenticateEmployee(LoginForm loginForm) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginForm.getEmail(), loginForm.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new Exception("AUTH FAILED", e);
        }
    }

    private String generateVerificationToken(Employee newEmployee) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = VerificationToken.builder() // build the verification token object
                .tokenValue(token)
                .email(newEmployee.getEmail())
                .build();
        verificationTokenRepository.save(verificationToken); // save to the table
        return token;
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private boolean updateEmployeeVerification(Employee employee, Boolean verified) {
        boolean verSuccess = false;
        try {
            employee.setEmployeeVerified(verified); // update the value of employee enabled
            employeeRepository.save(employee); // saved the updated employee
            verSuccess = true;
        } catch (Exception e) {
            log.error("Employee verification update failed");
        }
        return verSuccess;
    }

}
