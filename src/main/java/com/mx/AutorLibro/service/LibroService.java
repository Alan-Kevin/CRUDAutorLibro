package com.mx.AutorLibro.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mx.AutorLibro.dao.ILibroDao;
import com.mx.AutorLibro.dominio.Libro;
@Service
public class LibroService implements ILibroService {
	
	private ILibroDao dao;
	

	public LibroService(ILibroDao dao) {
		this.dao = dao;
	}

	@Override
	public Libro guardar(Libro libro) {
		return dao.save(libro);
	}

	@Override
	public Libro editar(Libro libro) {
		return dao.save(libro);
	}

	@Override
	public Libro buscar(Integer id) {
		return dao.findById(id).orElse(null);
	}

	@Override
	public void eliminar(Integer id) {
		dao.deleteById(id);
		
	}

	@Override
	public List<Libro> listar() {
		return (List<Libro>) dao.findAll();
	}

	@Override
	public List<Libro> buscarPorTituloExacto(String titulo) {
		return dao.findByTituloIgnoreCase(titulo);
	}

}
