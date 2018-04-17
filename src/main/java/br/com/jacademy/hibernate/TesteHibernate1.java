package br.com.jacademy.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.jacademy.hibernate.entity.Conta;

/**
 * @author jpereira testes com hibernate[
 * 
 *         1 - adicionar dependencia no pom.xml
 * 
 *         2- criar pasta META-INF e criar persistance xml especificao JPA
 * 
 */
public class TesteHibernate1 {

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

		emf.close();
		em.close();
		System.out.println("fechamos nossos recursos");

	}

	public static void main(String[] args) {
		TesteHibernate1 teste = new TesteHibernate1();
		teste.persistConta();

	}

}
