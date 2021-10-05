package axxentis.intenship.laboratoireapi.servicesImpl;

import axxentis.intenship.laboratoireapi.entities.Employee;
import axxentis.intenship.laboratoireapi.entities.Verification;
import axxentis.intenship.laboratoireapi.repositories.VerificationRepository;
import axxentis.intenship.laboratoireapi.services.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Transactional
@Service
public class VerificationServiceImpl implements VerificationService {
    @Autowired
    private final VerificationRepository verificationRepository;

    public VerificationServiceImpl(VerificationRepository verification, VerificationRepository verificationRepository) {
        super();
        this.verificationRepository = verificationRepository;
    }


    @Override
    public Verification saveVerification(Verification verifierContact) {
        return verificationRepository.save(verifierContact);
    }

    @Override
    public List<Verification> getAllVerification() {
        return verificationRepository.findAll();
    }

    @Override
    public List<Verification> getAllVerificationByEmployee(Employee employee) {
        return verificationRepository.findAllByEmployee(employee);
    }

    @Override
    public Optional<Verification> findVerificationByCode(String code) {
        return verificationRepository.findByCode(code);
    }
}
