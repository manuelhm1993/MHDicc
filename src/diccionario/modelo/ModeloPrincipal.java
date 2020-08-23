package diccionario.modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class ModeloPrincipal extends Modelo {
	
	//-------------------- Método CRUD --------------------//
	public static Palabra[] leerArchivo(String nombreArchivo, String filtroPalabra) throws IOException {
		String ruta = devolverNombreArchivo(nombreArchivo);
		BufferedReader filtroArchivo = new BufferedReader(new FileReader(ruta));
		LinkedList<Palabra> listaPalabras = new LinkedList<Palabra>();
		String linea;
		
		while((linea = filtroArchivo.readLine()) != null) {
			String[] array = linea.split(" = ");
			
			if(!linea.equals("") && (array.length == 2)) {
				Palabra nuevaPalabra = new Palabra(array[0], array[1]);
				
				if(filtroPalabra == null) {
					listaPalabras.add(nuevaPalabra);
				}
				else if(coincidencia(nuevaPalabra.getNombre(), filtroPalabra)) {
					listaPalabras.add(nuevaPalabra);
				}
			}
		}
		
		filtroArchivo.close();
		
		return ordenarPalabras(listaPalabras);
	}
	
	private static boolean coincidencia(String palabra, String filtro) {
		boolean coincide = false;
		int longPalabra = palabra.length();
		int longFiltro = filtro.length();
		int cont = 0;
		String subCadena = "";
		
		for(int i = 0; i < longPalabra; i++) {
			cont = longFiltro + i;
			
			if(cont <= longPalabra) {
				subCadena = palabra.substring(i, cont);
				
				if(subCadena.equals(filtro)) {
					coincide = true;
					break;
				}
			}
			else {
				break;
			}
		}
		
		return coincide;
	}
}
