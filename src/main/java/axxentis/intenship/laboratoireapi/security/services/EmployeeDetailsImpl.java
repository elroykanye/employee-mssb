package axxentis.intenship.laboratoireapi.security.services;

import axxentis.intenship.laboratoireapi.entities.Autorisation;
import axxentis.intenship.laboratoireapi.entities.Employee;
import axxentis.intenship.laboratoireapi.entities.EmployeeProfil;
import axxentis.intenship.laboratoireapi.entities.Privilege;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class EmployeeDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public EmployeeDetailsImpl(Long id, String username, String email, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static EmployeeDetailsImpl build(Employee employee) {
        List<EmployeeProfil> employeeProfils = employee.getEmployeeProfils();
        List<Autorisation> autorisations = new ArrayList<>();
        List<Privilege> privileges = new ArrayList<>();
        for (EmployeeProfil employeeProfil : employeeProfils
             ) {
            autorisations = employeeProfil.getProfil().getAutorisations();
            for (Autorisation authorisation:autorisations
                 ) {
                privileges.add(authorisation.getPrivilege());
            }
        }

        List<GrantedAuthority> authorities = privileges.stream()
                .map(privilege -> new SimpleGrantedAuthority(privilege.getLibelle()))
                .collect(Collectors.toList());

        return new EmployeeDetailsImpl(
                employee.getId(),
                employee.getUsername(),
                employee.getEmail(),
                employee.getPassword(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        EmployeeDetailsImpl employee = (EmployeeDetailsImpl) o;
        return Objects.equals(id, employee.id);
    }
}
