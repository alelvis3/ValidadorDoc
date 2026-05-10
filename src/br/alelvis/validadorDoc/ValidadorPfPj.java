package br.alelvis.validadorDoc;

public class ValidadorPfPj {
	private final String cpfOrCnpj;
	private String tipo;
	private String formatar;
	private String ufCpf;

	public String getTipo() {
		return tipo;
	}

	public String getFormatar() {
		return formatar;
	}

	public String getUfCpf() {
		return ufCpf;
	}

	public ValidadorPfPj(String cpfOrCnpj) {
		this.cpfOrCnpj = cpfOrCnpj.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
	}

	public boolean validar() {
		return switch (cpfOrCnpj.length()) {
		case 11 -> validarCPF(cpfOrCnpj);
		case 14 -> validarCNPJ(cpfOrCnpj);
		default -> false;
		};
	}

	private char calcularDigitoCNPJ(String num, int[] pesos) {
	    int soma = 0;
	    for (int i = 0; i < pesos.length; i++) {
	        // REGRA OFICIAL: Valor ASCII do caractere - 48
	        int valorCalculo = num.charAt(i) - 48; 
	        soma += valorCalculo * pesos[i];
	    }
	    int resto = soma % 11;
	    return (resto < 2) ? '0' : (char) ((11 - resto) + '0');
	}

	private boolean validarCNPJ(String cnpj) {
	    // Mantém a regra de 14 dígitos alfanuméricos
	    if (cnpj.length() != 14 || cnpj.matches("(\\w)\\1{13}")) return false;

	    try {
	        // Pesos oficiais permanecem os mesmos
	        int[] pesosDig1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
	        int[] pesosDig2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

	        char dig13 = calcularDigitoCNPJ(cnpj, pesosDig1);
	        char dig14 = calcularDigitoCNPJ(cnpj, pesosDig2);

	        if (dig13 == cnpj.charAt(12) && dig14 == cnpj.charAt(13)) {
	            this.tipo = "CNPJ";
	            // Máscara atualizada para aceitar letras nas primeiras 12 posições
	            this.formatar = cnpj.replaceAll("([A-Z0-9]{2})([A-Z0-9]{3})([A-Z0-9]{3})([A-Z0-9]{4})(\\d{2})",
	                    "$1.$2.$3/$4-$5");
	            return true;
	        }
	    } catch (Exception e) {
	        return false;
	    }
	    return false;
	}

	private boolean validarCPF(String cpf) {
		if (cpf.matches("(\\d)\\1{10}") || cpf.equals("12345678909"))
			return false;

		try {
			char dig10 = calcularDigito(cpf, 10, 9);
			char dig11 = calcularDigito(cpf, 11, 10);
			if (dig10 == cpf.charAt(9) && dig11 == cpf.charAt(10)) {
				tipo = "CPF";
				formatar = cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
				ufCpf = identificadorUf(cpf.charAt(8));
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	private char calcularDigito(String num, int pesoInicial, int posicao) {
		int soma = 0, peso = pesoInicial;
		for (int i = 0; i < posicao; i++) {
			soma += (num.charAt(i) - '0') * peso--;
		}
		int resto = 11 - (soma % 11);
		return (resto >= 10) ? '0' : (char) (resto + '0');
	}

	private String identificadorUf(char digito) {
		return switch (digito) {
		case '1' -> "DF, GO, MS, MT e TO";
		case '2' -> "PA, AM, AC, AP, RO e RR";
		case '3' -> "CE, MA e PI";
		case '4' -> "PE, RN, PB e AL";
		case '5' -> "BA e SE";
		case '6' -> "MG";
		case '7' -> "RJ e ES";
		case '8' -> "SP";
		case '9' -> "PR e SC";
		case '0' -> "RS";
		default -> "UF Desconhecida";
		};
	}
}