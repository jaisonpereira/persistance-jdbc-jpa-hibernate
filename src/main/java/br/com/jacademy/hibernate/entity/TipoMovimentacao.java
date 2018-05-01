package br.com.jacademy.hibernate.entity;

public enum TipoMovimentacao {
	ENTRADA(0), SAIDA(1);

	Integer value;

	TipoMovimentacao(int value) {
		this.value = value;
	}

}
