package com.ruta.backend.monolito.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruta.backend.monolito.dto.PersonDTO;
import com.ruta.backend.monolito.entities.DocumentType;
import com.ruta.backend.monolito.exceptions.ControllerAdvice;
import com.ruta.backend.monolito.services.Integrator;
import com.ruta.backend.monolito.services.interfaces.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

class PersonControllerTest {

    private MockMvc mvc;
    private PersonDTO personDTO;
    private DocumentType documentType = DocumentType.CC;
    private JacksonTester<PersonDTO> JSONpersonaDTO;

    @InjectMocks
    PersonController personController;

    @Mock
    PersonService personService;

    @Mock
    Integrator integrator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        JacksonTester.initFields(this, new ObjectMapper());
        // MockMvc standalone approach
        mvc = MockMvcBuilders.standaloneSetup(personController)
                .setControllerAdvice(new ControllerAdvice())
                .build();

        personDTO = new PersonDTO();
        personDTO.setNombres("pedro carlos");
        personDTO.setApellidos("gomez torres");
        personDTO.setNumDocumento("1012443736");
        personDTO.setTipoDocumento(documentType);
        personDTO.setEdad(24);
        personDTO.setCiudad("Bogotá");
        personDTO.setImagen(false);
    }

    @Test
    void createPersona() throws Exception {
        // when
        MockHttpServletResponse response = mvc.perform(
                        post("/create/persona").contentType(MediaType.APPLICATION_JSON)
                                .content(JSONpersonaDTO.write(personDTO).getJson()))
                                .andReturn().getResponse();

        //then
        assertEquals(response.getStatus(), HttpStatus.CREATED.value());
        assertTrue(response.getContentAsString().isEmpty());
    }

    @Test
    void getPersonaByTipoAndId() throws Exception {
        //given
        given(personService.findByDocumentTypeAndNumDocument("CC","1012443736"))
                .willReturn(Optional.of(personDTO));

        // when
        MockHttpServletResponse response = mvc.perform(
                        get("/getBy/TipoAndId/CC/1012443736")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        //then
        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(StandardCharsets.UTF_8), JSONpersonaDTO.write(personDTO).getJson());
    }

    @Test
    void getPersonaByEdad() throws Exception {
        //given
        List<PersonDTO> personDTOList = new ArrayList<>();
        personDTOList.add(personDTO);

        given(personService.findAllByAgeGreaterThanEqual(18))
                .willReturn(personDTOList);

        // when
        MockHttpServletResponse response = mvc.perform(
                        get("/getByEdad/18")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        //then
        List<String> jsonList = new ArrayList<>();
        jsonList.add(JSONpersonaDTO.write(personDTO).getJson());
        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(StandardCharsets.UTF_8), jsonList.toString());
    }

    @Test
    void updatePersona() throws Exception {
        // when
        MockHttpServletResponse response = mvc.perform(
                        put("/update").contentType(MediaType.APPLICATION_JSON)
                                .content(JSONpersonaDTO.write(personDTO).getJson()))
                .andReturn().getResponse();

        //then
        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertTrue(response.getContentAsString().isEmpty());
    }

    @Test
    void deletePersona() throws Exception{
        // when
        MockHttpServletResponse response = mvc.perform(
                        delete("/delete/1012443736").accept(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        //then
        assertEquals(response.getStatus(), HttpStatus.NO_CONTENT.value());
        assertTrue(response.getContentAsString().isEmpty());
    }
}