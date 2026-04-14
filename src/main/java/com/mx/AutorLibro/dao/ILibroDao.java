package com.mx.AutorLibro.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mx.AutorLibro.dominio.Libro;

public interface ILibroDao extends JpaRepository<Libro, Integer> {

    List<Libro> findByTituloIgnoreCase(String nombre);
}