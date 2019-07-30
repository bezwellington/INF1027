package br.com.empresa.acoes.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import br.com.empresa.acoes.model.Acao;
import br.com.empresa.acoes.model.FechamentoAcao;

public class AcoesInputReader {
	
	private static final String ACAO = "Acao";
	private static final String DATA = "Data";
	private static final String FECHAMENTO = "Fechamento";
	private static final String VOLUME = "Volume";
	
	private final DateTimeFormatter dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd");
	
	public List<Acao> readAcoesFromFile(File acoesFile) throws FileNotFoundException {
		assert (acoesFile != null);
		
		if (!acoesFile.isFile()) {
			throw new FileNotFoundException();
		}
		
		CSVFormat format = CSVFormat.DEFAULT
				.withSkipHeaderRecord()
				.withDelimiter(',')
				.withHeader(ACAO, DATA, FECHAMENTO, VOLUME);
		
		CSVParser hfParser = converteArquivo(acoesFile, format);
		
		return geraListaDeAcoes(hfParser);
	}


	private List<Acao> geraListaDeAcoes(CSVParser hfParser) {
		HashMap<String, Acao> acoes = new HashMap<String, Acao>();
		try {
			for (CSVRecord r : hfParser.getRecords()) {
				try {
					String idAcao = r.get(ACAO);
					LocalDate dataFechamento = LocalDate.parse(r.get(DATA), dateFormat);
					BigDecimal valorFechamento = new BigDecimal(r.get(FECHAMENTO));
					long volume = Long.parseLong(r.get(VOLUME));
				
					Acao acao = null;
					if ((acao = acoes.get(idAcao)) == null) {
						acao = new Acao(idAcao);
						acoes.put(idAcao, acao);
					}
					
					FechamentoAcao fechamento = new FechamentoAcao(acao, dataFechamento, valorFechamento, volume);
					acao.addFechamento(fechamento);
				
				} catch (NumberFormatException e) {
					System.err.println("Erro ao converter valor num�rico: " + e.getMessage());
					continue;
				}
			}
		} catch (IOException e) {
			System.err.println("Erro ao ler arquivo de entrada: " + e.getMessage());
		}
		
		return new ArrayList<Acao>(acoes.values());
	}


	private CSVParser converteArquivo(File acoesFile, CSVFormat format) {
		CSVParser hfParser = null;
		try {
			hfParser = CSVParser.parse(acoesFile, Charset.defaultCharset(), format);
		} catch (IOException e) {
			System.err.println("Erro ao converter arquivo de entrada: " + e.getMessage());
		}
		return hfParser;
	}
	
}
