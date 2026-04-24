package com.mx.AutorLibro.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.mx.AutorLibro.dominio.Autor;
import com.mx.AutorLibro.dominio.Libro;

import com.mx.AutorLibro.service.ILibroService;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(LibroController.class)
class LibroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ILibroService service;

    @Autowired
    private ObjectMapper objectMapper; // Para convertir objetos a JSON

    private Libro libroPrueba;
    private Autor autorDummy;

    @BeforeEach
    void setUp() {
        autorDummy = new Autor(1, "Gabriel García Márquez", "Colombiana", null);
        libroPrueba = new Libro(1, "Cien años de soledad", 350.0, autorDummy);
    }

    @Test
    void testListar() throws Exception {
        when(service.listar()).thenReturn(Arrays.asList(libroPrueba));

        mockMvc.perform(get("/libros/listar"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].titulo").value("Cien años de soledad"));
    }

    @Test
    void testGuardar() throws Exception {
        when(service.guardar(any(Libro.class))).thenReturn(libroPrueba);

        mockMvc.perform(post("/libros/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(libroPrueba)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value("Cien años de soledad"));
    }

    @Test
    void testBuscarEncontrado() throws Exception {
        when(service.buscar(1)).thenReturn(libroPrueba);

        mockMvc.perform(get("/libros/buscar/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testBuscarNoEncontrado() throws Exception {
        when(service.buscar(99)).thenReturn(null);

        mockMvc.perform(get("/libros/buscar/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No se encontró el autor con ID: 99"));
    }

    @Test
    void testEliminarExitoso() throws Exception {
        when(service.buscar(1)).thenReturn(libroPrueba);

        mockMvc.perform(delete("/libros/eliminar/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Eliminacion exitosa!"));
    }

    @Test
    void testBuscarPorTitulo() throws Exception {
        when(service.buscarPorTituloExacto("Quijote")).thenReturn(Collections.singletonList(libroPrueba));

        mockMvc.perform(get("/libros/buscar-titulo/Quijote"))
                .andExpect(status().isOk());
    }
}