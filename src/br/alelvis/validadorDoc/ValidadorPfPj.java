package br.alelvis.validadorDoc;

public class ValidadorPfPj {
	private final String cpfOrCnpj;
	private String tipo;
	private String formatar;
	private String ufCpf;

	public ValidadorPfPj(String cpfOrCnpj) {
		this.cpfOrCnpj = cpfOrCnpj.replaceAll("\\D", "");
	}

	public String getTipo() {
		return tipo;
	}

	public String getFormatar() {
		return formatar;
	}
	
	public String getUfCpf() {
		return ufCpf;
	}

	public boolean validar() {
		if (cpfOrCnpj.length() == 11) {
			return validarCPF(cpfOrCnpj);
		} else if (cpfOrCnpj.length() == 14) {
			return validarCNPJ(cpfOrCnpj);
		} else {
			return false;
		}
	}

	private boolean validarCPF(String cpf) {
		if (cpf.matches("(\\d)\\1{10}") || cpf.equals("12345678909")) {
			return false;
		}
		try {
			char dig10 = calcularDigito(cpf, 10, 9);
			char dig11 = calcularDigito(cpf, 11, 10);
			if (dig10 == cpf.charAt(9) && dig11 == cpf.charAt(10)) {
				tipo = "CPF";
				formatar = cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
				char uf = cpf.charAt(8);
				ufCpf = identificadorUf(uf);
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	private boolean validarCNPJ(String cnpj) {
		if (cnpj.matches("(\\d)\\1{13}") || cnpj.equals("11222333000181")) {
			return false;
		}
		try {
			char dig13 = calcularDigitoCNPJ(cnpj, 12);
			char dig14 = calcularDigitoCNPJ(cnpj, 13);
			if (dig13 == cnpj.charAt(12) && dig14 == cnpj.charAt(13)) {
				tipo = "CNPJ";
				formatar = cnpj.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	private char calcularDigito(String num, int pesoInicial, int posicao) {
		int soma = 0;
		int peso = pesoInicial;
		for (int i = 0; i < posicao; i++) {
			int n = num.charAt(i) - '0';
			soma += n * peso--;
		}
		int resto = 11 - (soma % 11);
		return (resto == 10 || resto == 11) ? '0' : (char) (resto + '0');
	}

	private char calcularDigitoCNPJ(String num, int posicao) {
		int soma = 0;
		int peso = 2;
		for (int i = posicao - 1; i >= 0; i--) {
			int n = num.charAt(i) - '0';
			soma += n * peso;
			peso = (peso == 9) ? 2 : peso + 1;
		}
		int resto = soma % 11;
		return (resto < 2) ? '0' : (char) ((11 - resto) + '0');
	}

	private String identificadorUf(char cpf) {
		switch (cpf) {
		case '1':
			return "Distrito Federal, Goiás, Mato Grosso do Sul e Tocantins";
		case '2':
			return "Pará, Amazonas, Acre, Amapá, Rondônia e Roraima";
		case '3':
			return "Ceará, Maranhão e Piauí";
		case '4':
			return "Pernambuco, Rio Grande do Norte, Paraíba e Alagoas";
		case '5':
			return "Bahia e Sergipe";
		case '6':
			return "Minas Gerais";
		case '7':
			return "Rio de Janeiro e Espírito Santo";
		case '8':
			return "São Paulo";
		case '9':
			return "Paraná e Santa Catarina";
		case '0':
			return "Rio Grande do Sul";
		default:
			return null;
		}
	}


}
