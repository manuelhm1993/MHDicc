package diccionario;

import diccionario.vista.MarcoPrincipal;

public class IniciarApp {
	
	public static void main(String[] args) {
		
		MarcoPrincipal marcoPrincipal = new MarcoPrincipal(600, 400, "Inicio");
		marcoPrincipal.setDefaultCloseOperation(MarcoPrincipal.EXIT_ON_CLOSE);
		marcoPrincipal.setVisible(true);
	}
}
