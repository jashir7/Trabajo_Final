package com.sena.gestionaprendices;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

}
