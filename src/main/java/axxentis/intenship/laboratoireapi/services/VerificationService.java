package axxentis.intenship.laboratoireapi.services;


import axxentis.intenship.laboratoireapi.entities.Employee;
import axxentis.intenship.laboratoireapi.entities.Verification;

import java.util.List;
import java.util.Optional;


public interface VerificationService {
    public Verification saveVerification(Verification verifierEmployee);
    public List<Verification> getAllVerification();
    public List<Verification> getAllVerificationByEmployee(Employee employee);
    public Optional<Verification> findVerificationByCode(String code);
}
