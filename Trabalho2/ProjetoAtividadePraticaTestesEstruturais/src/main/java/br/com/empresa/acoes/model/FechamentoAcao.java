package br.com.empresa.acoes.model;

import java.math.BigDecimal;

import org.joda.time.LocalDate;

public class FechamentoAcao {
	
	private final Acao acao; 
	private final LocalDate data;
	private final BigDecimal fechamento;
	private final long volume;
	
	public FechamentoAcao(final Acao acao, final LocalDate data, final BigDecimal fechamento, final long volume) {
		this.acao = acao;
		this.data = data;
		this.fechamento = fechamento;
		this.volume = volume;
	}

	public LocalDate getData() {
		return data;
	}

	public BigDecimal getValorFechamento() {
		return fechamento;
	}

	public long getVolume() {
		return volume;
	}

	public Acao getAcao() {
		return acao;
	}
}
