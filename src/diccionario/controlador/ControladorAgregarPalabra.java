package diccionario.controlador;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import diccionario.modelo.ModeloAgregarPalabra;
import diccionario.vista.MarcoAgregarPalabra;

public final class ControladorAgregarPalabra extends Controlador {
	
	//-------------------- Métodos constructores --------------------//
	private ControladorAgregarPalabra() {}
	
	//-------------------- Métodos para activar eventos --------------------//
	public static void activarEventos(MarcoAgregarPalabra marcoAgregarPalabra) {
		ControladorAgregarPalabra.marcoAgregarPalabra = marcoAgregarPalabra;
		
		JPanel laminaFormulario = marcoAgregarPalabra.getLaminaFormulario();
		JPanel laminaEnvio = marcoAgregarPalabra.getLaminaEnvio();
		
		ControladorAgregarPalabra controlador = new ControladorAgregarPalabra();
		
		controlador.activarCamposDeTexto(laminaFormulario);
		controlador.activarBotones(laminaEnvio);
		controlador.activarCierreMarco();
	}
	
	//-------------------- Activar cierre marco --------------------//
	private void activarCierreMarco() {
		marcoAgregarPalabra.addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent e) {
				limpiarCampos();
			}
		});
	}

	//-------------------- Activar campos de texto --------------------//
	private void activarCamposDeTexto(JPanel laminaFormulario) {
		Component[] componentesLF = laminaFormulario.getComponents();
		
		for(Component i : componentesLF) {
			if(i instanceof JPanel) {
				Component[] componentesI = ((JPanel) i).getComponents();
				int longComponentes = componentesI.length;
				JLabel etiqueta = null;
				
				for(int j = 0; j < longComponentes; j++) {
					if(componentesI[j] instanceof JLabel) {
						etiqueta = (JLabel) componentesI[j]; 
					}
					
					if(componentesI[j] instanceof JTextField) {
						if(etiqueta != null) {
							switch(etiqueta.getText()) {
								case "Palabra:":
									campoPalabra = (JTextField) componentesI[j];
									break;
								case "Definición:":
									campoDefinicion = (JTextField) componentesI[j];
									break;
							}
						}
					}
				}
			}
		}
	}
	
	//-------------------- Activar botones --------------------//
	private void activarBotones(JPanel laminaEnvio) {
		Component[] componentesLE = laminaEnvio.getComponents();
		
		for(Component i : componentesLE) {
			if(i instanceof JButton) {
				JButton boton = (JButton) i;
				
				switch(boton.getText()) {
					case "Agregar":
						boton.addActionListener(devolverOyenteEnviar());
						break;
					case "Limpiar campos":
						boton.addActionListener(devolverOyenteLimpiar());
						break;
				}
			}
		}
	}
	
	//-------------------- Oyentes de los botones --------------------//
	private ActionListener devolverOyenteEnviar() {
		ActionListener oyente = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String palabra = campoPalabra.getText();
				String definicion = campoDefinicion.getText();
				
				if(palabra.equals("") || definicion.equals("")) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(
							marcoAgregarPalabra,
							"Ambos campos son requeridos",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					try {
						if(ModeloAgregarPalabra.escribirArchivo(palabra, definicion)) {
							limpiarCampos();
							
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(
									marcoAgregarPalabra,
									"Palabra agregada correctamente");
							
							marcoAgregarPalabra.setVisible(false);
							
							recargarMarco(marcoAgregarPalabra.getMarcoPrincipal(),
									palabra);
						}
						else {
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(
									marcoAgregarPalabra,
									"La palabra: " + palabra 
									+ " ya está registrada, no fue agregada",
									"Advertencia",
									JOptionPane.WARNING_MESSAGE);
							
							limpiarCampos();
						}
					}
					catch (IOException e1) {
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(
								marcoAgregarPalabra,
								"Error al agregar la palabra",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		};
		
		return oyente;
	}
	
	private ActionListener devolverOyenteLimpiar() {
		ActionListener oyente = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				limpiarCampos();
			}
		};
		
		return oyente;
	}
	
	private void limpiarCampos() {
		campoPalabra.setText("");
		campoDefinicion.setText("");
	}

	//-------------------- Campos de clase --------------------//
	private JTextField campoPalabra, campoDefinicion;
	private static MarcoAgregarPalabra marcoAgregarPalabra;
}
