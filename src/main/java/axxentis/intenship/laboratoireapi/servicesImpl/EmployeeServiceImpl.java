package axxentis.intenship.laboratoireapi.servicesImpl;

import axxentis.intenship.laboratoireapi.entities.Department;
import axxentis.intenship.laboratoireapi.entities.Employee;
import axxentis.intenship.laboratoireapi.entities.PhoneNumber;
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
    public Optional<Employee> findEmployeeByIdAndOnLineIsTrue(Long id) {
        return employeeRepository.findEmployeeByEmployeeIdAndOnLineIsTrue(id);
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
        return employeeRepository.findEmployeeByPhoneNumber(phoneNumber);
    }

    @Override
    public void deleteEmployee(Long id) {

    }

    @Override
    public void desactiverEmployee(Employee employee) {
        employee.setStatut(false);
        employeeRepository.save(employee);
    }

    @Override
    public void activerEmployee(Employee employee) {
        employee.setStatut(true);
        employeeRepository.save(employee);
    }

    /**
     * Recherche en fonction des critères
     *
     * @param lastName
     * @return
     */
    @Override
    public List<Employee> findByCriteria(String lastName) {
        return employeeRepository.findAll((Specification<Employee>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (lastName != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("lastName"), lastName)));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        });
    }

    public List<Employee> searchByLikeCriteria(String search, String lastName, String firstName, Boolean status, String telephone, String departement) {
        return employeeRepository.findAll((Specification<Employee>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Join<Employee, PhoneNumber> phoneJoin = root.join("phoneNumbers");
            Join<Employee, Department> departmentJoin = root.join("department");
            if (search != null) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(root.get("lastName"), "%" + search + "%"),
                        criteriaBuilder.like(root.get("email"), "%" + search + "%"),
                        criteriaBuilder.like(root.get("firstName"), "%" + search + "%")
                ));
            }
            if (lastName != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%"+lastName.toLowerCase()+"%")));
            }
            if (firstName != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%"+firstName.toLowerCase()+"%")));
            }
            if (status != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("statut"), status)));
            }
            if (departement != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(criteriaBuilder.lower(departmentJoin.get("department")), departement.toLowerCase())));
            }
            if (telephone != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(criteriaBuilder.lower(phoneJoin.get("number")), telephone.toLowerCase())));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        });
    }


    public List<Employee> findByCriteria(String employeeName, String employeeRole) {
        return employeeRepository.findAll(new Specification<Employee>() {
            @Override
            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (employeeName != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("employeeName"), "%" + employeeName + "%")));
                }
                if (employeeRole != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("employeeRole"), employeeRole)));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }

}
