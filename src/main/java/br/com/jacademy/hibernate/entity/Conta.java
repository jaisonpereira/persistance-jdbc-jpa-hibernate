package br.com.jacademy.hibernate.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author jpereira notation Entity identifica que é uma entidade os imports sao
 *         da JPA
 */
@Entity
public class Conta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String titular;
	private String numero;
	private String banco;
	private String agencia;

	public String getAgencia() {
		return this.agencia;
	}

	public String getBanco() {
		return this.banco;
	}

	public Integer getId() {
		return this.id;
	}

	public String getNumero() {
		return this.numero;
	}

	public String getTitular() {
		return this.titular;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	@Override
	public String toString() {
		return String.format("[id: %d Titular: %s Agencia: %s Banco: %s Numero %s  ]", this.id, this.titular, this.agencia, this.banco, this.numero);
	}

}
