package uk.co.bluegecko.marine.sample.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import uk.co.bluegecko.marine.sample.config.TestApplicationConfiguration;
import uk.co.bluegecko.marine.sample.handler.ErrorHandler;
import uk.co.bluegecko.marine.sample.handler.VesselHandler;
import uk.co.bluegecko.marine.sample.model.data.IdentityProvider;
import uk.co.bluegecko.marine.sample.model.data.Vessel;
import uk.co.bluegecko.marine.sample.service.VesselService;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.endsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VesselController.class)
@ContextConfiguration(classes = {VesselController.class, VesselHandler.class, ErrorHandler.class, TestApplicationConfiguration.class})
public class VesselControllerTest {

	@MockBean
	private VesselService vesselService;

	@Autowired
	private MockMvc mockMvc;

	private final List<Vessel> vessels = List.of(
			Vessel.builder().id(11, 1).identifier(IdentityProvider.NICKNAME, "Test 001")
					.tonnage(1.0).beam(2.5).length(5.1).build(),
			Vessel.builder().id(12, 2).identifier(IdentityProvider.NICKNAME, "Test 002")
					.tonnage(1.1).beam(2.4).length(5.2).active(false).build(),
			Vessel.builder().id(13, 3).identifier(IdentityProvider.NICKNAME, "Test 003")
					.tonnage(1.2).beam(2.3).length(5.3).build(),
			Vessel.builder().id(14, 4).identifier(IdentityProvider.NICKNAME, "Test 004")
					.tonnage(1.3).beam(2.2).length(5.4).build(),
			Vessel.builder().id(15, 5).identifier(IdentityProvider.NICKNAME, "Test 005")
					.tonnage(1.4).beam(2.1).length(5.5).build());

	@BeforeEach
	void setUp() {
		when(vesselService.all()).thenReturn(vessels.stream().filter(Vessel::isActive).toList());
		when(vesselService.find(any(UUID.class))).thenReturn(vessels.stream().findFirst());
		when(vesselService.delete(any(UUID.class))).thenReturn(true);
	}

	@Test
	void testGetAll() throws Exception {
		mockMvc.perform(get("/vessel")
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpectAll(
						jsonPath("$.[0].identifiers.NICKNAME").value("Test 001"),
						jsonPath("$.[1].identifiers.NICKNAME").value("Test 003"),
						jsonPath("$.[2].identifiers.NICKNAME").value("Test 004"),
						jsonPath("$.[3].identifiers.NICKNAME").value("Test 005"))
				.andReturn();
	}

	@Test
	void testGetOne() throws Exception {
		mockMvc.perform(get("/vessel/{id}", new UUID(11, 1))
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.identifiers.NICKNAME").value("Test 001"))
				.andReturn();
	}

	@Test
	void testGetInvalid() throws Exception {
		mockMvc.perform(get("/vessel/{id}", "FooBar")
						.accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
				.andDo(print())
				.andExpect(status().is4xxClientError())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("400"))
				.andExpect(jsonPath("$.title").value("Bad Request"))
				.andExpect(jsonPath("$.detail").value("Invalid UUID string: FooBar"))
				.andExpect(jsonPath("$.instance").value(endsWith("/vessel/FooBar")))
				.andExpect(jsonPath("$.timestamp").value("2000-01-01T12:30:00"))
				.andReturn();
	}

	@Test
	void testDelete() throws Exception {
		mockMvc.perform(delete("/vessel/{id}", new UUID(11, 1)))
				.andDo(print())
				.andExpect(status().isNoContent())
				.andReturn();
	}

}
