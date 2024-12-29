package rmi_api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

public interface ServicesAPI extends Remote {

    ResponseRMI processUserQuery(String userQuery) throws RemoteException, SQLException;

    ResponseRMI getAllAnnonces() throws RemoteException, SQLException;

}
