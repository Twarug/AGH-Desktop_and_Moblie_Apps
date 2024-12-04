package dev.twardosz;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.twardosz.dto.AnimalCreateRequest;
import dev.twardosz.dto.AnimalShelterCreateRequest;
import dev.twardosz.dto.RatingCreateRequest;
import dev.twardosz.models.Animal;
import dev.twardosz.models.AnimalCondition;
import dev.twardosz.models.AnimalShelter;
import dev.twardosz.repositories.AnimalRepository;
import dev.twardosz.repositories.AnimalShelterRepository;
import dev.twardosz.repositories.RatingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@AutoConfigureMockMvc
public class AnimalShelterControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AnimalRepository animalRepository;

    @MockitoBean
    private AnimalShelterRepository animalShelterRepository;

    @MockitoBean
    private RatingRepository ratingRepository;

    @Test
    void shouldAddAnimal() throws Exception {
        AnimalCreateRequest animal = new AnimalCreateRequest("Dog", "Buddy", "Healthy", 3, 150.00, 1L);
        mockMvc.perform(post("/api/animal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(animal)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteAnimal() throws Exception {
        Long animalId = 1L;
        mockMvc.perform(delete("/api/animal/{id}", animalId))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnAnimalDetails() throws Exception {
        Long animalId = 1L;

        when(animalRepository.getReferenceById(animalId)).thenReturn(new Animal("Buddy", "Dog", AnimalCondition.Healthy, 3, 150.00,  null));

        mockMvc.perform(get("/api/animal/{id}", animalId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Buddy"));
    }

    @Test
    void shouldReturnShelterDataAsCSV() throws Exception {
        mockMvc.perform(get("/api/animalShelter/csv"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/csv;charset=UTF-8"));
    }

    @Test
    void shouldReturnAllShelters() throws Exception {
        mockMvc.perform(get("/api/animalShelter"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldAddNewShelter() throws Exception {
        AnimalShelterCreateRequest shelter = new AnimalShelterCreateRequest("City Shelter", 10);

        mockMvc.perform(post("/api/animalShelter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(shelter)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnShelterCapacity() throws Exception {
        Long shelterId = 1L;

        when(animalShelterRepository.getReferenceById(shelterId)).thenReturn(new AnimalShelter("City Shelter", 10));

        mockMvc.perform(get("/api/animalShelter/{id}/fill", shelterId))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteShelter() throws Exception {
        Long shelterId = 1L;

        mockMvc.perform(delete("/api/animalShelter/{id}", shelterId))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnAnimalsInShelter() throws Exception {
        Long shelterId = 1L;

        mockMvc.perform(get("/api/animalShelter/{id}/animal", shelterId))
                .andExpect(status().isOk());
    }

    @Test
    void shouldAddShelterRating() throws Exception {
        RatingCreateRequest rating = new RatingCreateRequest(1L, 5, "Great");

        mockMvc.perform(post("/api/rating")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(rating)))
                .andExpect(status().isOk());
    }

}
