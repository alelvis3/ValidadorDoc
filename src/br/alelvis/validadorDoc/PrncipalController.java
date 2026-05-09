package br.alelvis.validadorDoc;

public class PrncipalController {
	private final PrncipalView view;

	public PrncipalController(PrncipalView view) {
		this.view = view;
	}

	public void validarDocumento() {
		String input = view.txtDoc.getText();
		ValidadorPfPj validador = new ValidadorPfPj(input);

		if (validador.validar()) {
			view.lblSt.setText(validador.getTipo() + ": Válido");
			view.txtNumeroFormatado.setText(validador.getFormatar());
			view.lblEstado.setText(validador.getUfCpf());
		} else {
			view.lblSt.setText("Inválido!!");
			view.txtNumeroFormatado.setText("");
			view.lblEstado.setText("");
		}
	}
}