package data_analyse;

import java.util.ArrayList;
import java.util.Arrays;

import model.Annonce;
import opennlp.tools.tokenize.SimpleTokenizer;
import org.apache.commons.lang3.tuple.Pair;


public class NLPProcessor {
	private SimpleTokenizer tokenizer;
	private Identifier Identifier;
	private ArrayList<Pair<String,String>> tableIdentifiers = new ArrayList<Pair<String,String>>() ;

	public NLPProcessor() {
		tokenizer = SimpleTokenizer.INSTANCE;
		this.Identifier = new Identifier(
				Arrays.asList("Agadir", "Casablanca", "ElJadida", "Fes", "Ifrane", "Kenitra", "Marrakech", "Meknes", "Nador", "Oujda", "Rabat", "Sale", "Tangier", "Tétouan", "Tiznit", "Guercif"),
				Arrays.asList("Agence pub", "Agroalimentaire", "Assurance", "Automobile", "Banque", "BTP", "Centre d'appel", "Chimie", "Communication", "Comptabilité", "Conseil", "Distribution", "Electro-mécanique", "Electronique", "Enseignement", "Energie", "Etudes", "Extraction", "Formation", "Gaz", "Génie Civil", "Hôtellerie", "Indifférent", "Internet" , "Informatique", "Marketing Direct", "Offshoring", "Papier", "Pharmacie", "Telecom", "Spatial"),
				Arrays.asList("Bac","Bac+1","Bac+2","Bac+3","Bac+4","Bac+5"),
				Arrays.asList("emploi","rekrute","m-job"),
				Arrays.asList("Français","Anglais","Espagol","Allemand","Italien","Portugais","Chinois","Japonais","Arabe","Russe"),
				Arrays.asList("Plus","plus","meilleur","Meilleur","Tendance","tendance","necessaire"),
				Arrays.asList("competence","fonctions","fonction","experience","experiences","secteur","Secteur","ville","Ville",
						"site","page","niveau","etudeLevel","education","salaire"),
				Arrays.asList("7daydi"),
				Arrays.asList("Ventes","Droit","Programmation","Commnication","Finance","Entrepreneuriat")
		);
	}

	//Tokenization
	public String[] tokenizeQuery(String query) {
		return tokenizer.tokenize(query);
	}

	//Intent Recognition
	public void identifyIntent(String[] tokens) {
		//Columns identification
		String identifier = Identifier.identifyIdentifiers(tokens);
		if (!identifier.equals("none")) {
			if(identifier.equals("competence") || identifier.equals("competences")){
				identifier = "CompetencesRequises";
			}
			if(identifier.equals("fonctions") || identifier.equals("fonction")){
				identifier = "Fonction";
			}
			if(identifier.equals("experience") || identifier.equals("experiences")){
				identifier = "Experience";
			}
			if(identifier.equals("secteur") || identifier.equals("Secteur")){
				identifier = "Secteur";
			}
			if(identifier.equals("ville") || identifier.equals("Ville")){
				identifier = "City";
			}
			if(identifier.equals("site") || identifier.equals("page")){
				identifier = "SiteName";
			}
			if(identifier.equals("niveau") || identifier.equals("etudeLevel") || identifier.equals("education")){
				identifier = "EtudeLevel";
			}
			if(identifier.equals("salaire")){
				identifier = "Salaire";
			}
			Pair<String,String> pair = Pair.of("Column",identifier);
			this.tableIdentifiers.add(pair);
		}

		//Les mots cles des inputs
		//Plus requestes
		Boolean plus = Identifier.identifyPlusSecteur(tokens);
		if(plus){
			Pair<String,String> pair = Pair.of("Requete","Plus");
			this.tableIdentifiers.add(pair);
		}

		//Second Columns identification
		String identifier2 = Identifier.identifyIdentifiers2(tokens);
		System.out.println(identifier2);
		if (!identifier2.equals("none")) {
			if(identifier2.equals("competence") || identifier2.equals("competences")){
				identifier2 = "CompetencesRequises";
			}
			if(identifier2.equals("fonctions") || identifier2.equals("fonction")){
				identifier2 = "Fonction";
			}
			if(identifier2.equals("experience") || identifier2.equals("experiences")){
				identifier2 = "Experience";
			}
			if(identifier2.equals("ville") || identifier2.equals("Ville")){
				identifier2 = "City";
			}
			if(identifier2.equals("site") || identifier2.equals("page")){
				identifier2 = "SiteName";
			}
			if(identifier2.equals("secteur") || identifier2.equals("Secteur")){
				identifier2 = "Secteur";
			}
			if(identifier2.equals("niveau") || identifier2.equals("etudeLevel") || identifier2.equals("education")){
				identifier2 = "EtudeLevel";
			}
			if(identifier2.equals("salaire")){
				identifier2 = "Salaire";
			}
			if(!identifier2.equals(identifier)){
				Pair<String,String> pair = Pair.of("Column2",identifier2);
				this.tableIdentifiers.add(pair);
			}

		}

		//Values Identifier
		//secteur identifier
		String sector = Identifier.identifySector(tokens);
		if (!sector.equals("none")) {
			Pair<String,String> pair = Pair.of("Values",sector);
			this.tableIdentifiers.add(pair);
		}

		//experience identifier
		String experience = Identifier.identifyExperience(tokens);
		if (!experience.equals("none")) {
			Pair<String,String> pair = Pair.of("Values",experience);
			this.tableIdentifiers.add(pair);
		}

		//etudeLevel identifier
		String etudeLevel = Identifier.identifyEtudeLevel(tokens);
		if (!etudeLevel.equals("none")) {
			System.out.println(etudeLevel);
			Pair<String,String> pair = Pair.of("Values",etudeLevel);
			this.tableIdentifiers.add(pair);
		}

		//SiteName identifier
		String SiteName = Identifier.identifySiteName(tokens);
		if (!SiteName.equals("none")) {
			Pair<String,String> pair = Pair.of("Values",SiteName);
			this.tableIdentifiers.add(pair);
		}

		//Cities identifier
		String city = Identifier.identifyCity(tokens);
		if (!city.equals("none")) {
			Pair<String,String> pair = Pair.of("Values",city);
			this.tableIdentifiers.add(pair);
		}

		//CompetenceRequise identifier
		String competence = Identifier.identifyCompetence(tokens);
		if (!competence.equals("none")) {
			Pair<String,String> pair = Pair.of("Values",competence);
			this.tableIdentifiers.add(pair);
		}
		//salaireIdentifier
		String salaire1 = Identifier.identifySalaire(tokens);
		if (!salaire1.equals("none")) {
			Pair<String,String> pair = Pair.of("Values",salaire1);
			this.tableIdentifiers.add(pair);
		}
		String salaire2 = Identifier.identifySalaire2(tokens);
		if (!salaire2.equals("none")) {
			Pair<String,String> pair = Pair.of("Values",salaire2);
			this.tableIdentifiers.add(pair);
		}

		System.out.println(this.tableIdentifiers);
	}

