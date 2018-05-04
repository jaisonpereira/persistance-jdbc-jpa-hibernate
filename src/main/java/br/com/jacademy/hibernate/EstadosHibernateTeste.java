package br.com.jacademy.hibernate;

import java.time.LocalDateTime;

import org.hibernate.Session;

import br.com.jacademy.hibernate.entity.Conta;

/**
 * @author jpereira
 *
 *
 *
 *         1- transient
 *
 *         2 - managed
 *
 *         3 (dessasociado a sessao) detached
 *
 *         4 - removed
 *
 *
 *
 */
public class EstadosHibernateTeste {

	public static void main(String[] args) {
		// PopulaConta.populate();
		try {
			EstadosHibernateTeste e = new EstadosHibernateTeste();
			// e.transientTest();
			// e.detached();
			e.testeMysqlLocked();
		} finally {
			System.exit(0);
		}
	}

	private void detached() {
		// conta esta transient
		Conta conta = new Conta();
		conta.setTitular("Jason Pereira");
		conta.setAgencia("0019");
		conta.setBanco("Itau");
		conta.setNumero("456-x");

		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			/**
			 * Aqui ela é transformada em managed
			 */
			session.persist(conta);

			conta.setTitular("Jaison P" + LocalDateTime.now());
			System.out.println(conta.getTitular());
			/**
			 * um insert foi realizado sem a necessidade de realizar um find
			 */
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		/**
		 * Agora iremos alterar essa conta em uma outra session
		 *
		 */

		Session session2 = null;
		try {
			session2 = HibernateUtil.openSession();
			session2.beginTransaction();

			conta.setTitular("Jaison P" + LocalDateTime.now());
			System.out.println(conta.getTitular());

			// aqui obtemos um erro pois estamos utilizando detached
			/**
			 * Caused by: org.hibernate.PersistentObjectException: detached
			 * entity passed to persist: br.com.jacademy.hibernate.entity.Conta
			 *
			 */
			// session2.persist(conta);
			session2.saveOrUpdate(conta);

			/**
			 * um insert foi realizado sem a necessidade de realizar um find
			 */
			session2.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session2.close();
		}

	}

	/**
	 * Teste com o estado managed do hibernate
	 *
	 * ou seja o estado gerenciado
	 *
	 * Caso esteja presente em uma transacao o objeto sera sincronizado e um
	 * update sera realizado veja o exemplo a seguir
	 *
	 *
	 */
	private void managed() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			Conta conta = session.find(Conta.class, 1);
			conta.setTitular("Jaison P" + LocalDateTime.now());
			System.out.println(conta.getTitular());
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	/**
	 * Teste com o estado managed do hibernate
	 *
	 * para remover é necessario que o objeto esteja em estado managed
	 *
	 */
	private void removed() {
		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			Conta conta = session.find(Conta.class, 1);
			session.remove(conta);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	private void testeMysqlLocked() {
		Session session = null;
		try {
			session = HibernateUtil.openSession("mysql");
			session.beginTransaction();
			Conta conta = session.find(Conta.class, 1);
			conta.setTitular("Jaison P" + LocalDateTime.now());
			System.out.println(conta.getTitular());
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	/**
	 * Teste de transient para managed com transaction
	 */
	private void transientTest() {
		// conta esta transient
		Conta conta = new Conta();
		conta.setTitular("Jason Pereira");
		conta.setAgencia("0019");
		conta.setBanco("Itau");
		conta.setNumero("456-x");

		Session session = null;
		try {
			session = HibernateUtil.openSession();
			session.beginTransaction();
			/**
			 * Aqui ela é transformada em managed
			 */
			session.persist(conta);

			conta.setTitular("Jaison P" + LocalDateTime.now());
			System.out.println(conta.getTitular());
			/**
			 * um insert foi realizado sem a necessidade de realizar um find
			 */
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

}
