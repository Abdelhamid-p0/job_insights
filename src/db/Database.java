package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.commons.lang3.tuple.Pair;
import model.Annonce;

public class Database {

	// Database connection details
	private String url = "jdbc:mysql://localhost:3306/job_insight";
	private String user = "root";
	private String password = "";

	//INSERTION DANS LA BASE
	public void insertData(ArrayList<Annonce> listAnnonce) {
			// JDBC objects
			Connection connection = null;
			PreparedStatement preparedStatement = null;

			// La requête d'insertion
			String insertQuery = "INSERT INTO annonce (id,title, description, StartDate, EndDate, PostsNum, Secteur, Fonction, Experience, EtudeLevel, "
					+ "ContratDetails, URL, SiteName, adresseEntreprise , siteWebEntreprise , nomEntreprise , descriptionEntreprise, "
					+ "Region, City, Industry, TraitsPersonnalite, CompetencesRequises, SoftSkills, CompetencesRecommandees, Langue, NiveauLangue, "
					+ "Salaire, AvantagesSociaux, Teletravail) "
					+ "VALUES (?, ?, STR_TO_DATE(REGEXP_REPLACE(?, '[.]', '/'), '%d/%m/%Y'), STR_TO_DATE(REGEXP_REPLACE(?, '[.]', '/'), '%d/%m/%Y'), "
					+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";

			try {
				// Chargement du driver
				Class.forName("com.mysql.cj.jdbc.Driver");

				// Création de la connexion avec URL, mot de passe et username
				connection = DriverManager.getConnection(url, user, password);
				System.out.println("Connected to the database!");

			} catch (Exception e) {
				e.printStackTrace();
			}

			ArrayList<Annonce> test = this.selectData();
			if (test.size() == 0) {
				int i = listAnnonce.size();
				for (Annonce item : listAnnonce) {
					try {
						preparedStatement = connection.prepareStatement(insertQuery);

						preparedStatement.setInt(1, item.getId());
						preparedStatement.setString(2, item.getTitle());
						preparedStatement.setString(3, item.getDescription());
						preparedStatement.setString(4, item.getStartDate());
						preparedStatement.setString(5, item.getEndDate());
						preparedStatement.setInt(6, item.getPostsNum());
						preparedStatement.setString(7, item.getSecteur());
						preparedStatement.setString(8, item.getFonction());
						preparedStatement.setString(9, item.getExperience());
						preparedStatement.setString(10, item.getEtudeLevel());
						preparedStatement.setString(11, item.getContracDetails());
						preparedStatement.setString(12, item.getUrl());
						preparedStatement.setString(13, item.getSiteName());
						preparedStatement.setString(14, item.getAdresseEntreprise());
						preparedStatement.setString(15, item.getSiteWebEntreprise());
						preparedStatement.setString(16, item.getNomEntreprise());
						preparedStatement.setString(17, item.getDescriptionEntreprise());
						preparedStatement.setString(18, item.getRegion());
						preparedStatement.setString(19, item.getCity());
						preparedStatement.setString(20, item.getIndustry());
						preparedStatement.setString(21, item.getTraitsPersonnalite());
						preparedStatement.setString(22, item.getCompetencesRequises());
						preparedStatement.setString(23, item.getSoftSkills());
						preparedStatement.setString(24, item.getCompetencesRecommandees());
						preparedStatement.setString(25, item.getLangue());
						preparedStatement.setString(26, item.getNiveauLangue());
						preparedStatement.setString(27, item.getSalaire());
						preparedStatement.setString(28, item.getAvantagesSociaux());
						preparedStatement.setString(29, item.getTeletravail());

						// Exécution de la requête
						int rowsInserted = preparedStatement.executeUpdate();
						if (rowsInserted > i) {
							System.out.println("A new row (" + i + ") was inserted successfully!");
							i++;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				System.out.println("Rows already added ...");
			}

			// Fermeture des ressources
			try {
				if (preparedStatement != null) preparedStatement.close();
				if (connection != null) connection.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	// SELECTION DE TOUT LES LIGNES DE LA BASE DE DONNES
	public ArrayList<Annonce> selectData() {
			ArrayList<Annonce> a = new ArrayList<Annonce>();
			// JDBC objects
			Connection connection = null;
			PreparedStatement preparedStatement = null;
			// requete de selection
			String query = "SELECT * FROM annonce";
			try {
				// préparation à la connexion
				Class.forName("com.mysql.cj.jdbc.Driver");
				// Connection
				connection = DriverManager.getConnection(url, user, password);
				System.out.println("Connected to the database!");

			} catch (Exception e) {
				System.out.println("Connection failed");
				e.printStackTrace();
			}
			try {
				preparedStatement = connection.prepareStatement(query);
				// Exécution de la requête
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					Annonce test = new Annonce(
							resultSet.getInt("id"),
							resultSet.getString("title"),
							resultSet.getString("description"),
							resultSet.getString("StartDate"),
							resultSet.getString("EndDate"),
							resultSet.getInt("PostsNum"),
							resultSet.getString("Secteur"),
							resultSet.getString("Fonction"),
							resultSet.getString("Experience"),
							resultSet.getString("EtudeLevel"),
							resultSet.getString("ContratDetails"),
							resultSet.getString("URL"),
							resultSet.getString("SiteName"),
							resultSet.getString("adresseEntreprise"),
							resultSet.getString("siteWebEntreprise"),
							resultSet.getString("nomEntreprise"),
							resultSet.getString("descriptionEntreprise"),
							resultSet.getString("Region"),
							resultSet.getString("City"),
							resultSet.getString("Industry"),
							resultSet.getString("TraitsPersonnalite"),
							resultSet.getString("CompetencesRequises"),
							resultSet.getString("SoftSkills"),
							resultSet.getString("CompetencesRecommandees"),
							resultSet.getString("Langue"),
							resultSet.getString("NiveauLangue"),
							resultSet.getString("Salaire"),
							resultSet.getString("AvantagesSociaux"),
							resultSet.getString("Teletravail")
					);
					a.add(test);
				}
			} catch (Exception e) {
				System.out.println("Selection failed");
				e.printStackTrace();
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

	//LA TAILLE DE LA BASE DE DONNES
	public int DBsize(){
		int i = 0 ;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = "SELECT COUNT(*) FROM annonce";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			connection = DriverManager.getConnection(url,user,password);
			System.out.println("Connected to the database!");


		}catch(Exception e){
			System.out.println("Connection failed");
		}
		try {
			preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				i = resultSet.getInt(1);
				break;
			}
		}
		catch(Exception e) {
			System.out.println("Selection failed");
		}

		try {
			if (preparedStatement != null) preparedStatement.close();
			if (connection != null) connection.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return i;
	}

	//1st Case

	//SELECTION DE LA BASE AVEC UNE CONDITION SUR UNE DES COLOGNE
	public ArrayList<Annonce> SelectedData(String attribut , String token){
		ArrayList<Annonce> a = new ArrayList<Annonce>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = "SELECT * FROM annonce WHERE "+attribut+" LIKE ?";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			connection = DriverManager.getConnection(url,user,password);
			System.out.println("Connected to the database!");


		}catch(Exception e){
			System.out.println("Connection failed");
		}
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, "%" + token + "%");
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				//ERREUR C EST JUSTE A CAUSE DU CHANGEMENT DE LA BASE DE DONNES
				Annonce test = new Annonce(resultSet.getInt(1),
						resultSet.getString(2),
						resultSet.getString(3),
						resultSet.getString(4),
						resultSet.getString(5),
						resultSet.getInt(6),
						resultSet.getString(7),
						resultSet.getString(8),
						resultSet.getString(9),
						resultSet.getString(10),
						resultSet.getString(11),
						resultSet.getString(12),
						resultSet.getString(13),
						resultSet.getString(14),
						resultSet.getString(15),
						resultSet.getString(16),
						resultSet.getString(17),
						resultSet.getString(18),
						resultSet.getString(19),
						resultSet.getString(20),
						resultSet.getString(21),
						resultSet.getString(22),
						resultSet.getString(23),
						resultSet.getString(24),
						resultSet.getString(25),
						resultSet.getString(26),
						resultSet.getString(27),
						resultSet.getString(28),
						resultSet.getString(29));
				a.add(test);
				System.out.println("I entered here");
			}
		}
		catch(Exception e) {
			System.out.println("Selection error failed");
		}

		try {
			if (preparedStatement != null) preparedStatement.close();
			if (connection != null) connection.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return a;
	}

	//SELECTION DE LA BASE POUR LES DIAGRAMMES JFREECHART
	public ArrayList<Pair<String,Integer>> CountSelection(String attribut , String token){
		Boolean tokenParsed = false;
		int i = 0 ;
		int TotalSize = DBsize();
		ArrayList<Pair<String,Integer>> pairTable = new ArrayList<Pair<String,Integer>>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = "SELECT "+attribut+" , COUNT(*) as row_count FROM annonce GROUP BY "+attribut+" ORDER BY row_count DESC";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			connection = DriverManager.getConnection(url,user,password);
			System.out.println("Connected to the database!");


		}catch(Exception e){
			System.out.println("Connection failed");
		}
		try {
			preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				if(i<4){
					Pair<String,Integer> element = Pair.of(resultSet.getString(1),(int) ((resultSet.getInt(2) / (double) TotalSize) * 100));
					pairTable.add(element);
					if(element.getLeft().contains(token)){
						tokenParsed = true;
					}
				}
				if(i==4){
					if(tokenParsed){
						Pair<String,Integer> element = Pair.of(resultSet.getString(1),(int) ((resultSet.getInt(2) / (double) TotalSize) * 100));
						pairTable.add(element);
						break;
					}
					else{
						tokenParsed = resultSet.getString(1).contains(token);
					}
				}
				i ++ ;
			}
			if(pairTable.size() < 5){
				Pair<String,Integer> element = Pair.of(token,0);
				pairTable.add(element);
			}
			System.out.println("I entered here 2");
		}
		catch(Exception e) {
			System.out.println("Selection error 2 failed");
		}

		try {
			if (preparedStatement != null) preparedStatement.close();
			if (connection != null) connection.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return pairTable;
	}

	//2nd Case

	//SELECTION DE LA BASE AVEC UNE CONDITION SUR UNE DES COLOGNE
	public ArrayList<Annonce> SelectedData2(String column , String attribut , String token){
		ArrayList<Annonce> a = new ArrayList<Annonce>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = "SELECT * FROM annonce WHERE "+attribut+" LIKE ?";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			connection = DriverManager.getConnection(url,user,password);
			System.out.println("Connected to the database!");

		}catch(Exception e){
			System.out.println("Connection failed");
		}
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, "%" + token + "%");
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				Annonce test = new Annonce(
						resultSet.getInt("id"),
						resultSet.getString("title"),
						resultSet.getString("description"),
						resultSet.getString("StartDate"),
						resultSet.getString("EndDate"),
						resultSet.getInt("PostsNum"),
						resultSet.getString("Secteur"),
						resultSet.getString("Fonction"),
						resultSet.getString("Experience"),
						resultSet.getString("EtudeLevel"),
						resultSet.getString("ContratDetails"),
						resultSet.getString("URL"),
						resultSet.getString("SiteName"),
						resultSet.getString("Adresse d'entreprise"),
						resultSet.getString("Site web d'entreprise"),
						resultSet.getString("Nom d'entreprise"),
						resultSet.getString("Description d'entreprise"),
						resultSet.getString("Region"),
						resultSet.getString("City"),
						resultSet.getString("Industry"),
						resultSet.getString("TraitsPersonnalite"),
						resultSet.getString("CompetencesRequises"),
						resultSet.getString("SoftSkills"),
						resultSet.getString("CompetencesRecommandees"),
						resultSet.getString("Langue"),
						resultSet.getString("NiveauLangue"),
						resultSet.getString("Salaire"),
						resultSet.getString("AvantagesSociaux"),
						resultSet.getString("Teletravail")
				);
				a.add(test);
			}
		}
		catch(Exception e) {
			System.out.println("Selection failed");
		}

		try {
			if (preparedStatement != null) preparedStatement.close();
			if (connection != null) connection.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return a;
	}

	//SELECTION DE LA BASE POUR LES DIAGRAMMES JFREECHART
	public ArrayList<Pair<String,Integer>> CountSelection2(String Column , String attribut , String token){
		Boolean tokenParsed = false;
		int i = 0 ;
		int TotalSize = DBsize();
		ArrayList<Pair<String,Integer>> pairTable = new ArrayList<Pair<String,Integer>>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = "SELECT "+Column+" , COUNT(*) as row_count FROM annonce WHERE "+attribut+" LIKE ? "+token+"GROUP BY "+Column+" ORDER BY row_count DESC";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			connection = DriverManager.getConnection(url,user,password);
			System.out.println("Connected to the database!");


		}catch(Exception e){
			System.out.println("Connection failed");
		}
		try {
			preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				if(i<4){
					Pair<String,Integer> element = Pair.of(resultSet.getString(0),(int) ((resultSet.getInt(1)/TotalSize)*100));
					pairTable.add(element);
					if(element.getLeft().contains(token)){
						tokenParsed = true;
					}
				}
				if(i==4){
					if(tokenParsed){
						Pair<String,Integer> element = Pair.of(resultSet.getString(0),(int) ((resultSet.getInt(1)/TotalSize)*100));
						pairTable.add(element);
						break;
					}
					else{
						tokenParsed = resultSet.getString(0).contains(token);
					}
				}
				i ++ ;
			}
			if(pairTable.size() < 5){
				Pair<String,Integer> element = Pair.of(token,0);
				pairTable.add(element);
			}
		}
		catch(Exception e) {
			System.out.println("Selection failed");
		}

		try {
			if (preparedStatement != null) preparedStatement.close();
			if (connection != null) connection.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return pairTable;
	}

	//3rd Case

	//SELECTION DE LA BASE AVEC UNE CONDITION SUR UNE DES COLOGNE
	public ArrayList<Annonce> SelectedData3(String column , String attribut , String token){
		ArrayList<Annonce> a = new ArrayList<Annonce>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = "SELECT * FROM annonce WHERE "+attribut+" LIKE ?";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			connection = DriverManager.getConnection(url,user,password);
			System.out.println("Connected to the database!");

		}catch(Exception e){
			System.out.println("Connection failed");
		}
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, "%" + token + "%");
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				Annonce test = new Annonce(
						resultSet.getInt("id"),
						resultSet.getString("title"),
						resultSet.getString("description"),
						resultSet.getString("StartDate"),
						resultSet.getString("EndDate"),
						resultSet.getInt("PostsNum"),
						resultSet.getString("Secteur"),
						resultSet.getString("Fonction"),
						resultSet.getString("Experience"),
						resultSet.getString("EtudeLevel"),
						resultSet.getString("ContratDetails"),
						resultSet.getString("URL"),
						resultSet.getString("SiteName"),
						resultSet.getString("Adresse d'entreprise"),
						resultSet.getString("Site web d'entreprise"),
						resultSet.getString("Nom d'entreprise"),
						resultSet.getString("Description d'entreprise"),
						resultSet.getString("Region"),
						resultSet.getString("City"),
						resultSet.getString("Industry"),
						resultSet.getString("TraitsPersonnalite"),
						resultSet.getString("CompetencesRequises"),
						resultSet.getString("SoftSkills"),
						resultSet.getString("CompetencesRecommandees"),
						resultSet.getString("Langue"),
						resultSet.getString("NiveauLangue"),
						resultSet.getString("Salaire"),
						resultSet.getString("AvantagesSociaux"),
						resultSet.getString("Teletravail")
				);
				a.add(test);
			}
		}
		catch(Exception e) {
			System.out.println("Selection failed");
		}

		try {
			if (preparedStatement != null) preparedStatement.close();
			if (connection != null) connection.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return a;
	}

	//SELECTION DE LA BASE POUR LES DIAGRAMMES JFREECHART
	public ArrayList<Pair<String,Integer>> CountSelection3(String Column , String attribut , String token){
		Boolean tokenParsed = false;
		int i = 0 ;
		int TotalSize = DBsize();
		ArrayList<Pair<String,Integer>> pairTable = new ArrayList<Pair<String,Integer>>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String query = "SELECT "+Column+" , COUNT(*) as row_count FROM annonce WHERE "+attribut+" LIKE ? "+token+"GROUP BY "+Column+" ORDER BY row_count DESC";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			connection = DriverManager.getConnection(url,user,password);
			System.out.println("Connected to the database!");


		}catch(Exception e){
			System.out.println("Connection failed");
		}
		try {
			preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				if(i<4){
					Pair<String,Integer> element = Pair.of(resultSet.getString(0),(int) ((resultSet.getInt(1)/TotalSize)*100));
					pairTable.add(element);
					if(element.getLeft().contains(token)){
						tokenParsed = true;
					}
				}
				if(i==4){
					if(tokenParsed){
						Pair<String,Integer> element = Pair.of(resultSet.getString(0),(int) ((resultSet.getInt(1)/TotalSize)*100));
						pairTable.add(element);
						break;
					}
					else{
						tokenParsed = resultSet.getString(0).contains(token);
					}
				}
				i ++ ;
			}
			if(pairTable.size() < 5){
				Pair<String,Integer> element = Pair.of(token,0);
				pairTable.add(element);
			}
		}
		catch(Exception e) {
			System.out.println("Selection failed");
		}

		try {
			if (preparedStatement != null) preparedStatement.close();
			if (connection != null) connection.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return pairTable;
	}
}