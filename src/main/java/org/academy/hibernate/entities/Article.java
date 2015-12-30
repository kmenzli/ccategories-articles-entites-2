package org.academy.hibernate.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@SuppressWarnings("serial")
@Entity
@Table(name = "jpa06_hb_article")
public class Article implements Serializable {

	// champs
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@SuppressWarnings("unused")
	@Version
	private int version;

	@Column(length = 30)
	private String nom;

	// relation principale Article (many) -> Category (one)
	// cl� �trang�re Article(categorie_id)
	@ManyToOne()
	@JoinColumn(name = "categorie_id", nullable = false)
	private Categorie categorie;

	// constructeurs
	public Article() {
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

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
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
		return String.format("Article[%d,%d,%s]", id, version, nom);
	}

}
