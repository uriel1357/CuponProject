package client;

import dao.CompaniesDAO;
import dao.CuponsDAO;
import dao.CustomersDAO;

public abstract class ClientFacade {

    protected CompaniesDAO companiesDAO;

    protected CustomersDAO customersDAO;

    protected CuponsDAO cuponsDAO;

    public abstract  boolean login(String email, String password);
}
