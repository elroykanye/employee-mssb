package axxentis.intenship.laboratoireapi.controllers;

import axxentis.intenship.laboratoireapi.filters.CustomAuthenticationFilter;
import axxentis.intenship.laboratoireapi.serviceImplementations.EmployeeServiceImplementation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
@Validated
@Slf4j
public class HomeController {
    CustomAuthenticationFilter customAuthenticationFilter;
    AuthenticationManager authenticationManager;
    EmployeeServiceImplementation employeeServiceImplementation;
       PasswordEncoder passwordEncoder;


}
