//package com.banking_api.banking_api.controller;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.junit.jupiter.api.Assertions.*;
//@SpringBootTest
//@AutoConfigureMockMvc
//@AutoConfigureJsonTesters
//class UserControllerTest {
//
//
//        @Autowired
//        private MockMvc mvc;
//
//        @Autowired
//        private JacksonTester<DadosCadastroMedico> dadosCadastroMedicoJson;
//
//        @Autowired
//        private JacksonTester<DadosDetalhamentoMedico> dadosDetalhamentoMedicoJson;
//
//        @MockBean
//        private MedicoRepository repository;
//
//        @Test
//        @DisplayName("Deveria devolver codigo http 400 quando informacoes estao invalidas")
//        @WithMockUser
//        void cadastrar_cenario1() throws Exception {
//            var response = mvc
//                    .perform(post("/medicos"))
//                    .andReturn().getResponse();
//
//            assertThat(response.getStatus())
//                    .isEqualTo(HttpStatus.BAD_REQUEST.value());
//        }
//}