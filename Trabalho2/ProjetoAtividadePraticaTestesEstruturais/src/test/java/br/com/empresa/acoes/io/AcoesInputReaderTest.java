package br.com.empresa.acoes.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runners.JUnit4;

import br.com.empresa.acoes.io.AcoesInputReader;
import br.com.empresa.acoes.model.Acao;
import junit.framework.TestCase;

@org.junit.runner.RunWith(JUnit4.class)
public class AcoesInputReaderTest extends TestCase {

	private static final String FILE_PATH = "acoesteste.csv";
	private List<Acao> acoes;
	
	@Before
	public void runBefore() {
		try {
			URL resource = AcoesInputReaderTest.class.getResource(FILE_PATH);
			AcoesInputReader reader = new AcoesInputReader();
			File acoesFile = new File(resource.getFile());
			
			acoes = reader.readAcoesFromFile(acoesFile);
		} catch (FileNotFoundException e) {
			fail("N�o deveria gerar exce��o");
		}
	}
	
	@Test
	public void testLeArquivo4Acoes() {
		assertEquals("Deveria ter lido fechamentos de 3 a��es", 3, acoes.size());
	}
	
	@Test
	public void testCadaAcaoContem3Fechamentos() throws FileNotFoundException {
		for (Acao acao : acoes) {
			assertEquals("Cada acao no arquivo cont�m tres fechamentos", 3, acao.getNumeroFechamentos());
		}
	}
	
	@Test(expected=FileNotFoundException.class)
	public void testNomeArquivoIncorreto() throws FileNotFoundException {
		AcoesInputReader reader = new AcoesInputReader();
		File invalidFile = new File("InvalidFileName.csv");
		acoes = reader.readAcoesFromFile(invalidFile);
	}
}
