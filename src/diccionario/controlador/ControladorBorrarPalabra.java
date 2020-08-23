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

import diccionario.modelo.ModeloBorrarPalabra;
import diccionario.vista.MarcoBorrarPalabra;

public final class ControladorBorrarPalabra extends Controlador {
	
	//-------------------- Métodos constructores --------------------//
	private ControladorBorrarPalabra() {}
	
	//-------------------- Métodos para activar eventos --------------------//
	public static void activarEventos(MarcoBorrarPalabra marcoBorrarPalabra) {
		ControladorBorrarPalabra.marcoBorrarPalabra = marcoBorrarPalabra;
		
		JPanel laminaSuperior = marcoBorrarPalabra.getLaminaSuperior();
		JPanel laminaInferior = marcoBorrarPalabra.getLaminaInferior();
		
		ControladorBorrarPalabra controlador = new ControladorBorrarPalabra();
		
		controlador.activarCierreMarco();
		controlador.activarCombos(laminaSuperior);
		controlador.activarBotones(laminaInferior);
	}
	
	//-------------------- Activar cierre marco --------------------//
	private void activarCierreMarco() {
		marcoBorrarPalabra.addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent e) {
				resetearCampos();
			}
		});
	}
	
	//-------------------- Activar combos --------------------//
	private void activarCombos(JPanel laminaSuperior) {
		Component[] componentesLS = laminaSuperior.getComponents();
		
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
						
						String[] data = ModeloBorrarPalabra.devolverDataCombo(seleccion);
						
						for(String i : data) {
							comboPalabras.addItem(i);
						}
					}
					catch (IOException e1) {
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(
								marcoBorrarPalabra,
								"No hay palabras registradas");
						
						resetearCampos();
						marcoBorrarPalabra.setVisible(false);
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
					botonBorrar.setEnabled(!seleccion.equals("-- Seleccionar --"));
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
				botonBorrar = (JButton) i;
				
				switch(botonBorrar.getText()) {
					case "Borrar":
						botonBorrar.addActionListener(devolverOyenteBorrar());
						break;
				}
			}
		}
	}
	
	private ActionListener devolverOyenteBorrar() {
		
		ActionListener oyente = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String letra = comboLetras.getSelectedItem().toString();
				String palabra = comboPalabras.getSelectedItem().toString();
				
				try {
					if(ModeloBorrarPalabra.borrarPalabra(letra, palabra)) {
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(
								marcoBorrarPalabra,
								"Palabra borrada exitosamente");
						
						resetearCampos();
						recargarMarco(marcoBorrarPalabra.getMarcoPrincipal(),
								palabra);
						
						marcoBorrarPalabra.setVisible(false);
					}
					else {
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(
								marcoBorrarPalabra,
								"La palabra no fue borrada, intente nuevamente",
								"Advertencia", JOptionPane.WARNING_MESSAGE);
					}
				}
				catch (IOException e1) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(
							marcoBorrarPalabra,
							"Error al borrar la palabra",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			
		};
		
		return oyente;
	}
	
	private void resetearCampos() {
		botonBorrar.setEnabled(false);
		
		comboPalabras.setEnabled(false);
		comboPalabras.setSelectedIndex(0);
		
		comboLetras.setSelectedIndex(0);
	}

	//-------------------- Campos de clase --------------------//
	private JComboBox<String> comboLetras, comboPalabras;
	private JButton botonBorrar;
	private static MarcoBorrarPalabra marcoBorrarPalabra;
}
