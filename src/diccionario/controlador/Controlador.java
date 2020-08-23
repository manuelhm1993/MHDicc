package diccionario.controlador;

import java.io.IOException;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import diccionario.modelo.Modelo;
import diccionario.modelo.Palabra;
import diccionario.vista.Marco;
import diccionario.vista.MarcoPrincipal;

public class Controlador {
	
	//-------------------- Recarcar el marco --------------------//
	protected void recargarMarco(Marco marco, String nombreArchivo) throws IOException {
		if(marco instanceof MarcoPrincipal) {
			MarcoPrincipal principal = (MarcoPrincipal) marco;
			
			JComboBox<String> combo = principal.getCombo();
			JTable tablaPalabras = principal.getTablaPalabras();
			
			String inicial = String.valueOf(nombreArchivo.charAt(0)).toUpperCase();
			String ruta = Modelo.devolverNombreArchivo(inicial);
			Palabra[] listaPalabras = Modelo.devolverPalabrasRegistradas(ruta);
			
			if(listaPalabras.length > 0) {
				tablaPalabras.setModel(crearModeloTabla(listaPalabras));
				combo.setSelectedItem(inicial);
			}
			else {
				resetearMarco(marco);
			}
		}
	}
	
	protected AbstractTableModel crearModeloTabla(Palabra[] lista) {
		AbstractTableModel modelo = new AbstractTableModel() {
			
			public int getRowCount() {
				return filas;
			}
			
			public int getColumnCount() {
				return columnas;
			}
			
			public Object getValueAt(int filas, int columnas) {
				switch(columnas) {
					case 0: return lista[filas].getNombre();
					case 1: return lista[filas].getDefinicion();
				}
				
				return "Sin información";
			}
			
			public String getColumnName(int indice) {
				switch(indice) {
					case 0: return "Palabra";
					case 1: return "Significado";
				}
				
				return "Anexo";
			}
			
			private final int filas = lista.length, columnas = 2;
			private static final long serialVersionUID = 1L;
		};
		
		return modelo;
	}
	
	
	//-------------------- Resetear el marco --------------------//
	protected void resetearMarco(Marco marco) {
		if(marco instanceof MarcoPrincipal) {
			MarcoPrincipal principal = (MarcoPrincipal) marco;
			
			JTextField campoFiltro = principal.getCampoFiltro(); 
			JComboBox<String> combo = principal.getCombo();
			JTable tablaPalabras = principal.getTablaPalabras();
			
			if(campoFiltro.isEditable()) {
				campoFiltro.setText("");
				campoFiltro.setEditable(false);
			}
			
			tablaPalabras.setModel(resetearTablaPalabras());
			combo.setSelectedIndex(0);
			combo.requestFocus();
		}
	}
	
	protected AbstractTableModel resetearTablaPalabras() {
		AbstractTableModel reset = new AbstractTableModel() {
			
			public Object getValueAt(int filas, int columnas) {
				return null;
			}
			
			public int getRowCount() {
				return 0;
			}
			
			public int getColumnCount() {
				return 0;
			}
			
			private static final long serialVersionUID = 1L;
		};
		
		return reset;
	}
}
