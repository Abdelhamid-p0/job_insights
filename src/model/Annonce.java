package model;

public class Annonce {
	
	private int id ;
	private String title ;
	private String Location ;
	private String Description ;
	private String StartDate ;
	private String EndDate ;
	private int PostsNum ;
	private String Secteur ;
	private String Fonction ;
	private String Experience ;
	private String EtudeLevel ;
	private String ContracDetails ;
	public Annonce(int id, String title, String location, String description, String startDate, String endDate,
			int postsNum, String secteur, String fonction, String experience, String etudeLevel,
			String contracDetails) {
		this.id = id;
		this.title = title;
		Location = location;
		Description = description;
		StartDate = startDate;
		EndDate = endDate;
		PostsNum = postsNum;
		Secteur = secteur;
		Fonction = fonction;
		Experience = experience;
		EtudeLevel = etudeLevel;
		ContracDetails = contracDetails;
	}
	
	public Annonce(Annonce a) {
		this.id = a.id;
		this.title = a.title;
		Location = a.Location;
		Description = a.Description;
		StartDate = a.StartDate;
		EndDate = a.EndDate;
		PostsNum = a.PostsNum;
		Secteur = a.Secteur;
		Fonction = a.Fonction;
		Experience = a.Experience;
		EtudeLevel = a.EtudeLevel;
		ContracDetails = a.ContracDetails;
	}
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
	public String getLocation() {
		return Location;
	}
	public void setLocation(String location) {
		Location = location;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getStartDate() {
		return StartDate;
	}
	public void setStartDate(String startDate) {
		StartDate = startDate;
	}
	public String getEndDate() {
		return EndDate;
	}
	public void setEndDate(String endDate) {
		EndDate = endDate;
	}
	public int getPostsNum() {
		return PostsNum;
	}
	public void setPostsNum(int postsNum) {
		PostsNum = postsNum;
	}
	public String getSecteur() {
		return Secteur;
	}
	public void setSecteur(String secteur) {
		Secteur = secteur;
	}
	public String getFonction() {
		return Fonction;
	}
	public void setFonction(String fonction) {
		Fonction = fonction;
	}
	public String getExperience() {
		return Experience;
	}
	public void setExperience(String experience) {
		Experience = experience;
	}
	public String getEtudeLevel() {
		return EtudeLevel;
	}
	public void setEtudeLevel(String etudeLevel) {
		EtudeLevel = etudeLevel;
	}
	public String getContracDetails() {
		return ContracDetails;
	}
	public void setContracDetails(String contracDetails) {
		ContracDetails = contracDetails;
	}
	@Override
	public String toString() {
		return "annonce [id=" + id + ", title=" + title + ", Location=" + Location + ", Description=" + Description
				+ ", StartDate=" + StartDate + ", EndDate=" + EndDate + ", PostsNum=" + PostsNum + ", Secteur="
				+ Secteur + ", Fonction=" + Fonction + ", Experience=" + Experience + ", EtudeLevel=" + EtudeLevel
				+ ", ContracDetails=" + ContracDetails + "]";
	}
	
	public void affiche() {
		System.out.println(this.toString());
	}
	

}
