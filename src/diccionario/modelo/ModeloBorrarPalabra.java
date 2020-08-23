package diccionario.modelo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ModeloBorrarPalabra extends ModeloCambios {
	
	//-------------------- Método CRUD --------------------//
	public static boolean borrarPalabra(String letra, String palabra) throws IOException {
		String ruta = devolverNombreArchivo(letra);
		String[] contenido = devolverContenidoArchivo(ruta);
		BufferedWriter filtroArchivo = new BufferedWriter(new FileWriter(ruta));
		boolean palabraBorrada = false;
		
		for(String i : contenido) {
			String[] array = i.split(" = ");
			
			if(!array[0].equals(palabra)) {
				filtroArchivo.write(i);
				filtroArchivo.newLine();
			}
			else {
				palabraBorrada = true;
			}
		}
		
		filtroArchivo.close();
		
		return palabraBorrada;
	}
}

