package diccionario.controlador;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import diccionario.modelo.ModeloPrincipal;
import diccionario.modelo.Palabra;
import diccionario.vista.MarcoAgregarPalabra;
import diccionario.vista.MarcoBorrarPalabra;
import diccionario.vista.MarcoModificarPalabra;
import diccionario.vista.MarcoPrincipal;

public final class ControladorPrincipal extends Controlador {
	
	//-------------------- Métodos constructores --------------------//
	private ControladorPrincipal() {}
	
	//-------------------- Métodos para activar eventos --------------------//
	public static void activarEventos(MarcoPrincipal marco) {
		ControladorPrincipal.marco = marco;
		
		JPanel laminaSuperior = marco.getLaminaSuperior();
		JPanel laminaInferior = marco.getLaminaInferior();
		JMenuBar barraMenu = marco.getBarraMenu();
		
		ControladorPrincipal controlador = new ControladorPrincipal();
		
		controlador.activarBarraMenu(barraMenu);
		controlador.activarComboBox(laminaSuperior);
		controlador.activarBotoneraInferior(laminaInferior);
		
		marcoAgregarPalabra = new MarcoAgregarPalabra(ControladorPrincipal.marco);
		marcoModificarPalabra = new MarcoModificarPalabra(ControladorPrincipal.marco);
		marcoBorrarPalabra = new MarcoBorrarPalabra(ControladorPrincipal.marco);
		
		marcoAgregarPalabra.setDefaultCloseOperation(MarcoAgregarPalabra.DISPOSE_ON_CLOSE);
		marcoModificarPalabra.setDefaultCloseOperation(MarcoModificarPalabra.DISPOSE_ON_CLOSE);
		marcoBorrarPalabra.setDefaultCloseOperation(MarcoBorrarPalabra.DISPOSE_ON_CLOSE);
		
		controlador.tablaPalabras = ControladorPrincipal.marco.getTablaPalabras();
		controlador.tablaPalabras.setEnabled(false);
	}
	
	//-------------------- Activar barra de menú --------------------//
	private void activarBarraMenu(JMenuBar barraMenu) {
		int cantMenues = barraMenu.getMenuCount();
		
		for(int i = 0; i < cantMenues; i++) {
			activarMenu(barraMenu.getMenu(i));
		}
	}
	
	private void activarMenu(JMenu menu) {
		int cantItems = menu.getItemCount();
		
		for(int i = 0; i < cantItems; i++) {
			activarSubMenu(menu.getItem(i));
		}
	}
	
