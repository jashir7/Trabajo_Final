package com.sena.gestionaprendices;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class GestionaprendicesApplicationTests {

	private static final String PUBLIC_FRONTEND_ORIGIN =
			"https://r114qqv6-5173.use2.devtunnels.ms";

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	void contextLoads() {
	}

	@Test
	void allowsPreflightFromPublicFrontend() throws Exception {
		mockMvc.perform(options("/api/dashboard/resumen")
				.header("Origin", PUBLIC_FRONTEND_ORIGIN)
				.header("Access-Control-Request-Method", "GET"))
				.andExpect(status().isOk())
				.andExpect(header().string("Access-Control-Allow-Origin", PUBLIC_FRONTEND_ORIGIN));
	}

	@Test
	void rejectsPreflightFromUnknownOrigin() throws Exception {
		mockMvc.perform(options("/api/dashboard/resumen")
				.header("Origin", "https://untrusted.example")
				.header("Access-Control-Request-Method", "GET"))
				.andExpect(status().isForbidden());
	}

	@Test
	void persistsCoursesAndKeepsTheApiJsonShape() throws Exception {
		mockMvc.perform(post("/api/cursos")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
				        {
				          "nombre": "Curso persistente",
				          "descripcion": "Guardado en la base de datos",
				          "url": "https://example.test/curso"
				        }
				        """))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.nombre").value("Curso persistente"));

		mockMvc.perform(get("/api/cursos"))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("\"nombre\":\"Curso persistente\"")));
	}

}
