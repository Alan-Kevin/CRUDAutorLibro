package com.mx.AutorLibro.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mx.AutorLibro.dominio.Autor;

public interface IAutorDao extends JpaRepository<Autor, Integer> {

    @Query(value = "SELECT * FROM AUTORES_DB WHERE UPPER(NACIONALIDAD) = UPPER(:nac)", nativeQuery = true)
    List<Autor> buscarPorNacionalidadNative(String nac);
}