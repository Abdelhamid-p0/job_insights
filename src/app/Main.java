package app;

import java.util.ArrayList;

import data_analyse.NLPProcessor;
import model.Annonce;
import scraper.Rekrute_scraper;

import org.apache.commons.lang3.tuple.Pair;

//date format YYYY-MM-DD

public class Main {
	public static void main(String[] args) {
		//Rekrute_scraper a = new Rekrute_scraper();
		//a.ScraperRekrute();
		String question = "quelles sont les offres disponible dans le secteur Informatique" ;
		NLPProcessor nlp = new NLPProcessor();
		String[] tokens = question.split("\\s+");
		nlp.identifyIntent(tokens);
		Pair<String , Pair<ArrayList<Annonce>, ArrayList<Pair<String, Integer>>>> DataResponse = nlp.QuestionResponse();
		System.out.println("Reponse : "+DataResponse.getLeft());
		System.out.println("Data : "+DataResponse.getRight().getLeft());
		System.out.println("Chart : "+DataResponse.getRight().getRight());
	}
}