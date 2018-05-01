package br.com.jacademy.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author jpereira Classe auxilia na criacao da session factory, abertura de
 *         sessoes
 */
public class HibernateUtil {

	private static SessionFactory sessionFactory;

	public static void main(String[] args) {
		System.exit(0);
	}

	private static void createFactory() {
		// A SessionFactory is set up once for an application! configures
		// settings from hibernate.cfg.xml
		try {
			sessionFactory = new Configuration().configure().buildSessionFactory();
		} catch (Exception e) {
			// The registry would be destroyed by the SessionFactory, but we had
			// trouble building the SessionFactory
			// so destroy it manually.
			e.printStackTrace();
		}
	}

	public static Session openSession() {

		if (sessionFactory == null) {
			createFactory();
		}
		return sessionFactory.openSession();
	}

}
