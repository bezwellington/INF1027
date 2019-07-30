package br.com.empresa.acoes.ui;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import br.com.empresa.acoes.io.AcoesInputReader;
import br.com.empresa.acoes.model.Acao;
import br.com.empresa.acoes.model.FechamentoAcao;
import br.com.empresa.acoes.model.RetornoFechamentoAcao;

public class Main {
	
	private static final DateTimeFormatter dateFormat = DateTimeFormat.forPattern("dd-MM-yyyy");

	public static void main(String[] args) throws IOException {

		if (args.length == 0) {
			System.out.printf("Ao executar o programa, por favor informe o caminho do arquivo de entrada.");
			System.out.printf("Modo de usar: ");
			System.out.printf("quantum-finance-acoes [caminho arquivo entrada]");
			System.in.read();
			
			System.exit(0);
		}

		String filePath = args[0];
		List<Acao> acoes = readAcoes(filePath);
		
		for (Acao acao : acoes) {
			FechamentoAcao fechamentoMinimo = acao.getFechamentoMinimo();
			String dataFechamentoMinimo = fechamentoMinimo.getData().toString(dateFormat);
			String valorFechamentoMinimo = fechamentoMinimo.getValorFechamento().toPlainString();
			FechamentoAcao fechamentoMaximo = acao.getFechamentoMaximo();
			String dataFechamentoMaximo = fechamentoMaximo.getData().toString(dateFormat);
			String valorFechamentoMaximo = fechamentoMaximo.getValorFechamento().toPlainString();
			
			RetornoFechamentoAcao menorRetorno = acao.getFechamentoMenorRetorno();
			String dataMenorRetorno = menorRetorno.getFechamento().getData().toString(dateFormat);
			String valorMenorRetorno = menorRetorno.getRetorno().toPlainString();
			RetornoFechamentoAcao maiorRetorno = acao.getFechamentoMaiorRetorno();
			String dataMaiorRetorno = maiorRetorno.getFechamento().getData().toString(dateFormat);
			String valorMaiorRetorno = maiorRetorno.getRetorno().toPlainString();

			System.out.printf("Acao: %s", acao.getId());
			System.out.println();
			System.out.printf("Fechamento M�nimo: %s $%s", dataFechamentoMinimo, valorFechamentoMinimo);
			System.out.println();
			System.out.printf("Fechamento M�ximo: %s $%s", dataFechamentoMaximo, valorFechamentoMaximo);
			System.out.println();
			
			System.out.printf("Menor Retorno: %s $%s", dataMenorRetorno, valorMenorRetorno);
			System.out.println();
			System.out.printf("Maior Retorno: %s $%s", dataMaiorRetorno, valorMaiorRetorno);
			System.out.println();
			
			System.out.printf("Volume M�dio: %.2f", acao.getVolumeMedio());
			System.out.println();
			
			System.out.printf("-----------------------------");
			System.out.println();
		}
	}

	private static List<Acao> readAcoes(String filePath) throws IOException {
		System.out.println(filePath);
		File file = new File(filePath);
		AcoesInputReader inputReader = new AcoesInputReader();
		List<Acao> acoes = inputReader.readAcoesFromFile(file);
		Collections.sort(acoes);
		return acoes;
	}

}
