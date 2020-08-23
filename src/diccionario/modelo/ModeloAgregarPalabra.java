package diccionario.modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ModeloAgregarPalabra extends Modelo {
	
	//-------------------- Método CRUD --------------------//
	public static boolean escribirArchivo(String palabra, String definicion) throws IOException {
		palabra = palabra.toLowerCase();
		definicion = definicion.toLowerCase();
		
		String inicial = String.valueOf(palabra.charAt(0)).toUpperCase();
		String ruta = devolverNombreArchivo(inicial);
		BufferedWriter archivo = new BufferedWriter(new FileWriter(ruta, true));
		boolean palabraEscrita = false;
		
		if(!palabraExistente(ruta, palabra)) {
			archivo.write(palabra + " = " + definicion);
			archivo.newLine();
			palabraEscrita = true;
		}
		
		archivo.close();
		
		return palabraEscrita;
	}
	
	private static boolean palabraExistente(String nombreArchivo, String palabra) throws IOException {
		BufferedReader archivo = new BufferedReader(new FileReader(nombreArchivo));
		String linea;
		boolean palabraExistente = false;
		
		while((linea = archivo.readLine()) != null) {
			String[] array = linea.split(" = ");
			
			if(!linea.equals("") && (array.length == 2)) {
				if(array[0].equals(palabra)) {
					palabraExistente = true;
					break;
				}
			}
		}
		
		archivo.close();
		
		return palabraExistente;
	}
}
