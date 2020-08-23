package diccionario.vista;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import diccionario.controlador.ControladorModificarPalabra;

public class MarcoModificarPalabra extends MarcoCambios {
	
	//-------------------- Métodos constructores --------------------//
	public MarcoModificarPalabra(MarcoPrincipal marcoPrincipal) {
		super(marcoPrincipal, "Modificar palabra");
		
		construirLaminaCentrar();
		construirLaminaInferior();
		
		ControladorModificarPalabra.activarEventos(this);
	}
	
	//-------------------- Construir lámina inferior --------------------//
	private void construirLaminaCentrar() {
		laminaCentral = new JPanel(new BorderLayout());
		
		JTextField campoPalabra = devolverCampoTexto(16);
		JTextField campoDefinicion = devolverCampoTexto(16);
		
		JPanel laminaCampos = devolverFila(
				new JComponent[] {
					new JLabel("Palabra:"),
					campoPalabra,
					new JLabel("Definición:"),
					campoDefinicion
				},
				new GridLayout(2, 1));
		
		laminaCentral.add(laminaCampos, BorderLayout.NORTH);
		
		add(laminaCentral, BorderLayout.CENTER);
	}
	
	private JTextField devolverCampoTexto(int columnas) {
		JTextField campo = new JTextField(columnas);
		
		campo.setEditable(false);
		
		return campo;
	}
	
	//-------------------- Construir lámina inferior --------------------//
	protected void construirLaminaInferior() {
		laminaInferior = new JPanel();
		
		agregarBotones(laminaInferior, new String[] {
				"Modificar",
				"Limpiar campos"
			}, false);
		
		add(laminaInferior, BorderLayout.SOUTH);
	}
	
	//-------------------- Métodos getter --------------------//
	public JPanel getLaminaCentral() {
		return laminaCentral;
	}
	
	public JPanel getLaminaInferior() {
		return laminaInferior;
	}
	
	//-------------------- Campos de clase --------------------//
	private JPanel laminaCentral, laminaInferior;
	private static final long serialVersionUID = 1L;
}
