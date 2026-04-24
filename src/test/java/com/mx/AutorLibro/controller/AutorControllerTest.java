package com.mx.AutorLibro.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
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
import com.mx.AutorLibro.service.IAutorService;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(AutorController.class)
class AutorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IAutorService service;

    @Autowired
    private ObjectMapper objectMapper; // Convierte objetos Java a JSON

    private Autor autorPrueba;

    @BeforeEach
    void setUp() {
        autorPrueba = new Autor(1, "Gabriel García Márquez", "Colombiana", new ArrayList<>());
    }

    @Test
    void testListar() throws Exception {
        when(service.listar()).thenReturn(Arrays.asList(autorPrueba));

        mockMvc.perform(get("/autores/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Gabriel García Márquez"));
    }

    @Test
    void testGuardar() throws Exception {
        when(service.guardar(any(Autor.class))).thenReturn(autorPrueba);

        mockMvc.perform(post("/autores/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(autorPrueba)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Gabriel García Márquez"));
    }

    @Test
    void testEditar() throws Exception {
        when(service.editar(any(Autor.class))).thenReturn(autorPrueba);

        mockMvc.perform(put("/autores/editar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(autorPrueba)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nacionalidad").value("Colombiana"));
    }

    @Test
    void testBuscarEncontrado() throws Exception {
        when(service.buscar(1)).thenReturn(autorPrueba);

        mockMvc.perform(get("/autores/buscar/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testBuscarNoEncontrado() throws Exception {
        when(service.buscar(99)).thenReturn(null);

        mockMvc.perform(get("/autores/buscar/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No se encontró el autor con ID: 99"));
    }

    @Test
    void testEliminarExitoso() throws Exception {
        // Simulamos que el autor sí existe primero
        when(service.buscar(1)).thenReturn(autorPrueba);

        mockMvc.perform(delete("/autores/eliminar/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Eliminacion exitosa!"));
    }

    @Test
    void testBuscarPorNacionalidad() throws Exception {
        when(service.filtrarNacionalidad("Mexicana"))
            .thenReturn(Arrays.asList(new Autor(2, "Juan Rulfo", "Mexicana", new ArrayList<>())));

        mockMvc.perform(get("/autores/nacionalidad/Mexicana"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juan Rulfo"));
    }

}