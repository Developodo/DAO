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

public class AutorDAO implements DAO<Autor>{
	private final static String FINDALL ="SELECT * from autor";
	private final static String FINBYID ="SELECT * from autor WHERE dni=?";
	private final static String INSERT ="INSERT INTO autor (dni,nombre,apellidos) VALUES (?,?,?)";
	private final static String UPDATE ="UPDATE autor SET nombre=?, apellidos=? WHERE dni=?";
	
	private Connection conn;
	public AutorDAO(Connection conn) {
		this.conn = conn;
	}
	public AutorDAO() {
		this.conn=ConnectionMySQL.getConnect();
	}
	
	public List<Autor> findAll() throws SQLException {
		List<Autor> result = new ArrayList();
		try(PreparedStatement pst=this.conn.prepareStatement(FINDALL)){
			try(ResultSet res = pst.executeQuery()){
				while(res.next()) {
					Autor a = new Autor();
					a.setDni(res.getString("dni"));
					a.setNombre(res.getString("nombre"));
					a.setApellidos(res.getString("apellidos"));
					result.add(a);
				}
			}
		}
		return result;
	}

	public Autor findById(String id) throws SQLException {
		Autor result = null;
		try(PreparedStatement pst=this.conn.prepareStatement(FINBYID)){
			pst.setString(1, id);
			try(ResultSet res = pst.executeQuery()){
				if(res.next()) {
					result = new Autor();
					result.setDni(res.getString("dni"));
					result.setNombre(res.getString("nombre"));
					result.setApellidos(res.getString("apellidos"));
				}
			}
		}
		return result;
	}

	public Autor save(Autor entity) throws SQLException{
		Autor result = new Autor();
		if(entity!=null) {
			Autor a = findById(entity.getDni());
			if(a == null) {
				//INSERT
				try(PreparedStatement pst=this.conn.prepareStatement(INSERT)){
					pst.setString(1, entity.getDni());
					pst.setString(2, entity.getNombre());
					pst.setString(3, entity.getApellidos());
					pst.executeUpdate();
					/** Libros */
					LibroDAO ldao = new LibroDAO(this.conn);
					for(Libro l : entity.getLibros()) {
						l.setAutor(entity);
						ldao.save(l);
					}
				}
			}else {
				//UPDATE
				try(PreparedStatement pst=this.conn.prepareStatement(UPDATE)){
					pst.setString(1, entity.getNombre());
					pst.setString(2, entity.getApellidos());
					pst.setString(3, entity.getDni());
					pst.executeUpdate();
				}
				/** Libros */
				LibroDAO ldao = new LibroDAO(this.conn);
				for(Libro l : entity.getLibros()) {
					l.setAutor(entity);
					ldao.save(l);
				}
			}
			result=entity;
		}
		return result;
	}

	public void delete(Autor entity) {
		// TODO Auto-generated method stub
		
	}
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
