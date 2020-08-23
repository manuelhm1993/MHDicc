package diccionario.vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public abstract class MarcoCambios extends Marco {
	
	//-------------------- Métodos constructores --------------------//
	public MarcoCambios(MarcoPrincipal marcoPrincipal, String titulo) {
		super(
			marcoPrincipal.getWidth() - (marcoPrincipal.getWidth() / 6),
			(marcoPrincipal.getHeight() / 2),
			titulo);
		
		this.marcoPrincipal = marcoPrincipal;
		
		construirLaminaSuperior();
	}
	
	//-------------------- Construir lámina superior --------------------//
	protected void construirLaminaSuperior() {
		laminaSuperior = new JPanel(new GridLayout(1, 2));
		JComboBox<String> combo = new JComboBox<String>(new String[] {
				"-- Seleccionar --"
			});
		combo.setEnabled(false);
		
		JPanel laminaLetras = devolverFila(
				"Letra:",
				marcoPrincipal.devolverCombo(),
				new FlowLayout());
		JPanel laminaPalabras = devolverFila(
				"Palabra:",
				combo,
				new FlowLayout());
		
		laminaSuperior.add(laminaLetras);
		laminaSuperior.add(laminaPalabras);
		
		add(laminaSuperior, BorderLayout.NORTH);
	}
	
	//-------------------- Construir lámina inferior --------------------//
	protected abstract void construirLaminaInferior();
	
	public abstract JPanel getLaminaInferior();
	
	//-------------------- Métodos getter --------------------//
	public JPanel getLaminaSuperior() {
		return laminaSuperior;
	}
	
	public MarcoPrincipal getMarcoPrincipal() {
		return marcoPrincipal;
	}
	
	//-------------------- Campos de clase --------------------//
	private JPanel laminaSuperior;
	private MarcoPrincipal marcoPrincipal;
	private static final long serialVersionUID = 1L;
}
