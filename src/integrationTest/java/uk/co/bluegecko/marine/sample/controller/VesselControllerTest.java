package uk.co.bluegecko.marine.sample.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import uk.co.bluegecko.marine.sample.handler.VesselHandler;
import uk.co.bluegecko.marine.sample.service.base.VesselServiceBase;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VesselController.class)
@ContextConfiguration(classes = {VesselController.class, VesselHandler.class, VesselServiceBase.class})
public class VesselControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testGetAll() throws Exception {
		mockMvc.perform(get("/vessel")
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpectAll(
						jsonPath("$.[0].name").value("Test 001"),
						jsonPath("$.[1].name").value("Test 003"),
						jsonPath("$.[2].name").value("Test 004"),
						jsonPath("$.[3].name").value("Test 005"))
				.andReturn();
	}

	@Test
	void testGetOne() throws Exception {
		mockMvc.perform(get("/vessel/{id}", new UUID(11, 1))
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name").value("Test 001"))
				.andReturn();
	}
}
