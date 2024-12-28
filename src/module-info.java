/**
 * 
 */
/**
 * 
 */
module Front {
	    requires javafx.controls;
	    requires javafx.fxml;
	    requires javafx.graphics;
	    requires javafx.base;
		requires org.jfree.jfreechart; // Pour JFreeChart (assurez-vous que la dépendance est bien configurée)
	    requires java.rmi;
    	requires java.sql;
		requires rmi.api;

		exports Front; // Exportez le package contenant la classe GUI pour qu'il soit accessible.

}
