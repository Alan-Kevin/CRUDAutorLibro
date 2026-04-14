package com.mx.AutorLibro.service;

import java.util.List;

import com.mx.AutorLibro.dominio.Autor;

public interface IAutorService {
   
	Autor guardar(Autor autor);
	
	Autor editar(Autor autor);
	
    Autor buscar(int id);
    
    void eliminar(Integer id);
    
	List<Autor> listar();
    
    List<Autor> filtrarNacionalidad(String nac);
}