package diccionario.modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ModeloModificarPalabra extends ModeloCambios {
	
	//-------------------- Método CRUD --------------------//
	public static boolean modificarPalabra(String inicial, String palabra, String palabraModificada, String definicionModificada) throws IOException {
		String ruta = devolverNombreArchivo(inicial);
		String[] contenido = devolverContenidoArchivo(ruta);
		BufferedWriter filtroArchivo = new BufferedWriter(new FileWriter(ruta));
		boolean modificacionHecha = false;
		
		for(String i : contenido) {
			String[] array = i.split(" = ");
			
			if(!array[0].equals(palabra)) {
				filtroArchivo.write(i);
				filtroArchivo.newLine();
			}
			else {
				filtroArchivo.write(palabraModificada + " = " + definicionModificada);
				filtroArchivo.newLine();
				modificacionHecha = true;
			}
		}
		
		filtroArchivo.close();
		
		return modificacionHecha;
	}
	
	public static String devolverDefinicionPalabra(String palabra) throws IOException {
		palabra = palabra.toLowerCase();
		
		String inicial = String.valueOf(palabra.charAt(0)).toUpperCase();
		String ruta = devolverNombreArchivo(inicial);
		BufferedReader filtroArchivo = new BufferedReader(new FileReader(ruta));
		String linea, definicion = null;
		
		while((linea = filtroArchivo.readLine()) != null) {
			String[] array = linea.split(" = ");
			
			if(!linea.equals("") && (array.length == 2)) {
				if(array[0].equals(palabra)) {
					definicion = array[1];
					break;
				}
			}
		}
		
		filtroArchivo.close();
		
		return definicion;
	}
}
