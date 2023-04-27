import java.sql.SQLException;

import daoExample.model.dao.AutorDAO;
import daoExample.model.dao.LibroDAO;
import daoExample.model.domain.Autor;
import daoExample.model.domain.Libro;

public class Test1 {
	public static void main(String[] args) {
		Autor a = new Autor("2","Isabel","Allende");
		Libro l=new Libro();
		l.setTitulo("La ciudad de las bestias");
		l.setIsbn("9");
		a.addLibro(l);
		
		
		AutorDAO adao = new AutorDAO();
		try {
			adao.save(a);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
