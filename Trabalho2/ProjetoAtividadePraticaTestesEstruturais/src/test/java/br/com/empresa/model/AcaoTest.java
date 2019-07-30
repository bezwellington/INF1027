package br.com.empresa.model;

import java.math.BigDecimal;

import junit.framework.TestCase;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.JUnit4;

import br.com.empresa.acoes.model.Acao;
import br.com.empresa.acoes.model.FechamentoAcao;
import br.com.empresa.acoes.model.RetornoFechamentoAcao;


@org.junit.runner.RunWith(JUnit4.class)
public class AcaoTest extends TestCase {
	
	private Acao acaoOGXP3;
	
	@Before
	public void runBefore() {
		acaoOGXP3 = new Acao("OGXP3");
		
		LocalDate data1 = LocalDate.parse("2010-01-20");
		BigDecimal valorFechamento1 = new BigDecimal("30.30");
		long volume1 = 34252600;
		acaoOGXP3.addFechamento(new FechamentoAcao(acaoOGXP3, data1, valorFechamento1, volume1));
		
		LocalDate data2 = LocalDate.parse("2010-01-21");
		BigDecimal valorFechamento2 = new BigDecimal("20.10");
		long volume2 = 562343;
		acaoOGXP3.addFechamento(new FechamentoAcao(acaoOGXP3, data2, valorFechamento2, volume2));
		
		LocalDate data3 = LocalDate.parse("2010-01-22");
		BigDecimal valorFechamento3 = new BigDecimal("25.50");
		long volume3 = 34257500;
		acaoOGXP3.addFechamento(new FechamentoAcao(acaoOGXP3, data3, valorFechamento3, volume3));
		
		LocalDate data4 = LocalDate.parse("2010-01-23");
		BigDecimal valorFechamento4 = new BigDecimal("25.50");
		long volume4 = 0;
		acaoOGXP3.addFechamento(new FechamentoAcao(acaoOGXP3, data4, valorFechamento4, volume4));		
	}
	
	@Test
	public void testAcaoGetId() {
		assertEquals("Id da acao deve ser OGXP3", "OGXP3", acaoOGXP3.getId());
	}
	
	@Test
	public void testAcaoFechamentoMaximo() {
		FechamentoAcao fechamentoMaximo = acaoOGXP3.getFechamentoMaximo();
		assertEquals("Valor Fechamento M�ximo deve ser 30.30", new BigDecimal("30.30"), fechamentoMaximo.getValorFechamento());
		assertEquals("Data Fechamento M�nimo deve ser 2010-01-20", LocalDate.parse("2010-01-20"), fechamentoMaximo.getData());
	}
	
	@Test
	public void testAcaoFechamentoMinimo() {
		FechamentoAcao fechamentoMinimo = acaoOGXP3.getFechamentoMinimo();
		assertEquals("Valor Fechamento M�nimo deve ser 20.10", new BigDecimal("20.10"), fechamentoMinimo.getValorFechamento());
		assertEquals("Data Fechamento M�nimo deve ser 2010-01-21", LocalDate.parse("2010-01-21"), fechamentoMinimo.getData());		
	}
	
	@Test
	public void testAcaoFechamentoMaiorRetorno() {
		RetornoFechamentoAcao maiorRetorno = acaoOGXP3.getFechamentoMaiorRetorno();
		assertEquals("Valor maior retorno deve ser 30.30", new BigDecimal("30.30"), maiorRetorno.getRetorno());
		assertEquals("Data maior retorno deve ser 2010-01-20", LocalDate.parse("2010-01-20"), maiorRetorno.getFechamento().getData());
	}	
	
	@Test
	public void testAcaoFechamentoMenorRetorno() {
		RetornoFechamentoAcao menorRetorno = acaoOGXP3.getFechamentoMenorRetorno();
		assertEquals("Valor menor retorno deve ser -0.34", new BigDecimal("-0.34"), menorRetorno.getRetorno());
		assertEquals("Data menor retorno deve ser 2010-01-21", LocalDate.parse("2010-01-21"), menorRetorno.getFechamento().getData());
	}
	
	@Test
	public void testAcaoVolumeMedio() {
		// Complementar com testes aqui

	}	
}
