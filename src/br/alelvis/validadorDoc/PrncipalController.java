package br.alelvis.validadorDoc;

public class PrncipalController {
	private final PrncipalView view;
	private final ValidadorPfPj validador;

	public PrncipalController(PrncipalView view) {
		this.view = view;
		this.validador = new ValidadorPfPj(view.txtDoc.getText());
	}

	public void validarDocumento() {
		if (validador.validar()) {
			view.lblSt.setText(validador.getTipo() + ": Válido");
			view.txtNumeroFormatado.setText(validador.getFormatar());
			view.lblEstado.setText(validador.getUfCpf());
		} else {
			view.lblSt.setText("Inválido!!");
		}
	}
}
