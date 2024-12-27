package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.Annonce;

public class Database {

	    // Database connection details
	    private String url = "jdbc:mysql://localhost:3306/javaproject";
	    private String user = "root";
	    private String password = "";

	    public void insertData(ArrayList<Annonce> listAnnonce) {
	        // JDBC objects
	        Connection connection = null;
	        PreparedStatement preparedStatement = null;

	        // la requeette d insertion
	        String insertQuery = "INSERT INTO annonce (title, location, description, StartDate, EndDate, PostsNum, Secteur, Fonction, Experience, EtudeLevel, "
	        		+ "ContratDetails) VALUES (?, ?, ?, STR_TO_DATE(REGEXP_REPLACE(?, '[.]', '/'), '%d/%m/%Y'), STR_TO_DATE(REGEXP_REPLACE(?, '[.]', '/'), '%d/%m/%Y'),"
	        		+ " ?, ?, ?, ?, ?, ?)";
	        try {
	        	// Loading the connection
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				// creation de la connexion avec url mdp et username
		        connection = DriverManager.getConnection(url, user, password);
		        System.out.println("Connected to the database!");
		        
			} catch (Exception e) {
				e.printStackTrace();
			}
	        
	        ArrayList test = this.selectData();
	        if(test.size() == 0) {
	        	int i = listAnnonce.size();
	            for(Annonce item : listAnnonce) {
	            	try {
	                    preparedStatement = connection.prepareStatement(insertQuery);
	                    
	                    preparedStatement.setString(1, item.getTitle());
	                    preparedStatement.setString(2, item.getLocation());
	                    preparedStatement.setString(3, item.getDescription());
	                    preparedStatement.setString(4, item.getStartDate());
	                    preparedStatement.setString(5, item.getEndDate());
	                    preparedStatement.setInt(6, item.getPostsNum());
	                    preparedStatement.setString(7, item.getSecteur());
	                    preparedStatement.setString(8, item.getFonction());
	                    preparedStatement.setString(9, item.getExperience());
	                    preparedStatement.setString(10, item.getEtudeLevel());
	                    preparedStatement.setString(11, item.getContracDetails());

	                    // Step 4: Execute the query
	                    int rowsInserted = preparedStatement.executeUpdate();
	                    if (rowsInserted > i) {
	                        System.out.println("A new row ("+i+") was inserted successfully!");
	                        i++;
	                    }
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	        else {
	        	System.out.println("rows already added ...");
	        }
	        // Step 5: Close the resources
	        try {
	            if (preparedStatement != null) preparedStatement.close();
	            if (connection != null) connection.close();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        
	    }
	    
	    public ArrayList<Annonce> selectData() {
	    	ArrayList<Annonce> a = new ArrayList<Annonce>();
	    	// JDBC objects
	        Connection connection = null;
	        PreparedStatement preparedStatement = null;
	        //requete de selection 
	        String query = "SELECT * FROM annonce";
	    	try {
	    		//preparation a la connection 
	    		Class.forName("com.mysql.cj.jdbc.Driver");
	    		
	    		//Connection
	    		connection = DriverManager.getConnection(url,user,password);
	    		System.out.println("Connected to the database!");
	    		
	    		
	    	}catch(Exception e){
	    		System.out.println("Connection failed");
	    	}
	    	try {
	    		preparedStatement = connection.prepareStatement(query);
	    		// Executing the query
	            ResultSet resultSet = preparedStatement.executeQuery();
	            while(resultSet.next()){
	            	Annonce test = new Annonce(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5)
	            			,resultSet.getString(6),resultSet.getInt(7),resultSet.getString(8),resultSet.getString(9),resultSet.getString(10),resultSet.getString(11),
	            			resultSet.getString(12));
	            	a.add(test);
	            }
	    	}
	    	catch(Exception e) {
	    		System.out.println("Selection failed");
	    	}
	    	
	    	// Step 5: Close the resources
	        try {
	            if (preparedStatement != null) preparedStatement.close();
	            if (connection != null) connection.close();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    	return a;
	    	
	    }

}
