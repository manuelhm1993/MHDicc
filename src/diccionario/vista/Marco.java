package diccionario.vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Marco extends JFrame implements CreaMenu {
	
	//-------------------- Métodos constructores --------------------//
	public Marco(int anchoMarco, int altoMarco, String titulo) {
		setLayout(new BorderLayout());
		
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		
		anchoPantalla = pantalla.width;
		altoPantalla = pantalla.height;
		
		pantalla.setSize(anchoMarco, altoMarco);
		
		setTitle("DiccMH - " + titulo);
		setBounds(
			(anchoPantalla - anchoMarco) / 2,
			(altoPantalla - altoMarco) / 2,
			anchoMarco, altoMarco
		);
		
		setMinimumSize(pantalla);
	}
	
	//-------------------- Métodos setter --------------------//
	protected void agregarBotones(JComponent c, String[] items) {
		for(String i : items) {
			c.add(new JButton(i));
		}
	}
	
	protected void agregarBotones(JComponent c, String[] items, boolean activado) {
		for(String i : items) {
			JButton boton = new JButton(i);
			boton.setEnabled(activado);
			c.add(boton);
		}
	}
	
	//-------------------- Métodos getter --------------------//
	protected JPanel devolverFila(String rotulo, JComponent componente, LayoutManager disposicion) {
		JPanel fila = new JPanel(disposicion);
		
		fila.add(new JLabel(rotulo));
		fila.add(componente);
		
		return fila;
	}
	
	protected JPanel devolverFila(JComponent[] componentes, LayoutManager disposicion) {
		JPanel lamina = new JPanel(disposicion);
		int longComponentes = componentes.length;
		
		for(int i = 0; i < longComponentes; i += 2) {
			lamina.add(componentes[i]);
			lamina.add(componentes[i + 1]);
		}
		
		return lamina;
	}
	
	public int getAnchoPantalla() {
		return anchoPantalla;
	}
	
	public int getAltoPantalla() {
		return altoPantalla;
	}
	
	//-------------------- Métodos implementados --------------------//
	public void crearMenues(JMenuBar barra, String[] items) {
		for(String i : items) {
			JMenu menu = new JMenu(i);
			switch(menu.getText()) {
				case "Archivo":
					crearSubMenues(menu, new String[] {
							"Agregar palabra",
							"Modificar palabra",
							"Borrar palabra", 
							"Salir"
						}
					);
					break;
				case "Ayuda":
					crearSubMenues(menu, new String[] {
							"Licencia",
							"Acerca de..."
						});
					break;
			}
			
			barra.add(menu);
		}
	}
	
	public void crearSubMenues(JMenu menu, String[] items) {
		for(String i : items) {
			JMenuItem subMenu = new JMenuItem(i);
			menu.add(subMenu);
		}
	}
	
	//-------------------- Campos de clase --------------------//
	private int anchoPantalla, altoPantalla;
	private static final long serialVersionUID = 1L;
}
