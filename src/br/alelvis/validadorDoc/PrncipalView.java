package br.alelvis.validadorDoc;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class PrncipalView extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JTextField txtDoc;
	public JTextField txtNumeroFormatado;
	public JLabel lblSt;
	public JLabel lblEstado;

	public PrncipalView() {
		setTitle("Validador de CPF ou CNPJ");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 447, 220);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNumeroDoc = new JLabel("Digite o CPF ou CNPJ: ");
		lblNumeroDoc.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNumeroDoc.setBounds(10, 15, 224, 14);
		contentPane.add(lblNumeroDoc);

		txtDoc = new JTextField();
		txtDoc.setBounds(255, 12, 166, 20);
		contentPane.add(txtDoc);
		txtDoc.setColumns(20);

		JButton btnNewButton = new JButton("Validar documento");
		btnNewButton.setBounds(255, 40, 166, 23);
		contentPane.add(btnNewButton);

		JLabel lblValidaDoc = new JLabel("Status");
		lblValidaDoc.setHorizontalAlignment(SwingConstants.RIGHT);
		lblValidaDoc.setBounds(10, 40, 45, 23);
		contentPane.add(lblValidaDoc);

		txtNumeroFormatado = new JTextField();
		txtNumeroFormatado.setHorizontalAlignment(SwingConstants.CENTER);
		txtNumeroFormatado.setEditable(false);
		txtNumeroFormatado.setBounds(123, 109, 166, 20);
		contentPane.add(txtNumeroFormatado);
		txtNumeroFormatado.setColumns(10);

		lblSt = new JLabel("");
		lblSt.setHorizontalAlignment(SwingConstants.CENTER);
		lblSt.setBounds(74, 40, 184, 23);
		contentPane.add(lblSt);

		JLabel lblTipo = new JLabel("");
		lblTipo.setHorizontalAlignment(SwingConstants.LEFT);
		lblTipo.setBounds(10, 106, 108, 23);
		contentPane.add(lblTipo);

		JLabel lblNumeroFormatado = new JLabel("Numero Formatado");
		lblNumeroFormatado.setHorizontalAlignment(SwingConstants.CENTER);
		lblNumeroFormatado.setBounds(123, 74, 166, 23);
		contentPane.add(lblNumeroFormatado);

		lblEstado = new JLabel("");
		lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEstado.setHorizontalAlignment(SwingConstants.CENTER);
		lblEstado.setBounds(10, 140, 411, 30);
		contentPane.add(lblEstado);

		// Controller initialization
		new PrncipalController(this);

		btnNewButton.addActionListener(e -> {
			if (txtDoc.getText().isEmpty()) {
				lblSt.setText("Por favor, insira um CPF ou CNPJ");
			} else {
				new PrncipalController(this).validarDocumento();
			}
		});
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				PrncipalView frame = new PrncipalView();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}