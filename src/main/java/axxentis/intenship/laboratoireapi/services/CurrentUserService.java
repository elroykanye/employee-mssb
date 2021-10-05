package axxentis.intenship.laboratoireapi.services;

import axxentis.intenship.laboratoireapi.entities.Employee;

import java.util.List;


public interface CurrentUserService {
    public Employee information();
    public String getToken();
    public String getRefreshToken();
    public List<String> getAuthorizations();
}
