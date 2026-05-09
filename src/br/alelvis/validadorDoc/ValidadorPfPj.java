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

	private char calcularDigitoCNPJ(String num, int posicao) {
		int soma = 0, peso = 2;
		for (int i = posicao - 1; i >= 0; i--) {
			int n = Character.getNumericValue(num.charAt(i));
			soma += n * peso;
			peso = (peso == 9) ? 2 : peso + 1;
		}
		int resto = soma % 11;
		return (resto < 2) ? '0' : (char) ((11 - resto) + '0');
	}

	private boolean validarCNPJ(String cnpj) {
		if (cnpj.matches("(\\w)\\1{13}"))
			return false;
		
		try {
			char dig13 = calcularDigitoCNPJ(cnpj, 12);
			char dig14 = calcularDigitoCNPJ(cnpj, 13);
			if (dig13 == cnpj.charAt(12) && dig14 == cnpj.charAt(13)) {
				tipo = "CNPJ";
				formatar = cnpj.replaceAll("([A-Z0-9]{2})([A-Z0-9]{3})([A-Z0-9]{3})([A-Z0-9]{4})(\\d{2})",
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