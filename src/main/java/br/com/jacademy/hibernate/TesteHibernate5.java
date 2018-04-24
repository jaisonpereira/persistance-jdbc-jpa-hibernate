package br.com.jacademy.hibernate;

import java.util.stream.Stream;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import br.com.jacademy.hibernate.entity.Conta;

/**
 * @author Jaison Pereira 24 de abr de 2018 Classe responsavél por gerenciar
 *         operacoes utilizando hibernate 5.2
 *
 *         1- criar hibernate.cfg.xml dentro da pasta resource por default caso
 *         esteja em cenario de test test/resources/ caso esteja em prod
 *         main/resources
 *
 */
public class TesteHibernate5 {
	private SessionFactory sessionFactory;

	public static void main(String[] args) {
		new TesteHibernate5().persistConta();
		System.exit(0);
	}

	private void createFactory() {
		// A SessionFactory is set up once for an application! configures
		// settings from hibernate.cfg.xml
		try {
			this.sessionFactory = new Configuration().configure().buildSessionFactory();
		} catch (Exception e) {
			// The registry would be destroyed by the SessionFactory, but we had
			// trouble building the SessionFactory
			// so destroy it manually.
			e.printStackTrace();
		}
	}

	private void persistConta() {

		Conta conta = new Conta();
		conta.setTitular("Jason Pereira");
		conta.setAgencia("0019");
		conta.setBanco("Itau");
		conta.setNumero("456-x");
		Conta conta2 = new Conta();
		conta2.setTitular("Roberta Pereira");
		conta2.setAgencia("0020");
		conta2.setBanco("Itau");
		conta2.setNumero("454-x");

		System.out.println("criamos a entity Manager factory");
		/**
		 * Criando uma factory de sessoes
		 */
		createFactory();

		System.out.println("Abrindo a sessao");
		Session session = this.sessionFactory.openSession();

		System.out.println("iniciando transacao");
		session.beginTransaction();
		System.out.println("salvando item");
		session.saveOrUpdate(conta);
		session.saveOrUpdate(conta2);
		System.out.println("commitando item");
		session.getTransaction().commit();
		System.out.println("listando itens");
		Stream<Conta> streamConta = session.createQuery("select c from Conta c").stream();

		streamConta.forEach(Conta::showConta);
		System.out.println("fechamos nossos recursos");
		System.out.println("fechando sessao");
		session.close();

	}

}
