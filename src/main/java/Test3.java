import java.sql.SQLException;

import daoExample.model.dao.AutorDAO;
import daoExample.model.dao.LibroDAO;
import daoExample.model.domain.Autor;
import daoExample.model.domain.Libro;

public class Test3 {
	public static void main(String[] args) {
		AutorDAO adao = new AutorDAO();
		LibroDAO ldao= new LibroDAO();
		Autor allende;
		try {
			allende = adao.findById("12345678T");
			System.out.println(allende);
			if(allende.getLibros()==null) {
				System.out.println("Gasto recursos");
				allende.setLibros(ldao.findByAutor(allende));
			}
			
			
			if(allende.getLibros()==null) {
				System.out.println("Gasto recursos");
				allende.setLibros(ldao.findByAutor(allende));
			}
			System.out.println(allende.getLibros());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
