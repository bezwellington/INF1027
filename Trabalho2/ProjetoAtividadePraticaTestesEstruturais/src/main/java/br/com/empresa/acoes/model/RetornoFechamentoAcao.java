package br.com.empresa.acoes.model;

import java.math.BigDecimal;

public class RetornoFechamentoAcao {

	private final FechamentoAcao fechamento;
	private final BigDecimal retorno;
	
	public RetornoFechamentoAcao(final FechamentoAcao fechamento, final BigDecimal retorno) {
		this.fechamento = fechamento;
		this.retorno = retorno;
	}

	public FechamentoAcao getFechamento() {
		return fechamento;
	}

	public BigDecimal getRetorno() {
		return retorno;
	}
}
