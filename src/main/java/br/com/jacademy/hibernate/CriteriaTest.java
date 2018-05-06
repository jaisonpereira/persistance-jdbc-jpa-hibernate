package br.com.jacademy.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import br.com.jacademy.hibernate.entity.Movimentacao;

/**
 * @author jpereira
 *
 *         teste com criteria e restriction
 *
 *         Sessao pode ser usada com OpenEntityManagerInView
 *
 *         OpenSessionInView
 * 
 *         para definir o ciclo de vida da sessao
 * 
 * 
 * 
 *         Lock Pessimista
 * 
 *         LockOtimista
 * @Version
 * 
 * 
 *
 *
 */
public class CriteriaTest {

	public static void main(String[] args) {
		try {
			criteriaTestSimple("mov jaison", 1);
		} finally {
			System.exit(0);
		}
	}

	private static List<Movimentacao> criteriaTestSimple(String descricao, Integer idConta) {
		try (Session session = HibernateUtil.openSession()) {

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Movimentacao> query = builder.createQuery(Movimentacao.class);
			// instancia de root para tracar o caminho para cada movimentacao
			query.from(Movimentacao.class);
			Root<Movimentacao> root = query.from(Movimentacao.class);
			// utilizando path
			Path<String> pathDescricao = root.<String>get("descricao");
			// relacionando conta com movimentacao
			// Path<Integer> pathContaId = root.<Conta>get("conta").<Integer>get("id");

			List<Predicate> predicates = new ArrayList<Predicate>();

			// relacionamento com join em objeto one to many ou many to many
			// caso FOSSE NECESSARIO

			// Path<Integer> pathCategoriaId = root.join("categorias").<Integer>get("id");

			// criando predicato
			if (descricao != null && !descricao.trim().isEmpty()) {
				Predicate predicateDescricao = builder.like(pathDescricao, descricao);
				predicates.add(predicateDescricao);
			}
			// if (idConta != null) {
			// Predicate predicateConta = builder.equal(pathContaId, idConta);
			// predicates.add(predicateConta);
			// }

			// query.where(predicates.toArray(new Predicate[predicates.size()]));

			TypedQuery<Movimentacao> typedQuery = session.createQuery(query);
			return typedQuery.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

}
