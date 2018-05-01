package br.com.jacademy.hibernate;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Session;

import br.com.jacademy.hibernate.entity.Conta;
import br.com.jacademy.hibernate.entity.Movimentacao;

/**
 * @author jpereira
 *
 *         Query language baseado em relacionament o de atributo
 *
 *         saiba mais em :
 *
 *         https://docs.jboss.org/hibernate/orm/3.5/reference/pt-BR/html/queryhql.html
 *
 *
 */
public class HQLeJPQL {

	public static void main(String[] args) {
		try {

			// selectHQL();
			// lazyLoading();
			somaValores();

		} finally {
			System.exit(0);
		}
	}

	private static void selectHQL() {
		try (Session session = HibernateUtil.openSession()) {
			StringBuilder sb = new StringBuilder();
			/**
			 * Perceba o uso de fetch evitando o problema n+1
			 */

			sb.append(" select mov from Movimentacao mov join fetch mov.conta");
			Query query = session.createQuery(sb.toString());
			List<Movimentacao> movimentacoes = query.getResultList();
			movimentacoes.forEach(m -> System.out.println(m.getConta()));
		}

	}

	/**
	 * lazy loading e o n+1
	 * 
	 * 
	 * 
	 * aqui com oneToMany ocorre produto cartesiano
	 * 
	 * para cada movimentacao ele traz uma conta
	 */
	private static void lazyLoading() {
		try (Session session = HibernateUtil.openSession()) {
			StringBuilder sb = new StringBuilder();
			/**
			 * Perceba o uso de fetch evitando o problema n+1
			 */

			sb.append(" select c from Conta c");
			// sb.append(" select distinct c from Conta c left join fetch c.movimentacoes");
			Query query = session.createQuery(sb.toString());
			List<Conta> contas = query.getResultList();

			for (Conta conta : contas) {
				System.out.println(conta);
				// System.out.println(conta.getMovimentacoes());
			}

		}

	}

	private static void somaValores() {
		try (Session session = HibernateUtil.openSession()) {
			StringBuilder sb = new StringBuilder();
			/**
			 * Perceba o uso de fetch evitando o problema n+1
			 */

			sb.append(" select sum(m.valor) from Movimentacao m");
			// sb.append(" select distinct c from Conta c left join fetch c.movimentacoes");
			// Query query = session.createQuery(sb.toString());
			TypedQuery<BigDecimal> query = session.createQuery(sb.toString(), BigDecimal.class);
			// BigDecimal valor = (BigDecimal) query.getSingleResult();
			BigDecimal valor = query.getSingleResult();

			System.out.println("a soma foi :" + valor);
		}

	}

}