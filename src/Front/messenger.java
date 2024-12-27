package Front;

import Front.Annonce;
import java.util.ArrayList;
import java.rmi.*;
public interface messenger extends Remote {
	String sendInput(String msg) throws RemoteException;
	ArrayList<Annonce> filter(String critere1,String critere2) throws RemoteException;
	
}
