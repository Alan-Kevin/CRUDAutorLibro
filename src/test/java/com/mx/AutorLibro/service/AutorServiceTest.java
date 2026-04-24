package com.mx.AutorLibro.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList; // Importante para usar ArrayList
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mx.AutorLibro.dao.IAutorDao;
import com.mx.AutorLibro.dominio.Autor;

class AutorServiceTest {

	
	@Mock
	private IAutorDao dao;

	@InjectMocks
	private AutorService service;

	@BeforeEach
	void setUp() {
		// Inicializamos los mocks antes de cada prueba unitaria
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testListar() {
		List<Autor> lista = Arrays.asList(
				// Sustituimos null por una lista vacía para cumplir con el constructor
				new Autor(1, "Juan Rulfo", "Mexicana", new ArrayList<>()),
				new Autor(2, "Gabriel García Márquez", "Colombiana", new ArrayList<>())
		);

		// Simulamos el comportamiento del dao
		when(dao.findAll()).thenReturn(lista);

		List<Autor> resultado = service.listar();

		// Validaciones
		assertEquals(2, resultado.size());
		assertEquals("Juan Rulfo", resultado.get(0).getNombre());

		verify(dao).findAll();
	}

	@Test
	void testGuardar() {
		Autor autor = new Autor(1, "Octavio Paz", "Mexicana", new ArrayList<>());

		// Simulamos que el dao guarda y retorna el objeto
		when(dao.save(autor)).thenReturn(autor);

		Autor guardado = service.guardar(autor);

		// Validaciones
		assertNotNull(guardado);
		assertEquals("Octavio Paz", guardado.getNombre());
		assertEquals("Mexicana", guardado.getNacionalidad());

		verify(dao).save(autor);
	}

	@Test
	void testEditar() {
		Autor autor = new Autor(1, "Borges", "Argentina", new ArrayList<>());

		when(dao.save(autor)).thenReturn(autor);

		Autor editado = service.editar(autor);

		assertNotNull(editado);
		assertEquals("Argentina", editado.getNacionalidad());

		verify(dao).save(autor);
	}

	@Test
	void testBuscar() {
		Autor autor = new Autor(1, "Julio Cortázar", "Argentina", new ArrayList<>());

		when(dao.findById(1)).thenReturn(Optional.of(autor));

		Autor encontrado = service.buscar(1);

		assertNotNull(encontrado);
		assertEquals("Julio Cortázar", encontrado.getNombre());

		verify(dao).findById(1);
	}

	@Test
	void testEliminar() {
		service.eliminar(1);

		verify(dao).deleteById(1);
	}

	@Test
	void testFiltrarNacionalidad() {
		Autor a1 = new Autor(1, "Juan Rulfo", "Mexicana", new ArrayList<>());
		Autor a2 = new Autor(2, "Carlos Fuentes", "Mexicana", new ArrayList<>());

		when(dao.buscarPorNacionalidadNative("Mexicana")).thenReturn(Arrays.asList(a1, a2));

		List<Autor> resultado = service.filtrarNacionalidad("Mexicana");

		// Validaciones
		assertEquals(2, resultado.size());
		assertTrue(resultado.stream().allMatch(a -> a.getNacionalidad().equals("Mexicana")));

		verify(dao).buscarPorNacionalidadNative("Mexicana");
	}
}