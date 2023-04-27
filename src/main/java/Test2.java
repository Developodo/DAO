import java.sql.SQLException;

import daoExample.model.dao.AutorDAO;
import daoExample.model.dao.LibroDAO;
import daoExample.model.domain.Autor;
import daoExample.model.domain.Libro;

public class Test2 {
	public static void main(String[] args) {
		Libro l = new Libro();
		l.setTitulo("El Quijote");
		l.setIsbn("1");
		Autor a = new Autor("123","Miguel","Cervantes");
		l.setAutor(a);
		
		LibroDAO ldao = new LibroDAO();
		try {
			ldao.save(l);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
