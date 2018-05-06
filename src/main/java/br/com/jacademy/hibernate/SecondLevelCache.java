package br.com.jacademy.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

import br.com.jacademy.hibernate.entity.Categoria;

/**
 * @author jpereira Habilitando cache de segundo nivel
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

		} finally {
			System.exit(0);
		}
	}

	public static void populate() {

		try (Session session = HibernateUtil.openSession()) {
			StringBuilder sb = new StringBuilder();
			sb.append(" select c from Categoria c");
			Query query = session.createQuery(sb.toString());
			List<Categoria> categorias = query.getResultList();
			// dessa forma é executado dois selects
			// if (query.getResultList() != null && !query.getResultList().isEmpty()) {
			if (categorias != null && !categorias.isEmpty()) {

				System.out.println("Existem dados na base vou executar o segundo select");

				List<Categoria> categorias2 = query.getResultList();

			} else {
				System.out.println("Não existe dados na base");
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