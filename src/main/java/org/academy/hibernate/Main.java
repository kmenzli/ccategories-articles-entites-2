package org.academy.hibernate;

import org.academy.hibernate.entities.Article;
import org.academy.hibernate.entities.Categorie;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class Main {

	// constantes
	private final static String TABLE_ARTICLE = "jpa06_hb_article";

	private final static String TABLE_CATEGORIE = "jpa06_hb_categorie";

	// Contexte de persistance
	private static EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("jpa");

	private static EntityManager em = null;

	// Objets
	private static Categorie categorieA;

	private static Categorie categorieB;

	private static Categorie categorieC;

	private static Article articleA1;

	private static Article articleA2;

	private static Article articleB1;

	public static void main(String[] args) throws Exception {
		// nettoyage base
		log("clean");
		clean();
		dumpCategories();

		// test1
		log("test1");
		test1();

		// test2
		log("test2");
		test2();

		// test3
		log("test3");
		test3();

		// test4
		log("test4");
		test4();

		// test5
		log("test5");
		test5();

		// test6
		log("test6");
		test6();

		// test7
		log("test7");
		test7();

		// test8
		log("test8");
		test8();

		// fin contexte de persistance
		if (em.isOpen())
			em.close();

		// fermeture EntityManagerFactory
		emf.close();
	}

	// r�cup�rer l'EntityManager courant
	private static EntityManager getEntityManager() {
		if (em == null || !em.isOpen()) {
			em = emf.createEntityManager();
		}
		return em;
	}

	// r�cup�rer un EntityManager neuf
	private static EntityManager getNewEntityManager() {
		if (em != null && em.isOpen()) {
			em.close();
		}
		em = emf.createEntityManager();
		return em;
	}

	// affichage contenu table Categorie
	private static void dumpCategories() {
		// contexte de persistance
		EntityManager em = getEntityManager();
		// d�but transaction
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		// affichage cat�gories
		System.out.println("[categories]");
		for (Object p : em.createQuery(
				"select c from Categorie c order by c.nom asc").getResultList()) {
			System.out.println(p);
		}
		// fin transaction
		tx.commit();
	}

	// affichage contenu table Article
	private static void dumpArticles() {
		// contexte de persistance
		EntityManager em = getEntityManager();
		// d�but transaction
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		// affichage articles
		System.out.println("[articles]");
		for (Object p : em
				.createQuery("select a from Article a order by a.nom asc")
				.getResultList()) {
			System.out.println(p);
		}
		// fin transaction
		tx.commit();
	}

	// clean BD
	private static void clean() {
		// contexte de persistance
		EntityManager em = getEntityManager();
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
		// fin transaction
		tx.commit();
	}

	// logs
	private static void log(String message) {
		System.out.println("main : ----------- " + message);
	}

	// init base
	@SuppressWarnings("unchecked")
	public static void test1() {
		// contexte de persistance
		EntityManager em = getEntityManager();
		// d�but transaction
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		// cr�er trois cat�gorie
		categorieA = new Categorie();
		categorieA.setNom("A");
		categorieB = new Categorie();
		categorieB.setNom("B");
		categorieC = new Categorie();
		categorieC.setNom("C");
		// cr�er 3 articles
		articleA1 = new Article();
		articleA1.setNom("A1");
		articleA2 = new Article();
		articleA2.setNom("A2");
		articleB1 = new Article();
		articleB1.setNom("B1");
		// les relier � leur cat�gorie
		categorieA.addArticle(articleA1);
		categorieA.addArticle(articleA2);
		categorieB.addArticle(articleB1);
		// persister les cat�gories et par cascade (insertion) les articles
		em.persist(categorieA);
		em.persist(categorieB);
		em.persist(categorieC);
		// fin transaction
		tx.commit();
		// dump des tables
		dumpCategories();
		dumpArticles();
	}

	// requ�tes base
	@SuppressWarnings("unchecked")
	public static void test2() {
		// nouveau contexte de persistance
		EntityManager em = getNewEntityManager();
		// transaction
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		// liste des categories
		List categories = em.createQuery(
				"select c from Categorie c order by c.nom asc").getResultList();
		// affichage categories
		System.out.println(categories.size() + " categorie(s) trouv�e(s) :");
		for (Object c : categories) {
			Categorie uneCategorie = (Categorie) c;
			System.out.println(uneCategorie.getNom());
		}
		// liste des articles
		List articles = em
				.createQuery("select a from Article a order by a.nom asc")
				.getResultList();
		// affichages articles
		System.out.println(articles.size() + " article(s) trouv�(s) :");
		for (Object a : articles) {
			Article unArticle = (Article) a;
			System.out.println(unArticle.getNom());
		}
		// fin transaction
		tx.commit();
	}

	// rechercher un �l�ment particulier
	public static void test3() {
		// nouveau contexte de persistance
		EntityManager em = getNewEntityManager();
		// transaction
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		// chargement cat�gorie
		Categorie categorie = em.find(Categorie.class, categorieA.getId());
		// affichage cat�gorie et ses articles associ�s
		System.out.format("Articles de la cat�gorie %s :%n", categorie);
		for (Article a : categorie.getArticles()) {
			System.out.println(a);
		}
		// fin transaction
		tx.commit();
	}

	// supprimer un article
	@SuppressWarnings("unchecked")
	public static void test4() {
		// nouveau contexte de persistance
		EntityManager em = getNewEntityManager();
		// transaction
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		// chargement articleA1
		Article newarticle1 = em.find(Article.class, articleA1.getId());
		// suppression articleA1 (aucune cat�gorie n'est actuellement charg�e)
		em.remove(newarticle1);
		// toplink : l'article doit �tre enlev� de sa cat�gorie sinon le test6
		// plante
		// hibernate : ce n'est pas n�cessaire
		newarticle1.getCategorie().getArticles().remove(newarticle1);
		// fin transaction
		tx.commit();
		// dump des articles
		dumpArticles();
	}

	// modification d'1 article
	public static void test5() {
		// nouveau contexte de persistance
		EntityManager em = getNewEntityManager();
		// transaction
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		// modification articleA2
		articleA2.setNom(articleA2.getNom() + "-");
		// articleA2 est remis dans le contexte de persistance
		em.merge(articleA2);
		// fin transaction
		tx.commit();
		// dump des articles
		dumpArticles();
	}

	// modification d'1 cat�gorie
	public static void test6() {
		// nouveau contexte de persistance
		EntityManager em = getNewEntityManager();
		// transaction
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		// chargement cat�gorie
		categorieA = em.find(Categorie.class, categorieA.getId());
		// liste des articles de la cat�gorie A
		for (Article a : categorieA.getArticles()) {
			a.setNom(a.getNom() + "-");
		}
		// modification nom cat�gorie
		categorieA.setNom(categorieA.getNom() + "-");
		// fin transaction
		tx.commit();
		// dump des cat�gories et des articles
		dumpCategories();
		dumpArticles();
	}

	// suppression d'une cat�gorie
	public static void test7() {
		// nouveau contexte de persistance
		EntityManager em = getNewEntityManager();
		// transaction
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		// persistance cat�gorieB
		Categorie mergedcategorieB = em.merge(categorieB);
		// suppression cat�gorie et par cascade (delete) les articles associ�s
		em.remove(mergedcategorieB);
		// fin transaction
		tx.commit();
		// dump des cat�gories et des articles
		dumpCategories();
		dumpArticles();
	}

	// requ�tes
	@SuppressWarnings("unchecked")
	public static void test8() {
		// nouveau contexte de persistance
		EntityManager em = getNewEntityManager();
		// transaction
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		// liste des articles de la cat�gorie A
		List articles = em
				.createQuery(
						"select a from Categorie c join c.articles a where c.nom like 'A%' order by a.nom asc")
				.getResultList();
		// affichages articles
		System.out.println("Articles de la cat�gorie A");
		for (Object a : articles) {
			System.out.println(a);
		}
		// fin transaction
		tx.commit();
	}

}
