package org.academy.hibernate.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

@SuppressWarnings("serial")
@Entity
@Table(name="jpa06_hb_categorie")
public class Categorie implements Serializable {

	// champs
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@SuppressWarnings("unused")
	@Version
	private int version;

	@Column(length = 30)
	private String nom;

	// relation OneToMany non inverse (absence de mappedby) Categorie (one) -> Article (many)
	// impl�ment�e par une table de jointure Categorie_Article pour qu'� partir d'une cat�gorie
	// on puisse atteindre plusieurs articles
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Set<Article> articles = new HashSet<Article>();

	// constructeurs
	public Categorie() {
	}

	// getters et setters
	public Long getId() {
		return this.id;
	}

	@SuppressWarnings("unused")
	private void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Set<Article> getArticles() {
		return articles;
	}

	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}

	public int getVersion() {
		return version;
	}

	@SuppressWarnings("unused")
	private void setVersion(int version) {
		this.version = version;
	}

	// toString
	public String toString() {
		return String.format("Categorie[%d,%d,%s]", id, version, nom);
	}

	// association bidirectionnelle Categorie <--> Article
	public void addArticle(Article article) {
		articles.add(article);
		article.setCategorie(this);
	}
}
