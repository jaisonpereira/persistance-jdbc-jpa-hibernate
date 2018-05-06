package br.com.jacademy.hibernate;

import org.hibernate.Session;

import br.com.jacademy.hibernate.entity.Conta;

/**
 * @author jpereira
 *
 *         Teste com cache de primeiro nivel
 * 
 *         o cache reutiliza o objeto em cache de primeiro nivel DESDE QUE FAÇA
 *         PARTE DA MESMA SESSION
 * 
 *         por esse motivo o OpenSessionView é interessante e o cache de segundo
 *         nivel abordara outros assuntos
 *
 *
 */
public class FirstLevelCache {

	public static void main(String[] args) {
		try {

		} finally {
			System.exit(0);

		}
	}

	/**
	 * So é executado um select
	 */
	private static void testePrimeiroNivel() {
		Session session = null;
		try {
			session = HibernateUtil.openSession("mysql");
			session.beginTransaction();
			Conta conta = session.find(Conta.class, 1);
			if (conta == null) {
				conta = new Conta();
				conta.setTitular("Jason Pereira");
				conta.setAgencia("0019");
				conta.setBanco("Itau");
				conta.setNumero("456-x");
				session.persist(conta);
				conta = session.find(Conta.class, 1);
			}
			Conta conta2 = session.find(Conta.class, 1);

			System.out.println(conta);
			System.out.println(conta2);

			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

}
