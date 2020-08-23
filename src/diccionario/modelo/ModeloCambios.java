package diccionario.modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

public class ModeloCambios extends Modelo {
	
	protected static String[] devolverContenidoArchivo(String nombreArchivo) throws IOException {
		BufferedReader filtroArchivo = new BufferedReader(new FileReader(nombreArchivo));
		LinkedList<String> listaPalabras = new LinkedList<String>();
		String linea;
		
		while((linea = filtroArchivo.readLine()) != null) {
			String[] array = linea.split(" = ");
			
			if(!linea.equals("") && (array.length == 2)) {
				listaPalabras.add(linea);
			}
		}
		
		filtroArchivo.close();
		
		String[] palabras = listaPalabras.toArray(new String[listaPalabras.size()]);
		Arrays.sort(palabras);
		
		return palabras;
	}
}
