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
import com.mx.AutorLibro.service.IAutorService;

@RestController
@RequestMapping("autores")
@CrossOrigin 
public class AutorController {

    private final IAutorService service;

	public AutorController(IAutorService service) {
		this.service = service;
	}
    
	@GetMapping("listar")
	public ResponseEntity<?> listar(){
		return ResponseEntity.status(HttpStatus.OK).body(service.listar());}

    
    @PostMapping("guardar")
    public ResponseEntity<?> guardar(@RequestBody Autor autor) {
    	return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(autor));
    }

    
    @PutMapping("editar")
    public ResponseEntity<?> editar(@RequestBody Autor autor) {
    	 return ResponseEntity.status(HttpStatus.OK).body(service.editar(autor));
    }

    
    @GetMapping("buscar/{id}")
    public ResponseEntity<?> buscar(@PathVariable int id) {
        Autor autor = service.buscar(id);
        if (autor == null) {
            return new ResponseEntity<>("No se encontró el autor con ID: " + id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(autor, HttpStatus.OK);
    }

    
    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable int id) {
        Autor autor = service.buscar(id);
        
        if (autor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe ese id");
        }
        
        service.eliminar(id);
        return ResponseEntity.status(HttpStatus.OK).body("Eliminacion exitosa!");
    }
    
    @GetMapping("nacionalidad/{nac}")
    public ResponseEntity<?> buscarPorNac(@PathVariable String nac) {
        List<Autor> lista = service.filtrarNacionalidad(nac);
        if (lista.isEmpty()) {
            return new ResponseEntity<>("No se encontraron autores con la nacionalidad: " + nac, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }
    
}
