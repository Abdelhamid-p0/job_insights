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
		            	//traitement de la page initiale 
		            	
		            	
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
		                String Secteur = phase1 [1].replace("  ", "/").replace("-", "/");
		                String Fonction = phase2 [1].replace("  ", "/").replace("-", "/").replace(",", "/");
		                String Experience = phase3 [1];
		                String NiveauEtude = phase4 [1].replace(" ", "").toUpperCase();
		                String Contract = phase5 [1];
		                String urlSite = item.select("div div a.href").text();
		                String urlScrap = item.select("div div div a.href").text();
		                i++;
		                System.out.println(jobTitle+" "+jobDescription+" "+jobDateS+" "+jobDateE+" "+jobN+" "+Secteur+" "+Fonction+" "+Experience+" "+NiveauEtude+" "+ContratDetails+" "+urlSite+" "+Teletravaille+" ");
		            }
		            if(j==26) {
		            	System.out.println(i);
		            }
		        }catch (Exception e) {
		            e.printStackTrace();
		        }
			}
		}
		
		public ArrayList<Annonce> getListeAnnonce() {
			return listeAnnonce;
		}

		public void afficheAnnonce() {
	        for (Annonce item : listeAnnonce) {
	        	System.out.println("---------------------------------------Offre d emploi-------------------------------");
	        	System.out.println("Job Title: " + item.getTitle());
	        	System.out.println("Job Location: " + item.getCity());
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