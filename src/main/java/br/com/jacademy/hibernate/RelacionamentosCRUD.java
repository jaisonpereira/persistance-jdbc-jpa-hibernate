package br.com.jacademy.hibernate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.Session;

import br.com.jacademy.hibernate.entity.Conta;
import br.com.jacademy.hibernate.entity.Movimentacao;
import br.com.jacademy.hibernate.entity.TipoMovimentacao;

public class RelacionamentosCRUD {

	public static void main(String[] args) {
		insertMovimentacao();
		System.exit(0);
	}

	private static void insertMovimentacao() {
		Conta conta = new Conta();
		conta.setAgencia("00-78");
		conta.setBanco("Caixa");
		conta.setNumero("456");
		conta.setTitular("Jaison Pereira");

		Movimentacao mov = new Movimentacao();
		mov.setData(LocalDateTime.now());
		mov.setTipo(TipoMovimentacao.ENTRADA);
		mov.setValor(BigDecimal.TEN);
		mov.setConta(conta);
		mov.setDescricao("Test de NxN");

		try (Session session = HibernateUtil.openSession()) {
			session.beginTransaction();
			/**
			 * Necessario salvar conta antes
			 */
			session.saveOrUpdate(conta);

			session.saveOrUpdate(mov);
			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
