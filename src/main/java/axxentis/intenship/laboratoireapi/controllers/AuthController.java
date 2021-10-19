package axxentis.intenship.laboratoireapi.controllers;


import axxentis.intenship.laboratoireapi.advice.CustumMessage;
import axxentis.intenship.laboratoireapi.advice.CustumStatus;
import axxentis.intenship.laboratoireapi.config.PasswordValidator;
import axxentis.intenship.laboratoireapi.config.Tools;
import axxentis.intenship.laboratoireapi.dto.request.*;
import axxentis.intenship.laboratoireapi.dto.responses.EmployeeResponseDto;
import axxentis.intenship.laboratoireapi.dto.responses.CustumApiResponse;
import axxentis.intenship.laboratoireapi.dto.responses.TokenRefreshResponse;
import axxentis.intenship.laboratoireapi.dto.responses.UsernameForgotResponseDto;
import axxentis.intenship.laboratoireapi.entities.*;
import axxentis.intenship.laboratoireapi.exception.ConflictException;
import axxentis.intenship.laboratoireapi.exception.ResourceNotFoundException;
import axxentis.intenship.laboratoireapi.security.jwt.JwtUtils;
import axxentis.intenship.laboratoireapi.security.jwt.exception.TokenRefreshException;
import axxentis.intenship.laboratoireapi.security.services.RefreshTokenService;
import axxentis.intenship.laboratoireapi.services.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Validated
@AllArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final EmployeeService employeeService;
    private final CountryService countryService;
    private final CityService cityService;
    private final DepartmentService departmentService;
    private final PhoneNumberService phoneNumberService;
    private final PrivilegeService privilegeService;
    private final ProfilService profilService;
    private final AutorisationService autorisationService;
    private final EmployeeProfilService employeeProfilService;
    private final CurrentUserService currentUserService;
    private final RefreshTokenService refreshTokenService;
    private final VerifyEmployeeService verifyEmployeeService;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;



    Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private String message;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDto loginDto, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.trace("entering authenticateUser() method");
        Authentication authentication = null;
        System.out.println("ici 1");
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        } catch (Exception exception) {
            System.out.println("exception gen: " + exception.getMessage());
            message = CustumMessage.LOGIN_MOTPASSE_INCORRECT;
            LOGGER.error(message);
            throw new ResourceNotFoundException(message);
        }
        System.out.println("ici 2");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Employee currentEmployee = currentUserService.information();

        List<String> authorizations = currentUserService.getAuthorizations();
        String accessToken = currentUserService.getToken();
        String refreshToken = currentUserService.getRefreshToken();
        Optional<RefreshToken> optionalRefreshToken = refreshTokenService.findByToken(refreshToken);
        optionalRefreshToken.ifPresent(token -> currentEmployee.setDateLastConnection(token.getCreateAt()));
        currentEmployee.setOnLine(true);
        employeeService.updateEmployee(currentEmployee);

        LOGGER.info(CustumMessage.CONNEXION_CONTACT.concat(" ") + currentEmployee.getUsername().concat(" ") + CustumMessage.EFFECTUE_AVEC_SUCCESS);
        EmployeeResponseDto employeeDto = mapEmployeeToEmployeeResponseDTO(currentEmployee);
        return ResponseEntity.ok(new CustumApiResponse(employeeDto, accessToken, refreshToken, "Opération réalisée avec succès", HttpStatus.OK));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupDto signUpDto) {
        LOGGER.trace("entering registerUser() method");
        String message;
        System.out.println("Je suis dans la methiode pour l'enregistrement d'un utilisateur");

        if (!PasswordValidator.isValid(signUpDto.getPassword())) {
            message = CustumMessage.CRITERE_ROBUSTESSE_PASSWORD;
            LOGGER.warn(message);
            return ResponseEntity.ok(
                    new CustumApiResponse(false, message));
        }

        if (!signUpDto.getPassword().equals(signUpDto.getConfirmationPassword())) {
            message = CustumMessage.PASSWORD_NON_IDENTIQUE;
            LOGGER.warn(message);
            return ResponseEntity.ok(
                    new CustumApiResponse(false, message));
        }

        if (employeeService.existsEmployeeByUsername(signUpDto.getUsername())) {
            message = CustumMessage.USERNAME_TAKEN;
            LOGGER.warn(message);
            throw new ConflictException(message);
        }

        if (employeeService.existsEmployeeByEmail(signUpDto.getEmail())) {
            message = CustumMessage.EMAIL_TAKEN;
            LOGGER.warn(message);
            throw new ConflictException(message);
        }


        List<PhoneNumberDto> myPhoneNumbers = signUpDto.getPhoneNumbers();
        for (PhoneNumberDto phoneDto : myPhoneNumbers) {
            if (phoneDto.getNumber().isEmpty()) {
                message = CustumMessage.NUMERO_VIDE;
                LOGGER.error(message);
                return ResponseEntity.ok(new CustumApiResponse(false, message));
            }
            Country aCountry;
            Optional<Country> optionalCountry = countryService.findCountryByIsoCode(phoneDto.getCountry().getIsoCode());
            if (optionalCountry.isEmpty()) {
                Country newCountry = new Country();
                newCountry.setIsoCode(phoneDto.getCountry().getIsoCode());
                newCountry.setIndicative(phoneDto.getCountry().getIndicative());
                newCountry.setLibelle(phoneDto.getCountry().getLibelle());
                countryService.addCountry(newCountry);
                aCountry = newCountry;
            } else {
                aCountry = optionalCountry.get();
            }
            boolean alreadyExist = phoneNumberService.existPhoneNumberByNumberAndCountry(phoneDto.getNumber(), aCountry);
            if (alreadyExist) {
                message = CustumMessage.NUMERO_EXISTANT;
                LOGGER.error(message);
                throw new ConflictException(message);
            }
        }

        List<Department> departments = departmentService.findAllDepartment();
        if (departments.isEmpty()) {
            Department firstDepartement = new Department();
            firstDepartement.setLibelle(Tools.toCapitalize("ABU"));
            firstDepartement.setDescription(Tools.toCapitalize("Application Buisness Unit"));
            departmentService.addDepartment(firstDepartement);

            Department secondDepartment = new Department();
            secondDepartment.setLibelle(Tools.toCapitalize("HR"));
            secondDepartment.setDescription(Tools.toCapitalize("Human Ressource"));
            departmentService.addDepartment(secondDepartment);
        }
        Optional<Department> optionalDepartment = departmentService.findDepartmentById(signUpDto.getDepartementId());
        if (optionalDepartment.isEmpty()) {
            message = CustumMessage.SELECTIONNER_DEPARTEMENT;
            LOGGER.warn(message);
            throw new ResourceNotFoundException(message);
        }


        List<Country> countries = countryService.findAllCountry();
        if (countries.isEmpty()) {
            Country country = new Country();
            country.setLibelle(Tools.toCapitalize("Cameroun"));
            country.setIndicative(Tools.toCapitalize("+237"));
            country.setIsoCode(Tools.upperCase("CM"));
            countryService.addCountry(country);
        }

        List<City> cities = cityService.findAllCity();
        if (cities.isEmpty()) {
            City firstcity = new City();
            firstcity.setLibelle(Tools.toCapitalize("Yaoundé"));
            firstcity.setCountry(countryService.findCountryById(1L).get());
            cityService.addCity(firstcity);
            City secondcity = new City();
            secondcity.setLibelle(Tools.toCapitalize("Douala"));
            secondcity.setCountry(countryService.findCountryById(1L).get());
            cityService.addCity(secondcity);
        }
        Optional<City> optionalCity = cityService.findCityById(signUpDto.getCityId());

        if (optionalCity.isEmpty()) {
            message = CustumMessage.SELECTIONNER_VILLE;
            LOGGER.warn(message);
            throw new ResourceNotFoundException(message);
        }

        List<Profil> profils = profilService.findAllProfil();
        Optional<Privilege> optionalPrivilege = Optional.empty();
        if (profils.isEmpty()) {
            Profil profil = new Profil();
            profil.setLibelle(Tools.upperCase("USER"));
            profil.setIsInitialized(true);
            profilService.addProfil(profil);
            List<Privilege> privileges = privilegeService.findAllPrivilege();
            if (privileges.isEmpty()) {
                Privilege privilege = new Privilege();
                privilege.setLibelle(Tools.upperCase("UPDATE_CONTACT_PREFERENCE"));
                privilegeService.addPrivilege(privilege);
            } else {
                optionalPrivilege = privilegeService.findPrivilegeByLibelle("UPDATE_CONTACT_PREFERENCE");
            }
        }
        Optional<Profil> optionalProfil = profilService.findProfilByLibelle("USER");
        optionalPrivilege = privilegeService.findPrivilegeByLibelle("UPDATE_CONTACT_PREFERENCE");

        Autorisation autorisation = new Autorisation();
        autorisation.setPrivilege(optionalPrivilege.get());
        autorisation.setProfil(optionalProfil.get());
        autorisation.setStatut(true);
        autorisationService.saveAutorisation(autorisation);

        // Create new user's account
        Employee employee = new Employee();
        if (Tools.isValidEMAIL(signUpDto.getEmail())) {
            employee.setEmail(Tools.lowerCase(signUpDto.getEmail()));
        } else {
            message = CustumMessage.EMAIL_INCORRECTE;
            LOGGER.error(message);
            return ResponseEntity.ok(
                    new CustumApiResponse(false, message));
        }

        employee.setLastName(Tools.upperCase(signUpDto.getLastName()));
        employee.setFirstName(Tools.toCapitalize(signUpDto.getFirstName()));
        employee.setUsername(Tools.lowerCase(signUpDto.getUsername()));

        employee.setPassword(encoder.encode(signUpDto.getPassword()));
        employee.setCity(optionalCity.get());
        employee.setDepartment(optionalDepartment.get());
        employee.setStatut(true);
        employeeService.addEmployee(employee);

        EmployeeProfil employeeProfil = new EmployeeProfil();
        employeeProfil.setEmployee(employee);
        employeeProfil.setProfil(optionalProfil.get());
        employeeProfilService.saveEmployeeProfil(employeeProfil);
        for (PhoneNumberDto myPhoneNumber : myPhoneNumbers) {
            Optional<Country> optionalCountry = countryService.findCountryByIsoCode(myPhoneNumber.getCountry().getIsoCode());
            PhoneNumber phoneNumber = new PhoneNumber();
            phoneNumber.setCountry(optionalCountry.get());
            phoneNumber.setNumber(myPhoneNumber.getNumber());
            phoneNumber.setIsPrincipal(myPhoneNumber.getIsPrincipal());
            phoneNumber.setEmployee(employee);
            phoneNumberService.addPhoneNumber(phoneNumber);
        }
        message = CustumMessage.OPERATION_SUCCESS;
        LOGGER.info(message);
        return ResponseEntity.ok(new CustumApiResponse(true, message, CustumStatus.OK));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshDto request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getEmployee)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@Valid @RequestBody LogOutDto logOutDto, HttpServletRequest request) {
        LOGGER.trace("entering logoutUser() method");
        Optional<Employee> employeeToDisconnect = employeeService.findEmployeeByIdAndOnLineIsTrue(logOutDto.getEmployeeId());
        String message;
        if (employeeToDisconnect.isPresent()) {
            refreshTokenService.employeeTokenExpiration(employeeToDisconnect.get());
            employeeToDisconnect.get().setOnLine(false);
            employeeService.updateEmployee(employeeToDisconnect.get());
            message = CustumMessage.DECONNECTION;
            String logMessage = message;
            LOGGER.info(logMessage);
            return ResponseEntity.ok(new CustumApiResponse(true, message, HttpServletResponse.SC_OK));
        } else {
            message = CustumMessage.CONTACT_HORS_LIGNE;
            LOGGER.error(message);
            throw new ResourceNotFoundException(message);
        }

    }


    /**
     * Generation d'un code de verification
     * pour le processus de restoration du mdp
     *
     * @param passwordForgotDto
     * @return
     */
    @PostMapping(value = "/forgotpassword/generatecode")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> forgotPasswordEmployee(@Valid @RequestBody PasswordForgotDto passwordForgotDto) {
        LOGGER.trace("entering forgotPasswordEmployee() method");
        String message;
        Optional<Employee> optionalEmployee = employeeService.findEmployeeByUsername(passwordForgotDto.getUsername());
        if (optionalEmployee.isPresent()) {
            Employee currentUser = optionalEmployee.get();
            if (optionalEmployee.get().getStatut()) {
                message = CustumMessage.COMPTE_DESACTIVE;
                LOGGER.warn(message);
                throw new ResourceNotFoundException(message);
            }
            VerifyEmployee verifyEmployee = new VerifyEmployee();
            Date emissionDate = new Date(); // emissionDate == current time
            Date expirationDate = new Date(emissionDate.getTime() + TimeUnit.MINUTES.toMillis(5)); // Add 5 minutes
            String generatedCode = generateNewCode();


            if (passwordForgotDto.getOptionRecuperation().equalsIgnoreCase("SMS")) {
                if (passwordForgotDto.getPhoneNumber().isBlank() || passwordForgotDto.getPhoneNumber().isEmpty()) {
                    message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE;
                    LOGGER.warn(message);
                    return ResponseEntity.ok(
                            new CustumApiResponse(false, message));
                }
                Optional<PhoneNumber> optionalPhoneNumber = phoneNumberService.findNumberTelePhonePrincipaleByEmployee(optionalEmployee.get());
                if (optionalPhoneNumber.isEmpty()) {
                    message = CustumMessage.NUMERO_PRINCIPAL_VIDE;
                    LOGGER.warn(message);
                    throw new ResourceNotFoundException(message);
                }
                if (!passwordForgotDto.getPhoneNumber().equals(optionalPhoneNumber.get().getNumber())) {
                    message = CustumMessage.NUMERO_ERRONE;
                    LOGGER.warn(message);
                    return ResponseEntity.ok(
                            new CustumApiResponse(false, message));
                }
                verifyEmployee.setEmployee(optionalEmployee.get());
                verifyEmployee.setCode(generatedCode);
                verifyEmployee.setStatut(false);
                verifyEmployee.setDateEmission(emissionDate);
                verifyEmployee.setDateExpiration(expirationDate);
                verifyEmployee.setTelephone(optionalPhoneNumber.get().getNumber());
                verifyEmployeeService.saveVerificationEmployee(verifyEmployee);
            }
            if (passwordForgotDto.getOptionRecuperation().equalsIgnoreCase("EMAIL")) {
                if (passwordForgotDto.getEmail().isBlank() || passwordForgotDto.getEmail().isEmpty()) {
                    message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE;
                    LOGGER.warn(message);
                    return ResponseEntity.ok(
                            new CustumApiResponse(false, message));
                }

                if (optionalEmployee.get().getEmail().isEmpty()) {
                    message = CustumMessage.EMAIL_VIDE;
                    LOGGER.warn(message);
                    return ResponseEntity.ok(
                            new CustumApiResponse(false, message));
                }

                if (!passwordForgotDto.getEmail().equals(optionalEmployee.get().getEmail())) {
                    message = CustumMessage.EMAIL_ERRONE;
                    LOGGER.warn(message);
                    return ResponseEntity.ok(
                            new CustumApiResponse(false, message));
                }
                verifyEmployee.setEmployee(currentUser);
                verifyEmployee.setCode(generatedCode);
                verifyEmployee.setStatut(false);
                verifyEmployee.setDateEmission(emissionDate);
                verifyEmployee.setDateExpiration(expirationDate);
                verifyEmployee.setEmail(optionalEmployee.get().getEmail());
                verifyEmployeeService.saveVerificationEmployee(verifyEmployee);
            }
            int nbreGenerationCode = 0;
            if (currentUser.getNombreGenerationCode() == null) {
                nbreGenerationCode = 1;
            } else {
                nbreGenerationCode = currentUser.getNombreGenerationCode() + 1;
            }
            currentUser.setNombreGenerationCode(nbreGenerationCode);
            employeeService.updateEmployee(currentUser);
            message = CustumMessage.OPERATION_SUCCESS;
            LOGGER.info(message);
            return ResponseEntity.ok(new CustumApiResponse(message, CustumStatus.OK));
        } else {
            message = CustumMessage.CONTACT_INEXISTANT;
            LOGGER.warn(message);
            return ResponseEntity.ok(
                    new CustumApiResponse(false, message));
        }

    }

    @PostMapping(value = "/forgotpassword/validatecode")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> forgotPasswordEmployeeCodeValidation(@Valid @RequestBody PasswordReinitDto passwordReinitDto) {
        LOGGER.trace("entering forgotPasswordEmployeeCodeValidation() method");
        String message;
        Optional<VerifyEmployee> optionalVerifyEmployee = verifyEmployeeService.findVerificationEmployeeByCode(passwordReinitDto.getCodeRecu());
        if (optionalVerifyEmployee.isPresent()) {
            Employee currentUser = optionalVerifyEmployee.get().getEmployee();
            int nbreVerifyCode = 0;
            if (currentUser.getNombreVerifyCode() == null) {
                nbreVerifyCode = 1;
            } else {
                nbreVerifyCode = currentUser.getNombreVerifyCode() + 1;
                if (nbreVerifyCode >= 3) {
                    message = CustumMessage.VERIFICATION_SUSPENDUE;
                    Employee employee = optionalVerifyEmployee.get().getEmployee();
                    Date dateOperation = new Date(); // dateOperation == current time
                    Date dateFinSuspension = new Date(dateOperation.getTime() + TimeUnit.MINUTES.toMillis(15)); // Add 15 minutes
                    employee.setNombreVerifyCode(0);
                    employeeService.updateEmployee(employee);
                    LOGGER.warn(message);
                    return ResponseEntity.ok(
                            new CustumApiResponse(false, message));
                }
            }
            currentUser.setNombreVerifyCode(nbreVerifyCode);
            employeeService.updateEmployee(currentUser);
            if (optionalVerifyEmployee.get().getDateExpiration().compareTo(new Date()) < 0) {
                message = CustumMessage.CODE_EXPIRER;
                LOGGER.warn(message);
                return ResponseEntity.ok(
                        new CustumApiResponse(false, message));
            }
            if (!PasswordValidator.isValid(passwordReinitDto.getNouveauPassword())) {
                message = CustumMessage.CRITERE_ROBUSTESSE_PASSWORD;
                LOGGER.warn(message);
                return ResponseEntity.ok(
                        new CustumApiResponse(false, message));
            }

            if (!passwordReinitDto.getNouveauPassword().equals(passwordReinitDto.getConfirmationNouveauPassword())) {
                message = CustumMessage.PASSWORD_NON_IDENTIQUE;
                LOGGER.warn(message);
                return ResponseEntity.ok(
                        new CustumApiResponse(false, message));
            }
            currentUser.setPassword(encoder.encode(passwordReinitDto.getNouveauPassword()));
            employeeService.updateEmployee(currentUser);
            message = CustumMessage.OPERATION_SUCCESS;
            LOGGER.info(message);
            return ResponseEntity.ok(new CustumApiResponse(message, CustumStatus.OK));
        } else {
            message = CustumMessage.CODE_ERRONE;
            LOGGER.warn(message);
            return ResponseEntity.ok(
                    new CustumApiResponse(false, message));
        }
    }


    public String generateNewCode() {
        String newCode = Tools.generateCODE(5);
        Optional<VerifyEmployee> optionalVerifyEmployee = verifyEmployeeService.findVerificationEmployeeByCode(newCode);
        if (optionalVerifyEmployee.isPresent()) {
            generateNewCode();
        }
        return newCode;
    }


    /**
     * Transforme un Employee en UsernameForgotResponseDto
     *
     * @param employee
     * @return
     */
    private UsernameForgotResponseDto mapEmployeeToUsernameForgotResponseDTO(Employee employee) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(employee, UsernameForgotResponseDto.class);
    }

    /**
     * Transforme un Employee en EmployeeDTO
     *
     * @param employee
     * @return
     */
    private EmployeeResponseDto mapEmployeeToEmployeeResponseDTO(Employee employee) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(employee, EmployeeResponseDto.class);
    }

}

