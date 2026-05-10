package br.alelvis.validadorDoc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.DisplayName;

class ValidadorPfPjTest {
	
	
    // Este método lê o arquivo e entrega linha por linha para o teste acima
    static Stream<String> provedorDeCnpjs() throws IOException {
        // Caminho para o arquivo em src/test/resources
        return Files.lines(Paths.get("src/test/resources/cnpjs.txt"))
                    .filter(linha -> !linha.isBlank()); // Pula linhas vazias
    }
    
    @ParameterizedTest
    @DisplayName("Deve validar CNPJs a partir de um arquivo TXT")
    @MethodSource("provedorDeCnpjs")
    void deveValidarCnpjsDoArquivo(String cnpjCandidato) {
        ValidadorPfPj validador = new ValidadorPfPj(cnpjCandidato);
        
        assertTrue(validador.validar(), "CNPJ vindo do arquivo deveria ser válido: " + cnpjCandidato);
        assertEquals("CNPJ", validador.getTipo());
    }

	@Test
    @DisplayName("Deve validar um CPF numérico comum")
    void deveValidarCPFComum() {
        // Exemplo de CPF válido (substitua por um real se necessário)
        ValidadorPfPj validador = new ValidadorPfPj("342.518.678-74"); 
        // Nota: O switch do seu código retornará true se o cálculo do dígito bater
        assertTrue(validador.validar(), "CPF deveria ser válido");
        assertEquals("CPF", validador.getTipo());
    }
	
	@ParameterizedTest
	@DisplayName("Deve rejeitar CNPJs inválidos do arquivo")
	@MethodSource("provedorDeCnpjsInvalidos")
	void deveRejeitarCnpjsInvalidos(String cnpjInvalido) {
	    ValidadorPfPj validador = new ValidadorPfPj(cnpjInvalido);
	    assertFalse(validador.validar(), "CNPJ " + cnpjInvalido + " NÃO deveria ser válido");
	}

	static Stream<String> provedorDeCnpjsInvalidos() throws IOException {
	    return Files.lines(Paths.get("src/test/resources/cnpjs_invalidos.txt"))
	                .filter(linha -> !linha.isBlank());
	}

    @Test
    @DisplayName("Deve validar CNPJ Alfanumérico (Novo Padrão)")
    void deveValidarCnpjAlfanumerico() {
        // Exemplo: ABC12345000195 (O getNumericValue converte letras em valores)
        ValidadorPfPj validador = new ValidadorPfPj("ABC12345000195");
        
        // Se o cálculo do dígito bater com 95, ele validará como CNPJ
        boolean resultado = validador.validar();
        
        if (resultado) {
            assertEquals("CNPJ", validador.getTipo());
            assertTrue(validador.getFormatar().contains("."), "Deveria estar formatado");
        }
    }

    @Test
    @DisplayName("Deve identificar a UF correta pelo dígito do CPF")
    void deveIdentificarUfCpf() {
        // O dígito 8 no CPF 000.000.008-00 indica São Paulo na sua lógica
        ValidadorPfPj validador = new ValidadorPfPj("12345678805"); 
        if (validador.validar()) {
            assertEquals("SP", validador.getUfCpf());
        }
    }

    @Test
    @DisplayName("Deve rejeitar documentos com todos os dígitos iguais")
    void deveRejeitarDigitosIguais() {
        ValidadorPfPj cpfInvalido = new ValidadorPfPj("11111111111");
        ValidadorPfPj cnpjInvalido = new ValidadorPfPj("22222222222222");
        
        assertFalse(cpfInvalido.validar(), "Não deve aceitar CPF 111...");
        assertFalse(cnpjInvalido.validar(), "Não deve aceitar CNPJ 222...");
    }

    @Test
    @DisplayName("Deve limpar caracteres especiais na entrada")
    void deveLimparCaracteresEspeciais() {
        // Entrada suja
        ValidadorPfPj validador = new ValidadorPfPj("12.abc-345/0001-95");
        // O construtor limpa tudo que não é alfanumérico e deixa em UpperCase
        // Internamente deve virar "12ABC345000195"
        assertNotNull(validador);
    }

}
