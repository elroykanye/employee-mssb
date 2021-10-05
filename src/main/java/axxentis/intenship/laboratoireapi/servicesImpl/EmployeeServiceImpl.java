package axxentis.intenship.laboratoireapi.servicesImpl;

import axxentis.intenship.laboratoireapi.entities.Employee;
import axxentis.intenship.laboratoireapi.repositories.EmployeeRepository;
import axxentis.intenship.laboratoireapi.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Transactional
@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public Boolean existsEmployeeByUsername(String username) {
        return employeeRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsEmployeeByEmail(String email) {
        return employeeRepository.existsByEmail(email);
    }

    @Override
    public void updateEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public List<Employee> findAllEmployee() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> SearchEmployee(String searchKey) {
        return null;
    }


    @Override
    public Optional<Employee> findEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Optional<Employee> findEmployeeByIdAndIsOnLineIsTrue(Long id) {
        return employeeRepository.findEmployeeByIdAndIsOnLineIsTrue(id);
    }

    @Override
    public Optional<Employee> findEmployeeByUsername(String username) {
        return employeeRepository.findEmployeeByUsername(username);
    }


    @Override
    public Optional<Employee> findEmployeeByEmail(String email) {
        return employeeRepository.findEmployeeByEmail(email);
    }

    @Override
    public Optional<Employee> findEmployeeByPhoneNumber(String phoneNumber) {
        return employeeRepository.findEmployeeByNumeroTelephone(phoneNumber);
    }

    @Override
    public void deleteEmployee(Long id) {

    }
    /**
     * Recherche en fonction des critères
     *
     * @param nom
     * @return
     */
    @Override
    public List<Employee> findByCriteria(String nom) {
        return employeeRepository.findAll((Specification<Employee>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (nom != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("nom"), nom)));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        });
    }
}
