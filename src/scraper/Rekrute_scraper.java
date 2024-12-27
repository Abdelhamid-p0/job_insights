package scraper;

import model.Annonce;


import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Rekrute_scraper {

		private ArrayList<Annonce> listeAnnonce = new ArrayList<Annonce>();
		private int i=0;
		
		public Rekrute_scraper() {
		}

		public void ScraperRekrute() {
			for (int j=1;j<27;j++) {
				try {
		            String url = "https://www.rekrute.com/offres.html?p="+j+"&s=3&o=1";
		            Document document = Jsoup.connect(url).get();
		            
		            Elements jobItems = document.select("ul.job-list.job-list2#post-data > li");
		            for (Element item : jobItems) {     
		                String jobTitleAndLocation [] = item.select("h2 a.titreJob").text().split("\\|");  
		                String jobTitle = jobTitleAndLocation[0];
		                String jobLocation = jobTitleAndLocation[1];
		                String jobLocation1 = item.selectFirst("i.fa.fa-industry").parent().selectFirst("span").text();  
		                String moroccanCitiesRegex = "\\b(Casablanca|Rabat|Fès|Fez|Marrakech|Meknès|Meknes|Tétouan|Tetouan|Tanger|Tangier|Agadir|Oujda|Kénitra|Kenitra|Safi|El Jadida|Beni Mellal|Nador|Taza|Mohammedia|Khouribga|Settat|Larache|Ksar El Kebir|Essaouira|Al Hoceima|Inezgane|Taroudant|Berkane|Sidi Slimane|Sidi Kacem|Errachidia|Ouarzazate|Guelmim|Tan-Tan|Laâyoune|Laayoune|Dakhla|Chefchaouen|Midelt|Azrou|Ifrane|Tiznit|Zagora|Youssoufia|Sefrou|Boujdour)\\b";
		                Pattern pattern = Pattern.compile(moroccanCitiesRegex, Pattern.CASE_INSENSITIVE);
		                Matcher matcher = pattern.matcher(jobLocation1+jobTitle);
		                String jobLocationF = "";
		                while (matcher.find()) {
		                    jobLocationF = jobLocationF + matcher.group();
		                    break;
		                }
		                String jobDescription = item.selectFirst("i.fa.fa-binoculars").parent().selectFirst("span").text();               
		                String jobDate = item.select("em.date span").text();
		                String Date[] = jobDate.split(" ");
		                String jobDateS="",jobDateE="";
		                int jobN=0;
		                if(Date.length == 3) {
		                	jobDateS = Date[0];
		                    jobDateE = Date[1];
		                    jobN = Integer.valueOf(Date[2]);
		                }
		                if(Date.length == 2) {
		                	jobDateS = Date[0];
		                    jobDateE = Date[1];
		                    jobN = 0;
		                }
		                if(Date.length == 1 || Date.length == 0) {
		                	jobDateS = "Non Specifie";
		                    jobDateE = "Non Specifie";
		                    jobN = 0;
		                }
		                Elements Info = item.select("div ul > li");
		                String phase1 [] = Info.get(0).text().split(":");
		                String phase2 [] = Info.get(1).text().split(":");
		                String phase3 [] = Info.get(2).text().split(":");
		                String phase4 [] = Info.get(3).text().split(":");
		                String phase5 [] = Info.get(4).text().split(":");    
		                String Secteur = phase1 [1];
		                String Fonction = phase2 [1];
		                String Experience = phase3 [1];
		                String NiveauEtude = phase4 [1];
		                String Contract = phase5 [1];
		                listeAnnonce.add(new Annonce(i,jobTitle,jobLocation,jobDescription,jobDateS,jobDateE,jobN,Secteur,Fonction,Experience
		                		,NiveauEtude,Contract));
		                i++;
		            }
		        }catch (Exception e) {
		            e.printStackTrace();
		        }
			}
		}
		
		public ArrayList<Annonce> getListeAnnonce() {
			return listeAnnonce;
		}

		public void ScraperEmploiMa() {
			try {
	        	String url = "https://www.emploi.ma/recherche-jobs-maroc?page=1";
	        	Document doc =  Jsoup.connect(url).get();
	        	Elements jobOffers = doc.select("main#main-content div.page-content div div.page-main div.page-search-jobs div.page-search-jobs-wrapper div.page-search-jobs-content > div");
	        	for(Element item : jobOffers) {
	        		String title = item.select("div h3 a").text();
	        		String Description = item.select("div div p").text();
	        		Elements details = item.select("div ul li strong");
	        		String NiveauEtude ;
	        		String Experience ;
	        		String Contrat ;
	        		String Location1 ;
	        		String Location = "";
	        		String Fonction ;
	        		if(details.size() == 5) {
	        			NiveauEtude = details.get(0).text();
	            		Experience = details.get(1).text();
	            		Contrat = details.get(2).text();
	            		Location1 = details.get(3).text();
	            		String moroccanCitiesRegex = "\\b(Casablanca|Rabat|Fès|Fez|Marrakech|Meknès|Meknes|Tétouan|Tetouan|Tanger|Tangier|Agadir|Oujda|Kénitra|Kenitra|Safi|El Jadida|Beni Mellal|Nador|Taza|Mohammedia|Khouribga|Settat|Larache|Ksar El Kebir|Essaouira|Al Hoceima|Inezgane|Taroudant|Berkane|Sidi Slimane|Sidi Kacem|Errachidia|Ouarzazate|Guelmim|Tan-Tan|Laâyoune|Laayoune|Dakhla|Chefchaouen|Midelt|Azrou|Ifrane|Tiznit|Zagora|Youssoufia|Sefrou|Boujdour|Maroc)\\b";
	                    Pattern pattern = Pattern.compile(moroccanCitiesRegex, Pattern.CASE_INSENSITIVE);
	                    Matcher matcher = pattern.matcher(Location1+title);
	                    while (matcher.find()) {
	                    	Location = Location + matcher.group();
	                        break;
	                    }
	            		Fonction = details.get(4).text();
	        		}
	        		else {
	        			NiveauEtude = details.get(0).text();
	            		Experience = details.get(1).text();
	            		Contrat = details.get(2).text();
	            		Location1 = details.get(3).text();
	            		String moroccanCitiesRegex = "\\b(Casablanca|Rabat|Fès|Fez|Marrakech|Meknès|Meknes|Tétouan|Tetouan|Tanger|Tangier|Agadir|Oujda|Kénitra|Kenitra|Safi|El Jadida|Beni Mellal|Nador|Taza|Mohammedia|Khouribga|Settat|Larache|Ksar El Kebir|Essaouira|Al Hoceima|Inezgane|Taroudant|Berkane|Sidi Slimane|Sidi Kacem|Errachidia|Ouarzazate|Guelmim|Tan-Tan|Laâyoune|Laayoune|Dakhla|Chefchaouen|Midelt|Azrou|Ifrane|Tiznit|Zagora|Youssoufia|Sefrou|Boujdour)\\b";
	                    Pattern pattern = Pattern.compile(moroccanCitiesRegex, Pattern.CASE_INSENSITIVE);
	                    Matcher matcher = pattern.matcher(Location1+title);
	                    while (matcher.find()) {
	                    	Location = Location + matcher.group();
	                        break;
	                    }
	            		Fonction = "not defined";
	        		}
	        		
	        		String dateS = item.select("div time").text();
	        		String dateE = "30/12/2024";
	        		int dateN = 0;
	        		String Secteur = Fonction;	
	        		listeAnnonce.add(new Annonce(i,title,Location,Description,dateS,dateE,dateN,Secteur,Fonction,Experience
	                		,NiveauEtude,Contrat));
	                i++;
	        	}
			}
			catch(IOException e) {
				System.out.println(e);
			}
		}
		
		public void afficheAnnonce() {
	        for (Annonce item : listeAnnonce) {
	        	System.out.println("---------------------------------------Offre d emploi-------------------------------");
	        	System.out.println("Job Title: " + item.getTitle());
	        	System.out.println("Job Location: " + item.getLocation());
	        	System.out.println("Job Description: " + item.getDescription());
	        	System.out.println("Job Offer starts : " +item.getStartDate() + " Ends in : " + item.getEndDate());
	        	System.out.println("le nombre de postes : " + item.getPostsNum());
	        	System.out.println("Job Secteur: " + item.getSecteur());
	            System.out.println("Job Fonction: " + item.getFonction());
	            System.out.println("Job Experience required : " + item.getExperience());
	            System.out.println("Job Niveau etude demande : " + item.getEtudeLevel());

	        	
	        }
	        System.out.println("Le nombre d annonce scraper : "+listeAnnonce.size());
		}
			

}
