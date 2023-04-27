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
	private final static String FINDBYID ="SELECT * from libro WHERE isbn=?";
	private final static String INSERT ="INSERT INTO libro (isbn,titulo,dni_autor) VALUES (?,?,?)";
	private final static String UPDATE ="UPDATE libro SET titulo=?, dni_autor=? WHERE isbn=?";
	private final static String FINDBYAUTOR ="SELECT * from libro WHERE dni_autor=?";

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
					AutorDAO adao = new AutorDAO(this.conn);
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
		Libro result = null;
		try(PreparedStatement pst=this.conn.prepareStatement(FINDBYID)){
			pst.setString(1, id);
			try(ResultSet res = pst.executeQuery()){
				if(res.next()) {
					Libro l = new Libro();
					l.setIsbn(res.getString("isbn"));
					l.setTitulo(res.getString("titulo"));
					AutorDAO adao = new AutorDAO(this.conn);
					Autor a = adao.findById(res.getString("dni_autor"));
					l.setAutor(a);				
					result = l;
				}
			}
		}
		return result;
	}

	@Override
	public Libro save(Libro entity) throws SQLException {
		Libro result = new Libro();
		if(entity!=null) {
			Libro l = findById(entity.getIsbn());
			if(l == null) {
				//INSERT
				AutorDAO adao = new AutorDAO(this.conn);
				adao.save(entity.getAutor());
				try(PreparedStatement pst=this.conn.prepareStatement(INSERT)){
					pst.setString(1, entity.getIsbn());
					pst.setString(2, entity.getTitulo());
					pst.setString(3, entity.getAutor().getDni());
					pst.executeUpdate();
				}
			}else {
				//UPDATE
				AutorDAO adao = new AutorDAO(this.conn);
				adao.save(entity.getAutor());
				try(PreparedStatement pst=this.conn.prepareStatement(UPDATE)){
					pst.setString(1, entity.getTitulo());
					pst.setString(2, entity.getAutor().getDni());
					pst.setString(3, entity.getIsbn());
					pst.executeUpdate();
				}
			}
			result=entity;
		}
		return result;
	}

	@Override
	public void delete(Libro entity) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	
	public List<Libro> findByAutor(Autor a) throws SQLException {
		List<Libro> result = new ArrayList();
		AutorDAO adao = new AutorDAO(this.conn);
		Autor _a = adao.findById(a.getDni());
		if(_a != null) {
			try(PreparedStatement pst=this.conn.prepareStatement(FINDBYAUTOR)){
				pst.setString(1, a.getDni());
				try(ResultSet res = pst.executeQuery()){
					while(res.next()) {
						Libro l = new Libro();
						l.setIsbn(res.getString("isbn"));
						l.setTitulo(res.getString("titulo"));
						l.setAutor(_a);
						result.add(l);
					}
				}
			}
		}
		return result;
	}

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}
}
