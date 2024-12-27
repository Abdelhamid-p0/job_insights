package Front;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner; 
import java.io.File;
import Front.Annonce;
//this is the remote object that implements the interface that clients can use 
public class Server extends UnicastRemoteObject implements messenger {
	
	public Server() throws RemoteException {
		super();
	}
	
	@Override
	public String sendInput (String msg) throws RemoteException {
		String response="";
		if (msg.equals("Salam")) {
			response= "Salam Client !";
		}else if(msg.equals("What's the goal of this app? ")) {
			response = " We make it easy for clients to look up Job Offers !";
		}
		return response;
	}
	public  ArrayList<Annonce> filter (String fonction,String location) {
		System.out.println("filter method called with parameters: fonction=" + fonction + ", location=" + location);

		ArrayList<Annonce> liste = new ArrayList<Annonce>();
		int id;
		String Title,Location,Description,StartDate,EndDate,Fonction;
		try { Scanner sc = new Scanner(new File("C:\\Users\\ayaes\\Downloads\\file.csv"));
		System.out.println("Reading file: C:\\Users\\ayaes\\Downloads\\file.csv");

		sc.useDelimiter(",");
		while(sc.hasNextLine()) {
			String line = sc.nextLine();
			String[] tab = line.split("-");
			if (tab.length == 7){
				id = Integer.parseInt(tab[0]);
				Title = tab[1];
				Location = tab[2];
				Description = tab[3];
				StartDate = tab[4];
				EndDate = tab[5];
				Fonction = tab[6];
				boolean matchesFonction = (fonction == null || Fonction.equalsIgnoreCase(fonction));
                boolean matchesLocation = (location == null || Location.equalsIgnoreCase(location)); 
				if (matchesFonction|| matchesLocation) {
					Annonce annonce = new Annonce(id,Title,Location,Description,StartDate,EndDate,Fonction);
					System.out.println(annonce.toString1());
					liste.add(annonce);
				}
				}
			}
		sc.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return liste;
		}
}

