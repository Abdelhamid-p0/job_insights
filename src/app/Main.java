package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import db.Database;
import model.Annonce;
import scraper.Rekrute_scraper;

//date format YYYY-MM-DD

public class Main {
	public static void main(String[] args) {
		
		//1. Scrapping methode
		
		Rekrute_scraper s = new Rekrute_scraper();
		//scraper le site rekrute
		s.ScraperRekrute();
		//scraper le site emploi.ma
		//s.ScraperEmploiMa();
		//afficher les annonces scrapper
		s.afficheAnnonce();
		
		//2.Insertion des annonces dans la base
		
		Database b = new Database();
		b.insertData(s.getListeAnnonce());
		
		//3.recuperation des annonces de la base
		ArrayList<Annonce> a = b.selectData();
		
		//4. Creation de app
		
		JFrame frame = new JFrame("Aji tkhdm");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout(10, 10));
        Image icon = Toolkit.getDefaultToolkit().getImage(
                "C:\\Users\\najib\\eclipse-workspace\\maven-demo\\ressources\\briefcase.png"
            );
        frame.setIconImage(icon);
        
        //Bar de filtrage
        JPanel filtre = new JPanel(new GridLayout(4, 2, 10, 10));
        filtre.setBorder(new EmptyBorder(10, 10, 10, 10));
        filtre.setBackground(Color.LIGHT_GRAY);
        
        // Location filter
        JLabel locationLabel = new JLabel("Location:");
        JComboBox<String> locationFilter = new JComboBox<>(new String[]{"All", "Agadir", "Casablanca", "El Jadida", "Fes", "Ifrane", "Kenitra", "Marrakech", "Meknes", "Nador", 
        		"Oujda", "Rabat", "Sale", "Tangier", "Tétouan", "Tiznit", "Guercif"
        });
        filtre.add(locationLabel);
        filtre.add(locationFilter);
        
        // Secteur filtrage 
        JLabel SecteurLabel = new JLabel("Secteur:");
        JComboBox<String> SecteurFilter = new JComboBox(new String[]{"All","Agence pub", "Agroalimentaire", "Assurance", "Automobile", "Banque", "BTP", "Centre d'appel", "Chimie", 
        		"Communication", "Comptabilité", "Conseil", "Distribution", "Electro-mécanique", "Electronique", "Enseignement", "Energie", "Etudes", "Extraction", "Formation", 
        		"Gaz", "Génie Civil", "Génie Civil", "Hôtellerie", "Indifférent", "Informatique", "Marketing Direct", "Offshoring", "Papier", "Pharmacie", "Telecom", "Spatial"
        });
        filtre.add(SecteurLabel);
        filtre.add(SecteurFilter);
        
        // Etude filtrage 
        JLabel EtudeLabel = new JLabel("Niveau d'etude:");
        JComboBox<String> EtudeFilter = new JComboBox(new String[]{"All","Bac +1", "Bac +2","Bac +3", "Bac +4","Bac +5 et plus"});
        filtre.add(EtudeLabel);
        filtre.add(EtudeFilter);
        
        // Experience filtrage 
        JLabel ExperienceLabel = new JLabel("Niveau d'experience:");
        JComboBox<String> ExperienceFilter = new JComboBox(new String[]{"All","Moins de 1 an","De 1 à 3 ans ", "De 3 à 5 ans ", "De 5 à 10 ans ", 
        		"De 10 à 20 ans","Débuteur", "Plus de 20 ans"});
        filtre.add(ExperienceLabel);                            
        filtre.add(ExperienceFilter);
        
        //Annonces
        
        DefaultListModel<String> jobListModel = new DefaultListModel<>();
        JList<String> jobListView = new JList<>(jobListModel);
        JScrollPane scrollPane = new JScrollPane(jobListView);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        frame.add(scrollPane, BorderLayout.CENTER);

        // Update job list based on filters
        ActionListener filterListener = e -> {
            String selectedLocation = locationFilter.getSelectedItem().toString();
            String selectedSecteur = SecteurFilter.getSelectedItem().toString();
            String selectedEtude = EtudeFilter.getSelectedItem().toString();
            String selectedExperience = ExperienceFilter.getSelectedItem().toString();

            jobListModel.clear();

            for (Annonce job : a) {
                boolean matchesLocation = selectedLocation.equals("All") || job.getLocation().contains(selectedLocation);
                boolean matchesSecteur = selectedSecteur.equals("All")|| job.getSecteur().contains(selectedSecteur);
                boolean matchesEtude = selectedEtude.equals("All")|| job.getEtudeLevel().contains(selectedEtude);
                boolean matchesExperience = selectedExperience.equals("All")|| job.getExperience().contains(selectedExperience);
                
                
                if (matchesLocation && matchesSecteur&&matchesEtude&&matchesExperience) {
                    jobListModel.addElement("Title : "+job.getTitle());
                    jobListModel.addElement("Location : "+job.getLocation());
                    jobListModel.addElement("Description : "+job.getDescription());
                    jobListModel.addElement("Experience : "+job.getExperience());
                    jobListModel.addElement("Secteur : "+job.getSecteur());
                    jobListModel.addElement("Niveau d'etude : "+job.getEtudeLevel());
                    jobListModel.addElement("Fonction : "+job.getFonction());
                    jobListModel.addElement("Details du contrat : "+job.getContracDetails());
                    jobListModel.addElement("---------------------------------------------------------------------------------------------"
                    		+ "------------------------------------------------------------------------------------------------------------"
                    		+ "------------------------------------------------------------------------------------------------------------");
                }
            }
        };

        locationFilter.addActionListener(filterListener);
        SecteurFilter.addActionListener(filterListener);
        EtudeFilter.addActionListener(filterListener);
        ExperienceFilter.addActionListener(filterListener);
        
        filterListener.actionPerformed(null);

        frame.add(filtre, BorderLayout.NORTH);


     // Footer Section
        JPanel footerPanel = new JPanel(new FlowLayout());
        Label footerLabel = new Label("© 2024 Copyright a m3lm.");
        footerPanel.add(footerLabel);
        frame.add(footerPanel, BorderLayout.SOUTH);
        
        // Window close action
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        frame.setVisible(true);
    }
}