package br.com.jacademy.hibernate;

/**
 * @author jpereira
 *
 *         Hibernate Statistics
 *
 *
 *         1 - habilitar propriedade "hibernate.generate_statistics", "true"
 *
 *
 *
 */
public class HibernateStatistics {

	public static void main(String[] args) {
		try {
			testeStatistica();
		} finally {
			System.exit(0);
		}
	}

	private static void testeStatistica() {
		HibernateUtil.openSession();
		System.out.println("Hit " + HibernateUtil.getStatistics().getQueryCacheHitCount());
		System.out.println("Miss " + HibernateUtil.getStatistics().getQueryCacheMissCount());
		System.out.println("Conexoes " + HibernateUtil.getStatistics().getConnectCount());
		System.out.println("Queryes em execucao " + HibernateUtil.getStatistics().getQueryExecutionCount());
		System.out.println(
				"Queryes Lentas  executadas " + HibernateUtil.getStatistics().getQueryExecutionMaxTimeQueryString());
		System.out.println("Todas as queryes executadas ");
		for (String query : HibernateUtil.getStatistics().getQueries()) {
			System.out.println(query);
		}
		System.out.println("Transacoes executadas " + HibernateUtil.getStatistics().getTransactionCount());
	}

}
