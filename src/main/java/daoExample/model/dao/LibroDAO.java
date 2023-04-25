package daoExample.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import daoExample.model.connections.ConnectionMySQL;
import daoExample.model.domain.Autor;
import daoExample.model.domain.Libro;

public class LibroDAO implements DAO<Libro>{
	private final static String FINDALL ="SELECT * from libro";
	
	private Connection conn;
	public LibroDAO(Connection conn) {
		this.conn = conn;
	}
	public LibroDAO() {
		this.conn=ConnectionMySQL.getConnect();
	}
	
	
	@Override
	public List<Libro> findAll() throws SQLException {
		List<Libro> result = new ArrayList();
		try(PreparedStatement pst=this.conn.prepareStatement(FINDALL)){
			try(ResultSet res = pst.executeQuery()){
				while(res.next()) {
					Libro l = new Libro();
					l.setIsbn(res.getString("isbn"));
					l.setTitulo(res.getString("titulo"));
					AutorDAO adao = new AutorDAO();
					Autor a = adao.findById(res.getString("dni_autor"));
					l.setAutor(a);
					result.add(l);
				}
			}
		}
		return result;
	}

	@Override
	public Libro findById(String id) throws SQLException {
		return null;
	}

	@Override
	public Libro save(Libro entity) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Libro entity) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}
}
