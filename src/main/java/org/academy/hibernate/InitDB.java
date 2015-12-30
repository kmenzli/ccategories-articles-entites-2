package org.academy.hibernate;

import org.academy.hibernate.entities.Article;
import org.academy.hibernate.entities.Categorie;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class InitDB {

	// constantes
	private final static String TABLE_ARTICLE = "jpa06_hb_article";

	private final static String TABLE_CATEGORIE = "jpa06_hb_categorie";

	public static void main(String[] args) {
		// Contexte de persistance
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
		EntityManager em = null;
		// on r�cup�re un EntityManager � partir de l'EntityManagerFactory
		// pr�c�dent
		em = emf.createEntityManager();
		// d�but transaction
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		// requ�te
		Query sql1;
		// supprimer les �l�ments de la table ARTICLE
		sql1 = em.createNativeQuery("delete from " + TABLE_ARTICLE);
		sql1.executeUpdate();
		// supprimer les �l�ments de la table CATEGORIE
		sql1 = em.createNativeQuery("delete from " + TABLE_CATEGORIE);
		sql1.executeUpdate();
		// cr�er trois cat�gories
		Categorie categorieA = new Categorie();
		categorieA.setNom("A");
		Categorie categorieB = new Categorie();
		categorieB.setNom("B");
		Categorie categorieC = new Categorie();
		categorieC.setNom("C");
		// cr�er 3 articles
		Article articleA1 = new Article();
		articleA1.setNom("A1");
		Article articleA2 = new Article();
		articleA2.setNom("A2");
		Article articleB1 = new Article();
		articleB1.setNom("B1");
		// les relier � leur cat�gorie
		categorieA.addArticle(articleA1);
		categorieA.addArticle(articleA2);
		categorieB.addArticle(articleB1);
		// persister les cat�gories et par cascade (insertion) les articles
		em.persist(categorieA);
		em.persist(categorieB);
		em.persist(categorieC);
		// affichage cat�gories
		System.out.println("[categories]");
		for (Object p : em.createQuery(
				"select c from Categorie c order by c.nom asc").getResultList()) {
			System.out.println(p);
		}
		// affichage articles
		System.out.println("[articles]");
		for (Object p : em
				.createQuery("select a from Article a order by a.nom asc")
				.getResultList()) {
			System.out.println(p);
		}
		// fin transaction
		tx.commit();
		// fin EntityManager
		em.close();
		// fin EntityMangerFactory
		emf.close();
		// log
		System.out.println("termin�...");

	}
}
