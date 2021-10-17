package axxentis.intenship.laboratoireapi.controllers;

import axxentis.intenship.laboratoireapi.advice.CustumMessage;
import axxentis.intenship.laboratoireapi.advice.CustumStatus;
import axxentis.intenship.laboratoireapi.config.PasswordValidator;
import axxentis.intenship.laboratoireapi.config.Tools;
import axxentis.intenship.laboratoireapi.dto.request.*;
import axxentis.intenship.laboratoireapi.dto.responses.CustumApiResponse;
import axxentis.intenship.laboratoireapi.dto.responses.EmployeeResponseDto;
import axxentis.intenship.laboratoireapi.entities.*;
import axxentis.intenship.laboratoireapi.exception.ConflictException;
import axxentis.intenship.laboratoireapi.exception.ResourceNotFoundException;
import axxentis.intenship.laboratoireapi.services.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/intenship")
@AllArgsConstructor
@Validated
public class EmployeeController {
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final EmployeeService employeeService;
    private final PasswordEncoder encoder;
    private final CurrentUserService currentUserService;
    private final PhoneNumberService phoneNumberService;
    private final ProfilService profilService;
    private final PrivilegeService privilegeService;
    private final EmployeeProfilService employeeProfilService;
    private final CountryService countryService;
    private final CityService cityService;
    private final DepartmentService departmentService;
    private final AutorisationService autorisationService;


    public Employee currentUser;
    public String message;


    @GetMapping(value = "/employees")
    @PreAuthorize("hasAuthority('LIST_CONTACT') or hasAuthority('UPDATE_CONTACT_PREFERENCE')")
    public ResponseEntity<?> getAllEmployee() {
        LOGGER.trace("entering getAllEmployee() method");
        String message;
        List<Employee> employees = employeeService.findAllEmployee();
        if (!CollectionUtils.isEmpty(employees)) {
            List<EmployeeDto> lesEmployees = mapList(employees, EmployeeDto.class);
            message = CustumMessage.OPERATION_SUCCESS;
            LOGGER.info(message);
            return ResponseEntity.ok(new CustumApiResponse(lesEmployees, message, CustumStatus.OK));
        } else {
            message = CustumMessage.LISTE_VIDE;
            LOGGER.info(message);
            return ResponseEntity.ok(new CustumApiResponse(new ArrayList<>(), message, CustumStatus.NO_CONTENT));
        }
    }

    @PostMapping(value = "/employees/criterials")
    @PreAuthorize("hasAuthority('LIST_CONTACT')")
    public ResponseEntity<?> getAllEmployeeByCriterials(@Valid @RequestBody EmployeeCriteriaDto employeeCriteriaDto) {
        LOGGER.trace("entering getAllEmployeeByCriterials() method");
        String message;
        List<Employee> employees = employeeService.searchByLikeCriteria(employeeCriteriaDto.getSearch(), employeeCriteriaDto.getLastName(), employeeCriteriaDto.getFirstName(), employeeCriteriaDto.getStatus(), employeeCriteriaDto.getPhoneNumber(), employeeCriteriaDto.getDepartment());
        System.out.println("Size of date: " + employees.size());
        if (employees.size() > 0) {
            List<EmployeeDto> lesEmployees = mapList(employees, EmployeeDto.class);
            message = CustumMessage.OPERATION_SUCCESS;
            LOGGER.info(message);
            return ResponseEntity.ok(new CustumApiResponse(lesEmployees, message, CustumStatus.OK));
        } else {
            message = CustumMessage.LISTE_VIDE;
            LOGGER.info(message);
            return ResponseEntity.ok(new CustumApiResponse(new ArrayList<>(), message, CustumStatus.NO_CONTENT));
        }
    }


