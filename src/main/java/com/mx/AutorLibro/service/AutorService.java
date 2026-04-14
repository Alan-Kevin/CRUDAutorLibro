package com.mx.AutorLibro.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mx.AutorLibro.dao.IAutorDao;
import com.mx.AutorLibro.dominio.Autor;

@Service
public class AutorService implements IAutorService {
	
	private IAutorDao dao;
	
	public AutorService(IAutorDao dao) {
		this.dao = dao;
	}

	@Override
	public Autor guardar(Autor autor) {
		return dao.save(autor);
	}

	@Override
	public Autor editar(Autor autor) {
		return dao.save(autor);
	}

	@Override
	public Autor buscar(int id) {
		return dao.findById(id).orElse(null);
	}

	@Override
	public void eliminar(Integer id) {
		dao.deleteById(id);
		
	}

	@Override
	public List<Autor> listar() {
		return (List<Autor>) dao.findAll();
	}

	@Override
	public List<Autor> filtrarNacionalidad(String nac) {
		return dao.buscarPorNacionalidadNative(nac);
	}

   
}