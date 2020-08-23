package diccionario.vista;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import diccionario.controlador.ControladorBorrarPalabra;

public class MarcoBorrarPalabra extends MarcoCambios {
	
	//-------------------- Métodos constructores --------------------//
	public MarcoBorrarPalabra(MarcoPrincipal marcoPrincipal) {
		super(marcoPrincipal, "Borrar palabra");
		
		construirLaminaInferior();
		
		ControladorBorrarPalabra.activarEventos(this);
	}
	
	//-------------------- Construir lámina inferior --------------------//
	protected void construirLaminaInferior() {
		laminaInferior = new JPanel();
		JButton botonBorrar = new JButton("Borrar");
		
		botonBorrar.setEnabled(false);
		
		laminaInferior.add(botonBorrar);
		
		add(laminaInferior, BorderLayout.SOUTH);
	}
	
	//-------------------- Métodos getter --------------------//
	public JPanel getLaminaInferior() {
		return laminaInferior;
	}
	
	//-------------------- Campos de clase --------------------//
	private JPanel laminaInferior;
	private static final long serialVersionUID = 1L;
}
