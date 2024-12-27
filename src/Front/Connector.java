package Front;

import java.rmi.RemoteException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

//the remote object is created and registered in the RMI registry with naming.rebind()
public class Connector {
	public static void main(String[] args) {
		try {
			Registry registry = LocateRegistry.createRegistry(1110);
			Server serveur = new Server();
			Naming.rebind("//localhost:1110/Test", serveur);
			System.out.println("Server bound successfully");
			//UnicastRemoteObject.unexportObject(registry, true);  // true pour forcer l'arrêt
            //System.out.println("RMI registry arrêté.");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
