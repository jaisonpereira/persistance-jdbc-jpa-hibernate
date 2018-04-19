package br.com.jacademy.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.jacademy.hibernate.entity.Conta;

/**
 * @author jpereira testes com hibernate[
 *
 *
 *         Exemplo com HIBERNATE 4
 *
 *         1 - adicionar dependencia no pom.xml
 *
 *         2- criar pasta META-INF e criar persistance xml especificao JPA
 *
 */
public class TesteHibernate4 {

	public static void main(String[] args) {
		TesteHibernate4 teste = new TesteHibernate4();
		teste.persistConta();

	}

	private void persistConta() {

		Conta conta = new Conta();
		conta.setTitular("Jason Pereira");
		conta.setAgencia("0019");
		conta.setBanco("Itau");
		conta.setNumero("456-x");
		/**
		 * Aqui temos a fabrica de entidades
		 *
		 * e estamos criando ela apartir do nosso xml
		 *
		 * poderiamos ter diversos tipos de banco
		 */
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("financas");
		System.out.println("criamos a entity Manager factory");
		/**
		 * temos o gerenciador da unidade solicitada
		 */
		EntityManager em = emf.createEntityManager();
		System.out.println("criamos a entity Manager ");

		// Stream<Conta> streamConta = session.createQuery("select c from Conta
		// c").stream();

		// streamConta.forEach(Conta::toString);
		/**
		 * iniciando transacao
		 */
		em.getTransaction().begin();

		System.out.println("transacao");
		// persitimos nosso item
		em.persist(conta);
		System.out.println("persistimos a conta");
		// comitamos
		em.getTransaction().commit();
		System.out.println("commit realizado");

		em.close();
		emf.close();
		System.out.println("fechamos nossos recursos");

	}

}
