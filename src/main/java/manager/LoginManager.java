package manager;

import client.AdminFacade;
import client.ClientFacade;
import client.CompanyFacade;
import client.CustomerFacade;
import exceptions.ClientInvalidCredentialsException;

public class LoginManager {

    private static LoginManager loginManager;

    private LoginManager() {
    }

    public static LoginManager getInstance() {
        if (loginManager == null)
            loginManager = new LoginManager();

        return loginManager;
    }

    public ClientFacade login(String email, String password, ClientType clientType) throws ClientInvalidCredentialsException {
        ClientFacade facade = null;
        switch (clientType) {
            case COMPANY:
                facade = isLogin(email, password, new CompanyFacade());
                break;
            case CUSTOMER:
                facade = isLogin(email, password, new CustomerFacade());
                break;
            case ADMINISTRATOR:
                facade = isLogin(email, password, new AdminFacade());
                break;

        }
        return facade;
    }

    private ClientFacade isLogin(String email, String password, ClientFacade facade) {
        if (!facade.login(email, password)) {
            facade = null;
        }
        return facade;
    }
}
