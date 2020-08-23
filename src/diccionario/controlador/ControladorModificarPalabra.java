package diccionario.controlador;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import diccionario.modelo.ModeloModificarPalabra;
import diccionario.vista.MarcoModificarPalabra;

public class ControladorModificarPalabra extends Controlador {
	
	//-------------------- Métodos constructores --------------------//
	private ControladorModificarPalabra() {}
	
	//-------------------- Métodos para activar eventos --------------------//
	public static void activarEventos(MarcoModificarPalabra marcoModificarPalabra) {
		ControladorModificarPalabra.marcoModificarPalabra = marcoModificarPalabra;
		
		JPanel laminaSuperior = marcoModificarPalabra.getLaminaSuperior();
		JPanel laminaInferior = marcoModificarPalabra.getLaminaInferior();
		
		ControladorModificarPalabra controlador = new ControladorModificarPalabra();
		
		controlador.activarCierreMarco();
		controlador.activarCombos(laminaSuperior);
		controlador.activarBotones(laminaInferior);
	}
	
	//-------------------- Activar cierre marco --------------------//
	private void activarCierreMarco() {
		marcoModificarPalabra.addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent e) {
				resetearCampos();
			}
		});
	}
	
	//-------------------- Activar combos --------------------//
	private void activarCombos(JPanel laminaSuperior) {
		Component[] componentesLS = laminaSuperior.getComponents();
		iniciarCambosTexto();
		
		for(Component i : componentesLS) {
			if(i instanceof JPanel) {
				 activarSubLaminas(((JPanel) i).getComponents());
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void activarSubLaminas(Component[] componentesI) {
		int longComponentesI = componentesI.length;
		JLabel etiqueta = null;
		
		for(int j = 0; j < longComponentesI; j++) {
			if(componentesI[j] instanceof JLabel) {
				etiqueta = (JLabel) componentesI[j]; 
			}
			
			if(componentesI[j] instanceof JComboBox<?>) {
				if(etiqueta != null) {
					switch(etiqueta.getText()) {
						case "Letra:":
							comboLetras = (JComboBox<String>) componentesI[j];
							comboLetras.addActionListener(
									devolverOyenteComboLetras());
							break;
						case "Palabra:":
							comboPalabras = (JComboBox<String>) componentesI[j];
							comboPalabras.addActionListener(
									devolverOyenteComboPalabras());
							break;
					}
				}
			}
		}
	}
	
	private void iniciarCambosTexto() {
		JPanel laminaCentral = marcoModificarPalabra.getLaminaCentral();
		
		Component[] componentesLC = laminaCentral.getComponents();
		
		for(Component i : componentesLC) {
			if(i instanceof JPanel) {
				JPanel laminaFormulario = (JPanel) i;
				activarFormulario(laminaFormulario.getComponents());
			}
		}
	}
	
	private void activarFormulario(Component[] componentesI) {
		int longComponentesI = componentesI.length;
		JLabel etiqueta = null;
		
		for(int j = 0; j < longComponentesI; j++) {
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
	
	private ActionListener devolverOyenteComboLetras() {
		ActionListener oyente = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String seleccion = comboLetras.getSelectedItem().toString();
				int longSeleccion = seleccion.length();
				
				boolean activarComponentes = (longSeleccion > 1);
				
				if(activarComponentes) {
					resetearCampos();
				}
				else {
					try {
						comboPalabras.setEnabled(true);
						comboPalabras.removeAllItems();
						
						String[] data = ModeloModificarPalabra.devolverDataCombo(
								seleccion);
						
						for(String i : data) {
							comboPalabras.addItem(i);
						}
					}
					catch (IOException e1) {
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(
								marcoModificarPalabra,
								"No hay palabras registradas");
						
						resetearCampos();
						marcoModificarPalabra.setVisible(false);
					}
				}
			}
		};
		
		return oyente;
	}
	
	private ActionListener devolverOyenteComboPalabras() {
		ActionListener oyente = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(comboPalabras.getItemCount() > 0) {
					String seleccion = comboPalabras.getSelectedItem().toString();
					boolean activarComponentes = seleccion.equals("-- Seleccionar --");
					
					if(activarComponentes) {
						botonModificar.setEnabled(false);
						botonLimpiarCampos.setEnabled(false);
						
						campoPalabra.setEditable(false);
						campoDefinicion.setEditable(false);
						
						campoPalabra.setText("");
						campoDefinicion.setText("");
					}
					else {
						campoPalabra.setEditable(true);
						campoDefinicion.setEditable(true);
						
						campoPalabra.setText(seleccion);
						try {
							String definicion = ModeloModificarPalabra.devolverDefinicionPalabra(seleccion);
							
							if(definicion != null) {
								campoDefinicion.setText(definicion);
							}
							else {
								campoDefinicion.setText("");
							}
						}
						catch (IOException e1) {
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(
									marcoModificarPalabra,
									"No se pudo cargar la definición de la palabra",
									"Advertencia", JOptionPane.WARNING_MESSAGE);
						}
						
						botonModificar.setEnabled(true);
						botonLimpiarCampos.setEnabled(true);
					}
				}
			}
		};
		
		return oyente;
	}
	
	//-------------------- Activar botones --------------------//
	private void activarBotones(JPanel laminaInferior) {
		Component[] componentesLI = laminaInferior.getComponents();
		
		for(Component i : componentesLI) {
			if(i instanceof JButton) {
				JButton boton = (JButton) i;
				
				switch(boton.getText()) {
					case "Modificar":
						botonModificar = boton;
						botonModificar.addActionListener(devolverOyenteModificar());
						break;
					case "Limpiar campos":
						botonLimpiarCampos = boton;
						botonLimpiarCampos.addActionListener(new ActionListener() {
							
							public void actionPerformed(ActionEvent e) {
								resetearCampos();
							}
						});
						break;
				}
			}
		}
	}
	
	private ActionListener devolverOyenteModificar() {
		
		ActionListener oyente = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String inicial = comboLetras.getSelectedItem().toString();
				String palabra = comboPalabras.getSelectedItem().toString();
				String palabraModificada = campoPalabra.getText();
				String definicionModificada = campoDefinicion.getText();
				
				if(palabraModificada.equals("") || definicionModificada.equals("")) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(
							marcoModificarPalabra,
							"Error ambos campos son requeridos",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					try {
						if(ModeloModificarPalabra.modificarPalabra(inicial,
								palabra,
								palabraModificada, definicionModificada)) {
							
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(
									marcoModificarPalabra,
									"Palabra modificada exitosamente");
							
							resetearCampos();
							recargarMarco(marcoModificarPalabra.getMarcoPrincipal(),
									palabra);
							
							marcoModificarPalabra.setVisible(false);
						}
					}
					catch (IOException e1) {
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(
								marcoModificarPalabra,
								"Error al modificar la palabra, intente nuevamente",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				
			}
			
		};
		
		return oyente;
	}
	
	private void resetearCampos() {
		botonModificar.setEnabled(false);
		botonLimpiarCampos.setEnabled(false);
		
		campoPalabra.setEditable(false);
		campoDefinicion.setEditable(false);
		
		campoPalabra.setText("");
		campoDefinicion.setText("");
		
		comboPalabras.setEnabled(false);
		comboPalabras.setSelectedIndex(0);
		
		comboLetras.setSelectedIndex(0);
	}

	//-------------------- Campos de clase --------------------//
	private JComboBox<String> comboLetras, comboPalabras;
	private JTextField campoPalabra, campoDefinicion;
	private JButton botonModificar, botonLimpiarCampos;
	private static MarcoModificarPalabra marcoModificarPalabra;
}
