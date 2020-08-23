package diccionario.vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import diccionario.controlador.ControladorPrincipal;

public class MarcoPrincipal extends Marco {
	
	//-------------------- Métodos constructores --------------------//
	public MarcoPrincipal(int anchoMarco, int altoMarco, String titulo) {
		super(anchoMarco, altoMarco, titulo);
		
		crearBarraMenu();
		crearLaminaSuperior();
		crearLaminaCentral();
		crearLaminaInferior();
		
		ControladorPrincipal.activarEventos(this);
	}
	
	//-------------------- Construir barra de menú --------------------//
	private void crearBarraMenu() {
		barraMenu = new JMenuBar();
		crearMenues(barraMenu, new String[] {"Archivo", "Ayuda"});
		setJMenuBar(barraMenu);
	}
	
	//-------------------- Construir lámina superior --------------------//
	private void crearLaminaSuperior() {
		laminaSuperior = new JPanel(new GridLayout(1, 2));
		
		campoFiltro = new JTextField(12);
		campoFiltro.setEditable(false);
		combo = devolverCombo();
		
		JPanel laminaCombo = devolverFila(
				"Seleccionar letra:",
				combo,
				new FlowLayout());
		
		JPanel laminaFiltro = devolverFila(
				"Ingresar filtro:",
				campoFiltro,
				new FlowLayout());
		
		JButton boton = new JButton("X");
		boton.setPreferredSize(new Dimension(42, 20));
		
		laminaFiltro.add(boton);
		
		laminaSuperior.add(laminaCombo);
		laminaSuperior.add(laminaFiltro);
		
		add(laminaSuperior, BorderLayout.NORTH);
	}
	
	protected JComboBox<String> devolverCombo() {
		String[] alfabeto = new String[27];
		
		alfabeto[0] = "---";
		
		for(int i = 65; i <= 90; i++) {
			alfabeto[i - 64] = "" + (char) i;
		}
		
		return new JComboBox<String>(alfabeto);
	}
	
	//-------------------- Contrucción de la lámina central --------------------//
	private void crearLaminaCentral() {
		laminaCentral = new JPanel(new BorderLayout());
		tablaPalabras = new JTable();
		JScrollPane barras = new JScrollPane(
			tablaPalabras,
			JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		laminaCentral.add(barras, BorderLayout.CENTER);
		
		add(laminaCentral, BorderLayout.CENTER);
	}
	
	//-------------------- Contrucción de la lámina inferior --------------------//
	private void crearLaminaInferior() {
		laminaInferior = new JPanel();
		
		agregarBotones(laminaInferior, new String[] {
				"Agregar palabra",
				"Modificar palabra",
				"Borrar palabra"
			});
		
		add(laminaInferior, BorderLayout.SOUTH);
	}
	
	//-------------------- Métodos getter --------------------//
	public JMenuBar getBarraMenu() {
		return barraMenu;
	}
	
	public JPanel getLaminaSuperior() {
		return laminaSuperior;
	}
	
	public JPanel getLaminaInferior() {
		return laminaInferior;
	}
	
	public JTable getTablaPalabras() {
		return tablaPalabras;
	}
	
	public JTextField getCampoFiltro() {
		return campoFiltro;
	}
	
	public JComboBox<String> getCombo() {
		return combo;
	}
	
	//-------------------- Campos de clase --------------------//
	private JMenuBar barraMenu;
	private JPanel laminaSuperior, laminaCentral, laminaInferior;
	private JTable tablaPalabras;
	private JTextField campoFiltro;
	private JComboBox<String> combo;
	private static final long serialVersionUID = 1L;
}
