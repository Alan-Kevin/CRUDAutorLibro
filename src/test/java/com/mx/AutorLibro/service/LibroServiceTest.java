package com.mx.AutorLibro.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mx.AutorLibro.dao.ILibroDao;
import com.mx.AutorLibro.dominio.Autor;
import com.mx.AutorLibro.dominio.Libro;

/**
 * 
 */
class LibroServiceTest {

	//Simulacion Dao

	@Mock
	private ILibroDao dao;

	@InjectMocks
	private LibroService service;

	// Objeto Autor reutilizable para las pruebas
	private Autor autor;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		
		autor= new Autor();
		autor.setId(1);
		autor.setNombre("Gabriel García Márquez");
	}

	@Test
	void testListar() {
		List<Libro> lista = Arrays.asList(
				new Libro(1, "Cien años de soledad", 350.50, autor),
				new Libro(2, "El amor en los tiempos del cólera", 280.00, autor)
		);

		// Simulamos el comportamiento del dao
		when(dao.findAll()).thenReturn(lista);

		List<Libro> resultado = service.listar();

		// Validaciones
		assertEquals(2, resultado.size());
		assertEquals("Cien años de soledad", resultado.get(0).getTitulo());
		
		verify(dao).findAll();
	}

	@Test
	void testGuardar() {
		Libro libro = new Libro(1, "Pedro Páramo", 150.0, autor);
		
		when(dao.save(libro)).thenReturn(libro);
		
		Libro guardado = service.guardar(libro);
		
		// Validaciones
		assertNotNull(guardado);
		assertEquals(150.0, guardado.getPrecio());
		assertEquals("Pedro Páramo", guardado.getTitulo());
		
		verify(dao).save(libro);
	}

	@Test
	void testEditar() {
		Libro libro = new Libro(1, "Pedro Páramo Editado", 180.0, autor);
		
		when(dao.save(libro)).thenReturn(libro);
		
		Libro editado = service.editar(libro);
		
		assertNotNull(editado);
		assertEquals("Pedro Páramo Editado", editado.getTitulo());
		assertEquals(180.0, editado.getPrecio());
		
		verify(dao).save(libro);
	}

	@Test
	void testBuscar() {
		Libro libro = new Libro(1, "Rayuela", 400.0, autor);
		
		when(dao.findById(1)).thenReturn(Optional.of(libro));
		
		Libro encontrado = service.buscar(1);
		
		assertNotNull(encontrado);
		assertEquals("Rayuela", encontrado.getTitulo());
		assertEquals("Gabriel García Márquez", encontrado.getAutor().getNombre());
		
		verify(dao).findById(1);
	}

	@Test
	void testEliminar() {
		
		service.eliminar(1);
		
		verify(dao).deleteById(1);
	}

	@Test
	void testBuscarPorTituloExacto() {
		Libro l1 = new Libro(1, "Java para principiantes", 500.0, autor);
		
		when(dao.findByTituloIgnoreCase("Java para principiantes")).thenReturn(Arrays.asList(l1));
		
		List<Libro> resultado = service.buscarPorTituloExacto("Java para principiantes");
		
		assertNotNull(resultado);
		assertFalse(resultado.isEmpty());
		assertEquals(500.0, resultado.get(0).getPrecio());
		
		verify(dao).findByTituloIgnoreCase("Java para principiantes");
	}
}