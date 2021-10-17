package axxentis.intenship.laboratoireapi.services.servicesImpl;

import axxentis.intenship.laboratoireapi.entities.Employee;
import axxentis.intenship.laboratoireapi.entities.VerifyEmployee;
import axxentis.intenship.laboratoireapi.repositories.VerifyEmployeeRepository;
import axxentis.intenship.laboratoireapi.services.VerifyEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class VerifyEmployeeServiceImpl implements VerifyEmployeeService {
    @Autowired
    private final VerifyEmployeeRepository verifyEmployeeRepository;

    public VerifyEmployeeServiceImpl(VerifyEmployeeRepository verifyEmployeeRepository) {
        super();
        this.verifyEmployeeRepository = verifyEmployeeRepository;
    }


    @Override
    public VerifyEmployee saveVerificationEmployee(VerifyEmployee verifyEmployee) {
        return verifyEmployeeRepository.save(verifyEmployee);
    }

    @Override
    public List<VerifyEmployee> getAllVerificationEmployee() {
        return verifyEmployeeRepository.findAll();
    }

    @Override
    public List<VerifyEmployee> getAllVerificationEmployeeByEmployee(Employee employee) {
        return verifyEmployeeRepository.findAllByEmployee(employee);
    }

    @Override
    public Optional<VerifyEmployee> findVerificationEmployeeByCode(String code) {
        return verifyEmployeeRepository.findByCode(code);
    }
}
