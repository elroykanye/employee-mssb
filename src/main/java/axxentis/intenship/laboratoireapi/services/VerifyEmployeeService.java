package axxentis.intenship.laboratoireapi.services;


import axxentis.intenship.laboratoireapi.entities.Employee;
import axxentis.intenship.laboratoireapi.entities.VerifyEmployee;

import java.util.List;
import java.util.Optional;

public interface VerifyEmployeeService {
    public VerifyEmployee saveVerificationEmployee(VerifyEmployee verifyEmployee);
    public List<VerifyEmployee> getAllVerificationEmployee();
    public List<VerifyEmployee> getAllVerificationEmployeeByEmployee(Employee employee);
    public Optional<VerifyEmployee> findVerificationEmployeeByCode(String code);
}
