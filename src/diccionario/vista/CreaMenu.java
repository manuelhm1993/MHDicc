package diccionario.vista;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

public interface CreaMenu {
	void crearMenues(JMenuBar barra, String[] items);
	void crearSubMenues(JMenu menu, String[] items);
}
