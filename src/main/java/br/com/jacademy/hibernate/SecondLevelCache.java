package br.com.jacademy.hibernate;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Session;

import br.com.jacademy.hibernate.entity.Categoria;

/**
 * @author jpereira Habilitando cache de segundo nivel
 *
 *
 *         Nao esta totalmente IMPLEMENTADO!!!!
 *
 *         incluir import do maven
 *
 *
 *         incluir propriedades
 *
 *         as propriedades de cache foram inseridas no hibernate.cfg.xml a
 *         classe mapeada para cache foi categoria com estrategia de cache
 *         CacheConcurrencyStrategy.READ_WRITE
 *
 *
 */
public class SecondLevelCache {

	public static void main(String[] args) {
		try {
			populate();
			populate();

		} finally {
			System.exit(0);
		}
	}

	public static void populate() {

		try (Session session = HibernateUtil.openSession()) {
			StringBuilder sb = new StringBuilder();
			sb.append(" select c from Categoria c");
			Query query = session.createQuery(sb.toString(), Categoria.class);
			query.setHint("org.hibernate.cacheable", "true");
			System.out.println("vai executar a querye \n " + sb.toString());
			List<Categoria> categorias = query.getResultList();
			// dessa forma é executado dois selects
			// if (query.getResultList() != null && !query.getResultList().isEmpty()) {
			if (categorias != null && !categorias.isEmpty()) {
				System.out.println("Existem :" + categorias.size());
			} else {
				System.out.println("Não existe dados na base");
				System.out.println("carga sera realizada");
				for (int i = 0; i < 10; i++) {
					Categoria categoria = new Categoria();
					categoria.setDescricao("Categoria n: " + i);
					session.saveOrUpdate(categoria);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}