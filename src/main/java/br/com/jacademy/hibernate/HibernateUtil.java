package br.com.jacademy.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.stat.Statistics;

/**
 * @author jpereira Classe auxilia na criacao da session factory, abertura de
 *         sessoes
 */
public class HibernateUtil {

	private static SessionFactory sessionFactory;

	private static final String MYSQL = "mysql";

	private static void createFactory(String dataBase) {
		// A SessionFactory is set up once for an application! configures
		// settings from hibernate.cfg.xml
		try {
			if (dataBase != null && dataBase.trim().equalsIgnoreCase(MYSQL)) {
				sessionFactory = new Configuration().configure("hibernate.cfg.mysql.xml").buildSessionFactory();
			} else {
				sessionFactory = new Configuration().configure().buildSessionFactory();
			}
		} catch (Exception e) {
			// The registry would be destroyed by the SessionFactory, but we had
			// trouble building the SessionFactory
			// so destroy it manually.
			e.printStackTrace();
			throw e;
		}
	}

	public static void main(String[] args) {
		System.exit(0);
	}

	public static Session openSession() {
		return openSession(null);
	}

	/**
	 * Abre sessao de acordo com a base selecionada
	 *
	 * @param dataBase
	 * @return
	 */
	public static Session openSession(String dataBase) {
		if (sessionFactory == null) {
			if (dataBase != null && dataBase.trim().equalsIgnoreCase(MYSQL)) {
				createFactory(MYSQL);
			} else {
				createFactory(null);
			}
		}
		return sessionFactory.openSession();
	}

	/**
	 * Estatisticas do hibernate
	 * 
	 * @return
	 */
	public static Statistics getStatistics() {
		return sessionFactory.getStatistics();
	}

}
