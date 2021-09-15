package axxentis.intenship.laboratoireapi;

import axxentis.intenship.laboratoireapi.entities.Employee;
import axxentis.intenship.laboratoireapi.entities.Privilege;
import axxentis.intenship.laboratoireapi.services.EmployeeService;
import axxentis.intenship.laboratoireapi.services.PrivilegeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class     LaboratoireApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LaboratoireApiApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    CommandLineRunner run(PrivilegeService privilegeService, EmployeeService employeeService){
//        return args -> {
//           privilegeService.addPrivilege( new Privilege(null, "sqdsdq", "sqdsdq"));
//           employeeService.addEmployee( new Employee(null, "sqdsdq", "sqdsdq"));


//            userService.saveUser((new AppUser(null, "Best ", "best","1234",new ArrayList<>())));
//            userService.saveUser((new AppUser(null, "Sdsf ", "sdqs","1234",new ArrayList<>())));
//            userService.saveUser((new AppUser(null, "Fdss ", "eze","1234",new ArrayList<>())));
//            userService.saveUser((new AppUser(null, "Qsqds ", "zefef","1234",new ArrayList<>())));


//            userService.addRoleToUser("best","ROLE_USER");
//            userService.addRoleToUser("ssqd","ROLE_MANAGER");
//            userService.addRoleToUser("besqfdt","ROLE_ADMIN");
//            userService.addRoleToUser("besdst","ROLE_SUPER_ADMIN");
//        };
//    }



}