    @PostMapping(value = "/employees/add")
    @PreAuthorize("hasAuthority('ADD_CONTACT')")
    public ResponseEntity<?> addEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        LOGGER.trace("entering addEmployee() method");
        String message;
        Employee currentUser = currentUserService.information();
        List<EmployeeProfil> employeeProfils = currentUser.getEmployeeProfils();
        int counter = 0;
        boolean isHR = false;
        while (counter < employeeProfils.size()) {
            if (employeeProfils.get(counter).getProfil().getLibelle().equals("HR")) {
                isHR = true;
                break;
            }
            counter++;
        }
        Optional<Profil> optionalProfil = profilService.findProfilById(employeeDto.getProfilId());
        if (optionalProfil.isEmpty()) {
            message = CustumMessage.DONNEE_INTROUVABLE;
            LOGGER.error(message);
            throw new ResourceNotFoundException(message);
        }

        List<PhoneNumberDto> myPhoneNumbers = employeeDto.getPhoneNumbers();
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
        if (employeeDto.getServeurId().isBlank() || employeeDto.getServeurId().isEmpty()) {
            message = CustumMessage.SELECTIONNER_ORGANISATION;
            LOGGER.error(message);
            return ResponseEntity.ok(
                    new CustumApiResponse(false, message));
        }
        Optional<City> optionalCity = cityService.findCityById(employeeDto.getCityId());
        if (optionalCity.isEmpty()) {
            message = CustumMessage.SELECTIONNER_VILLE;
            LOGGER.error(message);
            throw new ResourceNotFoundException(message);
        }
        Optional<Department> optionalDepartment = departmentService.findDepartmentById(employeeDto.getDepartmentId());
        if (optionalDepartment.isEmpty()) {
            message = CustumMessage.SELECTIONNER_DEPARTEMENT;
            LOGGER.error(message);
            throw new ResourceNotFoundException(message);
        }
        Employee newEmployee = new Employee();
        if (Tools.isValidEMAIL(employeeDto.getEmail())) {
            newEmployee.setEmail(Tools.lowerCase(employeeDto.getEmail()));
        } else {
            message = CustumMessage.EMAIL_INCORRECTE;
            LOGGER.error(message);
            return ResponseEntity.ok(
                    new CustumApiResponse(false, message));
        }
        Optional<Employee> optionalEmployee = employeeService.findEmployeeByEmail(employeeDto.getEmail());
        if (optionalEmployee.isPresent()) {
            message = CustumMessage.EMAIL_TAKEN;
            LOGGER.error(message);
            throw new ConflictException(message);
        }