	//intents association with database
	public Pair<String,Pair<ArrayList<Annonce>,ArrayList<Pair<String, Integer>>>> QuestionResponse(){
		ResultsDataHandler backend = new ResultsDataHandler();
		//Les donnes a rendre au front end
		ArrayList<Annonce> data =  new ArrayList<Annonce>();
		ArrayList<Pair<String, Integer>> chart = new ArrayList<Pair<String, Integer>>();
		String ChatResponse = "";
		System.out.println("Traitement des tokens begins .");
		int size = this.tableIdentifiers.size();
		ArrayList<Pair<String,String>> table = this.tableIdentifiers;
		if(size == 1 && table.get(0).getLeft().equals("Values")){
			String value = table.getFirst().getRight();
			String column = "none";
			for(String i : this.Identifier.cities){
				if(value == i){
					column = "City";
				}
			}
			for(String i : this.Identifier.sectors){
				if(value == i){
					column = "Secteur";
				}
			}
			for(String i : this.Identifier.siteName){
				if(value == i){
					column = "SiteName";
				}
			}
			Pair<String,String> pair = Pair.of("Column",column);
			this.tableIdentifiers.add(pair);
			data = 	backend.Data(column,value);
			chart = backend.Chart(column,value);
			int pourcentage = 0 ;
			for(Pair<String , Integer> item : chart){
				if(item.getLeft() == table.get(0).getLeft()){
					pourcentage = item.getRight();
				}
			}
			ChatResponse = "Il se voit que vous voulez savoir les offres a propos de/du "+table.get(0).getRight()+" "+table.get(1).getRight()+
					"Donc le pourcentage des offres dans ce "+table.get(0).getRight()+" est : "+pourcentage+" est vous aurez si desous un chart sur " +
					"les "+table.get(0).getRight()+" demande dans le marche de travaille et les annonce a propos du "+table.get(0).getRight()+" "+table.get(1).getRight()+" :) .";
		} else if(size == 2 && table.get(0).getLeft().equals("Column") && table.get(1).getLeft().equals("Values")){
			data = backend.Data(table.get(0).getRight() , table.get(1).getRight());
			chart = backend.Chart(table.get(0).getRight() , table.get(1).getRight());
			int pourcentage = 0 ;
			for(Pair<String , Integer> item : chart){
				if(item.getLeft().contains(table.get(1).getRight())){
					pourcentage = item.getRight();
				}
			}
			ChatResponse = "Il se voit que vous voulez savoir les offres a propos de/du "+table.get(0).getRight()+" "+table.get(1).getRight()+
					"Donc le pourcentage des offres dans ce "+table.get(0).getRight()+" est : "+pourcentage+" est vous aurez si desous un chart sur " +
					"les "+table.get(0).getRight()+" demande dans le marche de travaille et les annonce a propos du "+table.get(0).getRight()+" "+table.get(1).getRight()+" :) .";
		}
		else if (size == 4 && table.get(0).getLeft().equals("Column") && table.get(1).getLeft().equals("Requete")
			&& table.get(2).getLeft().equals("Column2") && table.get(3).getLeft().equals("Values")) {
			if (table.get(1).getRight().equals("Plus")) {
				data = backend.Data2(table.get(0).getRight(), table.get(2).getRight(), table.get(3).getRight());
				chart = backend.Chart2(table.get(0).getRight(), table.get(2).getRight(), table.get(3).getRight());
				int pourcentage = 0 ;
				for(Pair<String , Integer> item : chart){
					if(item.getLeft() == table.get(0).getLeft()){
						pourcentage = item.getRight();
					}
				}
				ChatResponse = "Il se voit que vous voulez savoir les "+table.get(0).getRight()+" les plus demande dans le "+table.get(2).getRight()+" "+ table.get(3).getRight()+
						" Vous aurez si desous un chart qui demontre les "+table.get(0).getRight()+" les plus demande dans "+table.get(2).getRight()+" "+ table.get(3).getRight()+
						"est Par analyse de data en vois que c'est "+chart.get(0).getLeft()+"qui est plus demande :) .";
			}
		}

		Pair<String,Pair<ArrayList<Annonce>,ArrayList<Pair<String, Integer>>>> results = Pair.of(ChatResponse , Pair.of(data, chart)) ;
		return results;
	}
}
