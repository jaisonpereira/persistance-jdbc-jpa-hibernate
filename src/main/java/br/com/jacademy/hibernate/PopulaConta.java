package br.com.jacademy.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.com.jacademy.hibernate.entity.Conta;

public class PopulaConta {

	/**
	 * 
	 */
	public static void populate() {
		/**
		 * com try with resources o close é automatico
		 */
		List<Conta> contas = new ArrayList<>();
		for (int i = 10; i < 15; i++) {
			Conta conta = new Conta();
			conta.setTitular("Jason Pereira" + i);
			conta.setAgencia("001" + i);
			conta.setBanco("Itau");
			conta.setNumero("45" + i + "-x");
			contas.add(conta);
		}

		try (Session session = HibernateUtil.openSession()) {
			try {
				session.beginTransaction();
				for (Conta conta : contas) {
					session.saveOrUpdate(conta);
				}
				session.getTransaction().commit();
			} catch (HibernateException e) {
				session.getTransaction().rollback();
				throw e;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