	private void activarSubMenu(JMenuItem item) {
		switch(item.getText()) {
			//-------------------- Archivo --------------------//
			case "Agregar palabra":
				item.addActionListener(devolverOyenteAgregarPalabra());
				break;
			case "Modificar palabra":
				item.addActionListener(devolverOyenteModificarPalabra());
				break;
			case "Borrar palabra":
				item.addActionListener(devolverOyenteBorrarPalabra());
				break;
			case "Salir": 
				item.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
					}
				});
				break;
			//-------------------- Ayuda --------------------//
			case "Licencia":
				item.addActionListener(devolverOyenteAyuda(
						"- Derechos reservados MHenriquez C.A"
						+ "\n- Programador: Ing. Manuel Henriquez"
						+ "\n- Correo: manuelhm1993@gmail.com"
						+ "\n- Teléfono: +58-424-6827377"));
				break;
			case "Acerca de...":
				item.addActionListener(devolverOyenteAyuda(
						"- Nombre: DiccMH"
						+ "\n- Versión: 2.2"
						+ "\n- Año: 2020"));
				break;
		}
	}
	
	private ActionListener devolverOyenteAgregarPalabra() {
		ActionListener oyente = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				marcoAgregarPalabra.setVisible(!marcoAgregarPalabra.isVisible());
			}
		};
		
		return oyente;
	}
	
	private ActionListener devolverOyenteModificarPalabra() {
		ActionListener oyente = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				marcoModificarPalabra.setVisible(!marcoModificarPalabra.isVisible());
			}
		};
		
		return oyente;
	}
	
	private ActionListener devolverOyenteBorrarPalabra() {
		ActionListener oyente = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				marcoBorrarPalabra.setVisible(!marcoBorrarPalabra.isVisible());
			}
		};
		
		return oyente;
	}

	private ActionListener devolverOyenteAyuda(String mensaje) {
		
		ActionListener oyente = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(marco, mensaje);
			}
			
		};
		
		return oyente;
	}
	
	//-------------------- Activar laminaSuperior --------------------//
	@SuppressWarnings("unchecked")
	private void activarComboBox(JPanel laminaSuperior) {
		Component[] laminas = laminaSuperior.getComponents();
		JPanel laminaCombo = (JPanel) laminas[0];
		JPanel laminaFiltro = (JPanel) laminas[1];
		
		Component[] componentesLC = laminaCombo.getComponents();
		Component[] componentesLF = laminaFiltro.getComponents();
		
		for(Component i : componentesLC) {
			if(i instanceof JComboBox) {
				combo = (JComboBox<String>) i;
				combo.addActionListener(devolverOyenteCombo());
			}
		}
		
		for(Component i : componentesLF) {
			if(i instanceof JTextField) {
				campoFiltro = (JTextField) i;
				campoFiltro.getDocument().addDocumentListener(devolverOyenteDocumento());
			}
			
			if(i instanceof JButton) {
				JButton botonLimpiar = (JButton) i;
				botonLimpiar.addActionListener(devolverOyenteLimpiarFiltro());
				botonLimpiar.addKeyListener(new KeyAdapter() {
					
					public void keyReleased(KeyEvent e) {
						super.keyReleased(e);
						
						if(e.getKeyCode() == KeyEvent.VK_ENTER) {
							resetearMarco(marco);
						}
					}
				});
			}
		}
	}
	
	private ActionListener devolverOyenteCombo() {
		ActionListener oyente = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String seleccion = combo.getSelectedItem().toString();
				int longSeleccion = seleccion.length();
				
				campoFiltro.setEditable(!(longSeleccion > 1));
				campoFiltro.setText("");
				
				if(!campoFiltro.isEditable()) {
					tablaPalabras.setModel(resetearTablaPalabras());
					documentoActivo = false;
				}
				else {
					agregarLaminaBuscar(seleccion);
				}
			}
		};
		
		return oyente;
	}
	
	private DocumentListener devolverOyenteDocumento() {
		DocumentListener oyente = new DocumentListener() {
			
			public void removeUpdate(DocumentEvent e) {
				filtroPalabras = campoFiltro.getText();
				agregarLaminaBuscar(combo.getSelectedItem().toString());
				documentoActivo = true;
			}
			
			public void insertUpdate(DocumentEvent e) {
				filtroPalabras = campoFiltro.getText();
				agregarLaminaBuscar(combo.getSelectedItem().toString());
				documentoActivo = true;
			}
			
			public void changedUpdate(DocumentEvent e) {}
		};
		
		return oyente;
	}
	
	private ActionListener devolverOyenteLimpiarFiltro() {
		ActionListener oyente = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				resetearMarco(marco);
			}
		};
		
		return oyente;
	}
	
	//-------------------- Activar botonera --------------------//
	private void activarBotoneraInferior(JPanel laminaInferior) {
		Component[] componentes = laminaInferior.getComponents();
		
		for(Component i : componentes) {
			if(i instanceof JButton) {
				JButton boton = (JButton) i;
				agregarAccion(boton);
			}
		}
	}
	
	private void agregarAccion(JButton boton) {
		switch(boton.getText()) {
			case "Agregar palabra":
				boton.addActionListener(devolverOyenteAgregarPalabra());
				break;
			case "Modificar palabra":
				boton.addActionListener(devolverOyenteModificarPalabra());
				break;
			case "Borrar palabra":
				boton.addActionListener(devolverOyenteBorrarPalabra());
				break;
		}
	}
	
	//-------------------- Agregar contenido --------------------//
	private void agregarLaminaBuscar(String seleccion) {
		try {
			int longSeleccion = seleccion.length();
			
			if(longSeleccion == 1) {
				Palabra[] lista = ModeloPrincipal.leerArchivo(seleccion, filtroPalabras);
				
				if(lista.length > 0) {
					tablaPalabras.setModel(crearModeloTabla(lista));
				}
				else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(
						marco, "No se encontraron palabras"
					);
					
					if(!documentoActivo) {
						resetearMarco(marco);
					}
				}
			}
		}
		catch (IOException e) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(
				marco, "No se pudo abrir el archivo",
				"Error", JOptionPane.ERROR_MESSAGE
			);
		}
	}
	
	//-------------------- Campos de clase --------------------//
	private JComboBox<String> combo;
	private JTable tablaPalabras;
	private JTextField campoFiltro;
	private String filtroPalabras;
	private boolean documentoActivo;
	private static MarcoPrincipal marco;
	private static MarcoAgregarPalabra marcoAgregarPalabra;
	private static MarcoModificarPalabra marcoModificarPalabra;
	private static MarcoBorrarPalabra marcoBorrarPalabra;
}
