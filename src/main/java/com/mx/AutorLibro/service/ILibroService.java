package com.mx.AutorLibro.service;

import java.util.List;

import com.mx.AutorLibro.dominio.Libro;

public interface ILibroService {
    
	Libro guardar(Libro libro);
	
	Libro editar(Libro libro);
	
    Libro buscar(Integer id);
    
    void eliminar(Integer id);
    
    List<Libro> listar();
    
    List<Libro> buscarPorTituloExacto(String titulo);
}