package com.mx.AutorLibro.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.AutorLibro.dominio.Autor;
import com.mx.AutorLibro.dominio.Libro;
import com.mx.AutorLibro.service.ILibroService;

@RestController
@RequestMapping("libros")
@CrossOrigin
public class LibroController {
	
	private final ILibroService service;

    public LibroController(ILibroService service) {
        this.service = service;
    }
   
    @GetMapping("listar")
    public ResponseEntity<?> listar(){
		return ResponseEntity.status(HttpStatus.OK).body(service.listar());}
    
    @PostMapping("guardar")
    public ResponseEntity<?> guardar(@RequestBody Libro libro) {
    	return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(libro));
    }
    
    @PutMapping("editar")
    public ResponseEntity<?> editar(@RequestBody Libro libro) {
    	 return ResponseEntity.status(HttpStatus.OK).body(service.editar(libro));
    }
    
    @GetMapping("buscar/{id}")
    public ResponseEntity<?> buscar(@PathVariable int id) {
        Libro libro = service.buscar(id);
        if (libro == null) {
            return new ResponseEntity<>("No se encontró el autor con ID: " + id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(libro, HttpStatus.OK);
    }
    
    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable int id) {
    
        Libro libro = service.buscar(id);
        
        if (libro == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe ese id");
        }
    
        service.eliminar(id);
        return ResponseEntity.status(HttpStatus.OK).body("Eliminacion exitosa!");
    }
    
    @GetMapping("/buscar-titulo/{titulo}")
    public ResponseEntity<?> buscarPorTitulo(@PathVariable String titulo) {
        List<Libro> resultados = service.buscarPorTituloExacto(titulo);
        if (resultados.isEmpty()) {
            return new ResponseEntity<>("No se encontró el libro: " + titulo, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultados, HttpStatus.OK);
    }

}