        if (isHR) {

            newEmployee.setLastName(Tools.upperCase(employeeDto.getLastName()));
            newEmployee.setFirstName(Tools.toCapitalize(employeeDto.getFirstName()));
            newEmployee.setUsername(Tools.lowerCase(generateUsername()));
            newEmployee.setPassword(encoder.encode(Tools.generateRandomPASSWORD(6)));
            newEmployee.setEmail(employeeDto.getEmail());
            newEmployee.setStatut(false);
            newEmployee.setNombreGenerationCode(0);
            newEmployee.setNombreVerifyCode(0);
            newEmployee.setCity(optionalCity.get());
            newEmployee.setDepartment(optionalDepartment.get());
            employeeService.addEmployee(newEmployee);
            for (PhoneNumberDto myPhoneNumber : myPhoneNumbers) {
                Optional<Country> optionalCountry = countryService.findCountryByIsoCode(myPhoneNumber.getCountry().getIsoCode());
                PhoneNumber phoneNumber = new PhoneNumber();
                phoneNumber.setCountry(optionalCountry.get());
                phoneNumber.setNumber(myPhoneNumber.getNumber());
                phoneNumber.setIsPrincipal(myPhoneNumber.getIsPrincipal());
                phoneNumber.setEmployee(newEmployee);
                phoneNumberService.addPhoneNumber(phoneNumber);
            }

            List<Long> privilegeIds = employeeDto.getPrivilegeIds();
            if (privilegeIds.size() > 0) {
                for (Long privilegeId : privilegeIds) {
                    Optional<Privilege> privilege = privilegeService.findPrivilegeById(privilegeId);
                    Autorisation autorisation = new Autorisation();
                    if (privilege.isPresent()) {
                        if (!autorisationService.existsByProfilAndPrivilege(optionalProfil.get(), privilege.get())) {
                            autorisation.setPrivilege(privilege.get());
                            autorisation.setProfil(optionalProfil.get());
                            autorisation.setStatut(false);
                            autorisationService.saveAutorisation(autorisation);
                        }
                    }
                }
            }

            EmployeeProfil newEmployeeProfil = new EmployeeProfil();
            newEmployeeProfil.setEmployee(newEmployee);
            newEmployeeProfil.setProfil(optionalProfil.get());
            employeeProfilService.saveEmployeeProfil(newEmployeeProfil);
        } else {
            System.out.println("ici je ne suis pas Sales");
            if (employeeDto.getUsername().isBlank() || employeeDto.getUsername().isEmpty()) {
                message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE;
                LOGGER.error(message);
                return ResponseEntity.ok(
                        new CustumApiResponse(false, message));
            }
            if (employeeDto.getPassword().isEmpty() || employeeDto.getPassword().isBlank()) {
                message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE;
                LOGGER.error(message);
                return ResponseEntity.ok(
                        new CustumApiResponse(false, message));
            }
            if (employeeDto.getConfirmationPassword().isEmpty() || employeeDto.getConfirmationPassword().isBlank()) {
                message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE;
                LOGGER.error(message);
                return ResponseEntity.ok(
                        new CustumApiResponse(false, message));
            }
            if (employeeDto.getPassword().length() < 6 || employeeDto.getConfirmationPassword().length() < 6) {
                message = CustumMessage.TAILLE_MOT_PASSE_INCORRECTE;
                LOGGER.error(message);
                return ResponseEntity.ok(
                        new CustumApiResponse(false, message));
            }
            Optional<Employee> employeeFound = employeeService.findEmployeeByUsername(employeeDto.getUsername());
            if (employeeFound.isPresent()) {
                message = CustumMessage.USERNAME_TAKEN;
                LOGGER.error(message);
                return ResponseEntity.ok(new CustumApiResponse(false, message));
            }
            if (!PasswordValidator.isValid(employeeDto.getPassword())) {
                message = CustumMessage.CRITERE_ROBUSTESSE_PASSWORD;
                LOGGER.error(message);
                return ResponseEntity.ok(
                        new CustumApiResponse(false, message));
            }
            if (!employeeDto.getPassword().equals(employeeDto.getConfirmationPassword())) {
                message = CustumMessage.PASSWORD_NON_IDENTIQUE;
                LOGGER.error(message);
                return ResponseEntity.ok(
                        new CustumApiResponse(false, message));
            }
            newEmployee.setPassword(encoder.encode(employeeDto.getPassword()));
            newEmployee.setDepartment(optionalDepartment.get());
            newEmployee.setStatut(false);
            newEmployee.setCity(optionalCity.get());
            newEmployee.setOnLine(false);
            newEmployee.setLastName(Tools.upperCase(employeeDto.getLastName()));
            newEmployee.setFirstName(Tools.toCapitalize(employeeDto.getFirstName()));
            newEmployee.setUsername(Tools.lowerCase(employeeDto.getUsername()));
            newEmployee.setNombreGenerationCode(0);
            newEmployee.setNombreVerifyCode(0);
            employeeService.addEmployee(newEmployee);
            for (PhoneNumberDto myPhoneNumber : myPhoneNumbers) {
                Optional<Country> optionalCountry = countryService.findCountryByIsoCode(myPhoneNumber.getCountry().getIsoCode());
                PhoneNumber phoneNumber = new PhoneNumber();
                phoneNumber.setCountry(optionalCountry.get());
                phoneNumber.setNumber(myPhoneNumber.getNumber());
                phoneNumber.setIsPrincipal(myPhoneNumber.getIsPrincipal());
                phoneNumber.setEmployee(newEmployee);
                phoneNumberService.addPhoneNumber(phoneNumber);
            }
        }
        message = CustumMessage.OPERATION_SUCCESS;
        LOGGER.info(message);
        return ResponseEntity.ok(new CustumApiResponse(message, CustumStatus.OK));
    }


    @PutMapping(value = "/employees/update")
    @PreAuthorize("hasAuthority('UPDATE_CONTACT')")
    public ResponseEntity<?> updateEmployee(@Valid @RequestBody EmployeeUpdateRequestDto employeeUpdateRequestDto) {
        Optional<Employee> optionalEmployee = employeeService.findEmployeeById(employeeUpdateRequestDto.getId());
        if (optionalEmployee.isEmpty()) {
            message = CustumMessage.CONTACT_INEXISTANT;
            LOGGER.error(message);
            throw new ResourceNotFoundException(message);
        }
        Optional<Employee> employee = employeeService.findEmployeeByEmail(employeeUpdateRequestDto.getEmail());
        if (employee.isPresent() && !optionalEmployee.get().getEmployeeId().equals(employeeUpdateRequestDto.getId())) {
            message = CustumMessage.EMAIL_TAKEN;
            LOGGER.error(message);
            throw new ConflictException(message);
        }
        List<PhoneNumberDto> phoneNumbers = employeeUpdateRequestDto.getPhoneNumbers();
        for (PhoneNumberDto phoneDto : phoneNumbers) {
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
            if (alreadyExist && !optionalEmployee.get().getEmployeeId().equals(employeeUpdateRequestDto.getId())) {
                message = CustumMessage.NUMERO_EXISTANT;
                LOGGER.error(message);
                throw new ConflictException(message);
            }
        }

        optionalEmployee.get().setLastName(Tools.upperCase(employeeUpdateRequestDto.getLastName()));
        optionalEmployee.get().setFirstName(Tools.toCapitalize(employeeUpdateRequestDto.getFirstName()));
        optionalEmployee.get().setEmail(Tools.lowerCase(employeeUpdateRequestDto.getEmail()));
        employeeService.updateEmployee(optionalEmployee.get());
        for (PhoneNumberDto myPhoneNumber : phoneNumbers) {
            Optional<Country> optionalCountry = countryService.findCountryByIsoCode(myPhoneNumber.getCountry().getIsoCode());
            PhoneNumber phoneNumber = new PhoneNumber();
            phoneNumber.setCountry(optionalCountry.get());
            phoneNumber.setNumber(myPhoneNumber.getNumber());
            phoneNumber.setIsPrincipal(myPhoneNumber.getIsPrincipal());
            phoneNumber.setEmployee(optionalEmployee.get());
            phoneNumberService.addPhoneNumber(phoneNumber);
        }
        EmployeeResponseDto employeeToUpdated = mapEmployeeToEmployeeResponseDTO(optionalEmployee.get());
        return ResponseEntity.ok(new CustumApiResponse(employeeToUpdated, CustumMessage.OPERATION_SUCCESS, CustumStatus.OK));
    }


    /**
     * Modifier Mot de passe
     *
     * @param passwordDto
     * @return
     */
    @PostMapping(value = "/employees/updatepassword")
    @PreAuthorize("hasAuthority('UPDATE_CONTACT_PASSWORD')")
    public ResponseEntity<?> updatePasswordEmployee(@Valid @RequestBody PasswordDto passwordDto) {
        LOGGER.trace("entering updatePasswordEmployee() method");
        String message;
        currentUser = currentUserService.information();
        if (!PasswordValidator.isValid(passwordDto.getNouveauPassword())) {
            message = CustumMessage.CRITERE_ROBUSTESSE_PASSWORD;
            LOGGER.error(message);
            return ResponseEntity.ok(
                    new CustumApiResponse(false, message));
        }

        if (!passwordDto.getNouveauPassword().equals(passwordDto.getConfirmationNouveauPassword())) {
            message = CustumMessage.PASSWORD_NON_IDENTIQUE;
            LOGGER.error(message);
            return ResponseEntity.ok(
                    new CustumApiResponse(false, message));
        }

        if (!encoder.matches(passwordDto.getAncienPassword(), currentUser.getPassword())) {
            message = CustumMessage.ANCIEN_PASSWORD;
            LOGGER.error(message);
            throw new ResourceNotFoundException(message);
        }
        if (!currentUser.getStatut()) {
            message = CustumMessage.COMPTE_DESACTIVE;
            LOGGER.error(message);
            throw new ResourceNotFoundException(message);
        }
        currentUser.setPassword(encoder.encode(passwordDto.getNouveauPassword()));
        employeeService.updateEmployee(currentUser);
        message = CustumMessage.OPERATION_SUCCESS;
        LOGGER.info(message);
        return ResponseEntity.ok(new CustumApiResponse(message, CustumStatus.OK));
    }

    /**
     * @param assignProfilToEmployeeDto
     * @return
     */
    @PostMapping(value = "/employees/assignprofil")
    @PreAuthorize("hasAuthority('ASSIGN_CONTACT_PROFIL')")
    public ResponseEntity<?> assignProfilToEmployee(@Valid @RequestBody AssignProfilToEmployeeDto assignProfilToEmployeeDto) {
        LOGGER.trace("entering activerEmployee() method");
        String message;
        currentUser = currentUserService.information();
        Optional<Employee> optionalEmployee = employeeService.findEmployeeById(assignProfilToEmployeeDto.getEmployeeId());
        if (optionalEmployee.isPresent()) {
            Optional<Profil> optionalProfil = profilService.findProfilById(assignProfilToEmployeeDto.getProfilId());
            if (optionalProfil.isPresent()) {
                Optional<EmployeeProfil> optionalEmployeeProfil = employeeProfilService.findEmployeeProfilByEmployeeAndProfil(optionalEmployee.get(), optionalProfil.get());
                if (optionalEmployeeProfil.isEmpty()) {
                    EmployeeProfil newEmployeeProfil = new EmployeeProfil();
                    newEmployeeProfil.setProfil(optionalProfil.get());
                    newEmployeeProfil.setEmployee(optionalEmployee.get());
                    employeeProfilService.saveEmployeeProfil(newEmployeeProfil);
                    message = CustumMessage.OPERATION_SUCCESS;
                    LOGGER.info(message);
                    return ResponseEntity.ok(new CustumApiResponse(message, CustumStatus.OK));
                } else {
                    message = CustumMessage.PROFIL_DUPLICATION;
                    LOGGER.error(message);
                    throw new ResourceNotFoundException(message);
                }
            } else {
                message = CustumMessage.PROFIL_INEXISTANT;
                LOGGER.error(message);
                throw new ResourceNotFoundException(message);
            }
        } else {
            message = CustumMessage.CONTACT_INEXISTANT;
            LOGGER.error(message);
            throw new ResourceNotFoundException(message);
        }
    }


    /**
     * Cette methode retourne une employee en fonction d'un identifiant
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/employees/find/{id}")
    @PreAuthorize("hasAuthority('SHOW_CONTACT')")
    public ResponseEntity<?> findEmployeeById(@Valid @PathVariable(value = "id") @Min(1) Long id) {
        LOGGER.trace("entering getemployeeById() method");
        Optional<Employee> elementFound = employeeService.findEmployeeById(id);
        if (elementFound.isEmpty()) {
            message = CustumMessage.DONNEE_INTROUVABLE;
            LOGGER.error(message);
            throw new ResourceNotFoundException(message);
        } else {
            message = CustumMessage.OPERATION_SUCCESS;
            LOGGER.info(message);
            EmployeeResponseDto employeeDtoFound = mapEmployeeToEmployeeResponseDTO(elementFound.get());
            return ResponseEntity.ok(new CustumApiResponse(employeeDtoFound, message, CustumStatus.OK));
        }

    }


    /**
     * Transforme source List to dto list targetClass
     *
     * @param source
     * @param targetClass
     * @param <S>
     * @param <T>
     * @return
     */
    <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        ModelMapper modelMapper = new ModelMapper();
        return source.stream().map(element -> modelMapper.map(element, targetClass)).collect(Collectors.toList());
    }

    /**
     * Transforme un Employee en EmployeeDTO
     *
     * @param employee
     * @return
     */
    private EmployeeDto mapEmployeeToEmployeeDTO(Employee employee) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(employee, EmployeeDto.class);
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


    public String generateUsername() {
        String username = Tools.generateUSERNAME();
        Optional<Employee> optionalEmployee = employeeService.findEmployeeByUsername(username);
        if (optionalEmployee.isPresent()) {
            generateUsername();
        }
        return username;
    }

}
