package rmi_api;

import java.io.Serial;
import java.io.Serializable;

public class Annonce  implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private int id;
    private String title;
    private String description;
    private String startDate;
    private String endDate;
    private int postsNum;
    private String secteur;
    private String fonction;
    private String experience;
    private String etudeLevel;
    private String contratDetails;
    private String url;
    private String siteName;
    private String adresseEntreprise;
    private String siteWebEntreprise;
    private String nomEntreprise;
    private String descriptionEntreprise;
    private String region;
    private String city;
    private String industry;
    private String traitsPersonnalite;
    private String competencesRequises;
    private String softSkills;
    private String competencesRecommandees;
    private String langue;
    private String niveauLangue;
    private String salaire;

    public Annonce() {

    }

    @Override
    public String toString() {
        return "Annonce{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", postsNum=" + postsNum +
                ", secteur='" + secteur + '\'' +
                ", fonction='" + fonction + '\'' +
                ", experience='" + experience + '\'' +
                ", etudeLevel='" + etudeLevel + '\'' +
                ", contratDetails='" + contratDetails + '\'' +
                ", url='" + url + '\'' +
                ", siteName='" + siteName + '\'' +
                ", adresseEntreprise='" + adresseEntreprise + '\'' +
                ", siteWebEntreprise='" + siteWebEntreprise + '\'' +
                ", nomEntreprise='" + nomEntreprise + '\'' +
                ", descriptionEntreprise='" + descriptionEntreprise + '\'' +
                ", region='" + region + '\'' +
                ", city='" + city + '\'' +
                ", industry='" + industry + '\'' +
                ", traitsPersonnalite='" + traitsPersonnalite + '\'' +
                ", competencesRequises='" + competencesRequises + '\'' +
                ", softSkills='" + softSkills + '\'' +
                ", competencesRecommandees='" + competencesRecommandees + '\'' +
                ", langue='" + langue + '\'' +
                ", niveauLangue='" + niveauLangue + '\'' +
                ", salaire='" + salaire + '\'' +
                ", avantagesSociaux='" + avantagesSociaux + '\'' +
                ", teletravail='" + teletravail + '\'' +
                '}';
    }

    private String avantagesSociaux;
    private String teletravail;

    // Constructor for initializing the object
    public Annonce(int id, String title, String description, String startDate, String endDate, int postsNum,
                   String secteur, String fonction, String experience, String etudeLevel, String contratDetails,
                   String url, String siteName, String adresseEntreprise, String siteWebEntreprise, String nomEntreprise,
                   String descriptionEntreprise, String region, String city, String industry, String traitsPersonnalite,
                   String competencesRequises, String softSkills, String competencesRecommandees, String langue,
                   String niveauLangue, String salaire, String avantagesSociaux, String teletravail) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.postsNum = postsNum;
        this.secteur = secteur;
        this.fonction = fonction;
        this.experience = experience;
        this.etudeLevel = etudeLevel;
        this.contratDetails = contratDetails;
        this.url = url;
        this.siteName = siteName;
        this.adresseEntreprise = adresseEntreprise;
        this.siteWebEntreprise = siteWebEntreprise;
        this.nomEntreprise = nomEntreprise;
        this.descriptionEntreprise = descriptionEntreprise;
        this.region = region;
        this.city = city;
        this.industry = industry;
        this.traitsPersonnalite = traitsPersonnalite;
        this.competencesRequises = competencesRequises;
        this.softSkills = softSkills;
        this.competencesRecommandees = competencesRecommandees;
        this.langue = langue;
        this.niveauLangue = niveauLangue;
        this.salaire = salaire;
        this.avantagesSociaux = avantagesSociaux;
        this.teletravail = teletravail;
    }

    // Copy constructor
    public Annonce(Annonce a) {
        this.id = a.id;
        this.title = a.title;
        this.description = a.description;
        this.startDate = a.startDate;
        this.endDate = a.endDate;
        this.postsNum = a.postsNum;
        this.secteur = a.secteur;
        this.fonction = a.fonction;
        this.experience = a.experience;
        this.etudeLevel = a.etudeLevel;
        this.contratDetails = a.contratDetails;
        this.url = a.url;
        this.siteName = a.siteName;
        this.adresseEntreprise = a.adresseEntreprise;
        this.siteWebEntreprise = a.siteWebEntreprise;
        this.nomEntreprise = a.nomEntreprise;
        this.descriptionEntreprise = a.descriptionEntreprise;
        this.region = a.region;
        this.city = a.city;
        this.industry = a.industry;
        this.traitsPersonnalite = a.traitsPersonnalite;
        this.competencesRequises = a.competencesRequises;
        this.softSkills = a.softSkills;
        this.competencesRecommandees = a.competencesRecommandees;
        this.langue = a.langue;
        this.niveauLangue = a.niveauLangue;
        this.salaire = a.salaire;
        this.avantagesSociaux = a.avantagesSociaux;
        this.teletravail = a.teletravail;
    }

    // Getters and setters for all fields

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getPostsNum() {
        return postsNum;
    }

    public void setPostsNum(int postsNum) {
        this.postsNum = postsNum;
    }

    public String getSecteur() {
        return secteur;
    }

    public void setSecteur(String secteur) {
        this.secteur = secteur;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getEtudeLevel() {
        return etudeLevel;
    }

    public void setEtudeLevel(String etudeLevel) {
        this.etudeLevel = etudeLevel;
    }

    public String getContratDetails() {
        return contratDetails;
    }

    public void setContratDetails(String contratDetails) {
        this.contratDetails = contratDetails;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getAdresseEntreprise() {
        return adresseEntreprise;
    }

    public void setAdresseEntreprise(String adresseEntreprise) {
        this.adresseEntreprise = adresseEntreprise;
    }

    public String getSiteWebEntreprise() {
        return siteWebEntreprise;
    }

    public void setSiteWebEntreprise(String siteWebEntreprise) {
        this.siteWebEntreprise = siteWebEntreprise;
    }

    public String getNomEntreprise() {
        return nomEntreprise;
    }

    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }

    public String getDescriptionEntreprise() {
        return descriptionEntreprise;
    }

    public void setDescriptionEntreprise(String descriptionEntreprise) {
        this.descriptionEntreprise = descriptionEntreprise;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getTraitsPersonnalite() {
        return traitsPersonnalite;
    }

    public void setTraitsPersonnalite(String traitsPersonnalite) {
        this.traitsPersonnalite = traitsPersonnalite;
    }

    public String getCompetencesRequises() {
        return competencesRequises;
    }

    public void setCompetencesRequises(String competencesRequises) {
        this.competencesRequises = competencesRequises;
    }

    public String getSoftSkills() {
        return softSkills;
    }

    public void setSoftSkills(String softSkills) {
        this.softSkills = softSkills;
    }

    public String getCompetencesRecommandees() {
        return competencesRecommandees;
    }

    public void setCompetencesRecommandees(String competencesRecommandees) {
        this.competencesRecommandees = competencesRecommandees;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public String getNiveauLangue() {
        return niveauLangue;
    }

    public void setNiveauLangue(String niveauLangue) {
        this.niveauLangue = niveauLangue;
    }

    public String getSalaire() {
        return salaire;
    }

    public void setSalaire(String salaire) {
        this.salaire = salaire;
    }

    public String getAvantagesSociaux() {
        return avantagesSociaux;
    }

    public void setAvantagesSociaux(String avantagesSociaux) {
        this.avantagesSociaux = avantagesSociaux;
    }

    public String getTeletravail() {
        return teletravail;
    }

    public void setTeletravail(String teletravail) {
        this.teletravail = teletravail;
    }
}
