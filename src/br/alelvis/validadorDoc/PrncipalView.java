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
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public JTextField txtDoc, txtNumeroFormatado;
    public JLabel lblSt, lblEstado;

    public PrncipalView() {
        setTitle("Validador de CPF ou CNPJ - Java 25");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 250);
        
        JPanel contentPane = new JPanel(null);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JLabel lblNumeroDoc = new JLabel("Digite CPF ou CNPJ:");
        lblNumeroDoc.setBounds(10, 15, 150, 14);
        contentPane.add(lblNumeroDoc);

        txtDoc = new JTextField();
        txtDoc.setBounds(160, 12, 260, 20);
        contentPane.add(txtDoc);

        JButton btnValidar = new JButton("Validar");
        btnValidar.setBounds(160, 40, 260, 30);
        contentPane.add(btnValidar);

        lblSt = new JLabel("Status", SwingConstants.CENTER);
        lblSt.setBounds(10, 80, 410, 23);
        contentPane.add(lblSt);

        txtNumeroFormatado = new JTextField();
        txtNumeroFormatado.setEditable(false);
        txtNumeroFormatado.setHorizontalAlignment(SwingConstants.CENTER);
        txtNumeroFormatado.setBounds(120, 110, 200, 25);
        contentPane.add(txtNumeroFormatado);

        lblEstado = new JLabel("", SwingConstants.CENTER);
        lblEstado.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblEstado.setBounds(10, 150, 410, 30);
        contentPane.add(lblEstado);

        PrncipalController controller = new PrncipalController(this);

        btnValidar.addActionListener(e -> {
            if (txtDoc.getText().isBlank()) {
                lblSt.setText("Campo vazio!");
            } else {
                controller.validarDocumento();
            }
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new PrncipalView().setVisible(true));
    }
}