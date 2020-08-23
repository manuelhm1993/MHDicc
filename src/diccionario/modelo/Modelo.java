package diccionario.modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Modelo {
	
	//-------------------- Métodos getter --------------------//
	public static String devolverNombreArchivo(String inicial) {
		String ruta = "bin" 
				+ File.separator + "palabras"
				+ File.separator + "Palabras" + inicial + ".txt";
		
		return ruta;
	}
	
	protected static Palabra[] ordenarPalabras(List<Palabra> listaPalabras) {
		Palabra[] palabras = listaPalabras.toArray(new Palabra[listaPalabras.size()]);
		Arrays.sort(palabras);
		
		return palabras;
	}
	
	public static Palabra[] devolverPalabrasRegistradas(String nombreArchivo) throws IOException {
		BufferedReader archivo = new BufferedReader(new FileReader(nombreArchivo));
		LinkedList<Palabra> listaPalabras = new LinkedList<Palabra>();
		String linea;
		
		while((linea = archivo.readLine()) != null) {
			String[] array = linea.split(" = ");
			
			if(!linea.equals("") && (array.length == 2)) {
				Palabra palabraSiguiente = new Palabra(array[0], array[1]);
				listaPalabras.add(palabraSiguiente);
			}
		}
		
		archivo.close();
		
		return ordenarPalabras(listaPalabras);
	}
	
	public static String[] devolverDataCombo(String letra) throws IOException {
		String ruta = devolverNombreArchivo(letra);
		BufferedReader filtroArchivo = new BufferedReader(new FileReader(ruta));
		LinkedList<String> listaPalabras = new LinkedList<String>();
		String linea;
		
		listaPalabras.add("-- Seleccionar --");
		
		while((linea = filtroArchivo.readLine()) != null) {
			String[] array = linea.split(" = ");
			
			if(!linea.equals("") && (array.length == 2)) {
				listaPalabras.add(array[0]);
			}
		}
		
		filtroArchivo.close();
		
		String[] palabras = listaPalabras.toArray(new String[listaPalabras.size()]);
		Arrays.sort(palabras);
		
		return palabras;
	}
}
