package uk.co.bluegecko.marine.sample.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import uk.co.bluegecko.marine.sample.config.JacksonConfiguration;
import uk.co.bluegecko.marine.sample.handler.VesselHandler;
import uk.co.bluegecko.marine.sample.model.data.Vessel;
import uk.co.bluegecko.marine.sample.service.VesselService;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VesselController.class)
@ContextConfiguration(classes = {VesselController.class, VesselHandler.class, JacksonConfiguration.class})
public class VesselControllerTest {

	@MockBean
	private VesselService vesselService;

	@Autowired
	private MockMvc mockMvc;

	private final List<Vessel> vessels = List.of(
			Vessel.builder().id(11, 1).name("Test 001").tonnage(1.0).width(2.5).length(5.1).build(),
			Vessel.builder().id(12, 2).active(false).name("Test 002").tonnage(1.1).width(2.4).length(5.2).build(),
			Vessel.builder().id(13, 3).name("Test 003").tonnage(1.2).width(2.3).length(5.3).build(),
			Vessel.builder().id(14, 4).name("Test 004").tonnage(1.3).width(2.2).length(5.4).build(),
			Vessel.builder().id(15, 5).name("Test 005").tonnage(1.4).width(2.1).length(5.5).build());

	@BeforeEach
	void setUp() {
		when(vesselService.all()).thenReturn(vessels.stream().filter(Vessel::isActive).toList());
		when(vesselService.find(any(UUID.class))).thenReturn(vessels.stream().findFirst());
	}

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
