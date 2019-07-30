package br.com.empresa.acoes.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;

public class Acao implements Comparable<Acao> {
	
	private final String id;
	private final LinkedHashSet<FechamentoAcao> fechamentos;
	private final ArrayList<RetornoFechamentoAcao> retornosDeFechamentos;
	
	private FechamentoAcao fechamentoMaximo = null;
	private FechamentoAcao fechamentoMinimo = null;
	private FechamentoAcao ultimoFechamentoInserido = null;
	
	public Acao(final String id) {
		assert (id != null && !id.equals(""));
		
		this.id = id;
		this.fechamentos = new LinkedHashSet<FechamentoAcao>();
		this.retornosDeFechamentos = new ArrayList<RetornoFechamentoAcao>();
	}

	/**
	 * Adiciona fechamento desta acao em uma determinada data.
	 * @param fechamento
	 * @return
	 */
	public boolean addFechamento(final FechamentoAcao fechamento) {
		assert (fechamento != null);
		assert (fechamento.getAcao() != null);
		assert (fechamento.getAcao().equals(this));
		
		boolean incluiu = fechamentos.add(fechamento);
		
		if (incluiu) {
			limpaFechamentosMaximoMinimo();
			updateRetornos(fechamento);
			ultimoFechamentoInserido = fechamento;
		}
		
		return incluiu;
	}
	

	public FechamentoAcao getFechamentoMaximo() {
		if (fechamentoMaximo == null) {
			List<FechamentoAcao> fechamentosOrdenados = getFechamentosOrdenadosPorValor();
			fechamentoMaximo = fechamentosOrdenados.get(fechamentosOrdenados.size() - 1);
		}
		
		return fechamentoMaximo;
	}

	public FechamentoAcao getFechamentoMinimo() {
		if (fechamentoMinimo == null) {
			List<FechamentoAcao> fechamentosOrdenados = getFechamentosOrdenadosPorValor();
			fechamentoMinimo = fechamentosOrdenados.get(0);
		}
		
		return fechamentoMinimo;
	}
	
	public RetornoFechamentoAcao getFechamentoMaiorRetorno() {
		List<RetornoFechamentoAcao> retornosOrdenados = getRetornosOrdenadosPorValor();
		
		return retornosOrdenados.get(retornosOrdenados.size() - 1);
	}
	
	public RetornoFechamentoAcao getFechamentoMenorRetorno() {
		List<RetornoFechamentoAcao> retornosOrdenados = getRetornosOrdenadosPorValor();
		
		return retornosOrdenados.get(0);
	}
	
	public double getVolumeMedio() {
		long numeroDeFechamentos = 0;
		long somaVolumes = 0;
		for (FechamentoAcao fechamentoAcao : fechamentos) {
			long volume = fechamentoAcao.getVolume();
			if (volume != 0) {
				numeroDeFechamentos++;
				somaVolumes += volume;
			}
		}
		
		if (numeroDeFechamentos > 0)
			return somaVolumes / (double) numeroDeFechamentos;
		
		return 0d;
	}
	
	public String getId() {
		return id;
	}

	@Override
	public int compareTo(Acao other) {
		return getId().compareTo(other.getId());
	}	
	
	private void limpaFechamentosMaximoMinimo() {
		fechamentoMaximo = null;
		fechamentoMinimo = null;
	}
	
	private List<FechamentoAcao> getFechamentosOrdenadosPorValor() {
		List<FechamentoAcao> fechamentosOrdenados = new ArrayList<FechamentoAcao>(fechamentos);
		
		Collections.sort(fechamentosOrdenados, new Comparator<FechamentoAcao>() {
			@Override public int compare(FechamentoAcao f1, FechamentoAcao f2) {
				return f1.getValorFechamento().compareTo(f2.getValorFechamento());
			}
		});
		return fechamentosOrdenados;
	}
	
	private void updateRetornos(final FechamentoAcao fechamento) {
		if (ultimoFechamentoInserido != null) {
			FechamentoAcao fechamentoDiaAnterior = ultimoFechamentoInserido;
			BigDecimal valorRetorno = fechamento.getValorFechamento().divide(fechamentoDiaAnterior.getValorFechamento(), BigDecimal.ROUND_HALF_EVEN);
			valorRetorno = valorRetorno.subtract(BigDecimal.ONE);
			
			retornosDeFechamentos.add(new RetornoFechamentoAcao(fechamento, valorRetorno));
		} else {
			//Assumindo que o retorno do primeiro fechamento seja o valor do fechamento 
			retornosDeFechamentos.add(new RetornoFechamentoAcao(fechamento, fechamento.getValorFechamento()));
		}
	}	

	private List<RetornoFechamentoAcao> getRetornosOrdenadosPorValor() {
		List<RetornoFechamentoAcao> retornosOrdenados = new ArrayList<RetornoFechamentoAcao>(retornosDeFechamentos);
		
		Collections.sort(retornosOrdenados, new Comparator<RetornoFechamentoAcao>() {
			@Override public int compare(RetornoFechamentoAcao e1, RetornoFechamentoAcao e2) {
				return e1.getRetorno().compareTo(e2.getRetorno());
			}
		});
		return retornosOrdenados;
	}

	public int getNumeroFechamentos() {
		return fechamentos.size();
	}
}
