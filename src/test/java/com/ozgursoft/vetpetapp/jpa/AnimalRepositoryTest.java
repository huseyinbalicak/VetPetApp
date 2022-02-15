package com.ozgursoft.vetpetapp.jpa;

import com.ozgursoft.vetpetapp.model.Animal;
import com.ozgursoft.vetpetapp.model.Owner;
import com.ozgursoft.vetpetapp.repository.AnimalRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AnimalRepositoryTest {

    @Autowired
    private AnimalRepository animalRepository;

    @Test
    void getAnimals() {
        var animalList = animalRepository.findAll();
        assertThat(animalList.size()).isEqualTo(6);
    }

    @Test
    void insertAnimal() {

        var animal=new Animal();
        animal.setName("sdfd");
        animal.setAge(45);
        animal.setBreed("fdd");
        animal.setSpecies("frrr");
        animal.setOwner(new Owner(1L));

        animalRepository.save(animal);
        var animalList = animalRepository.findAll();
        assertThat(animalList.size()).isEqualTo(7);
    }
    @Test
    void getAnimal() {
        var animalOptional = animalRepository.findById(1L);
        assertThat(animalOptional).isNotEmpty();
    }

    @Test
    void updateAnimal() {
        var animal = animalRepository.findById(1L).orElse(null);
        assertThat(animal).isNotNull();

        animal.setName("Sürgün");
        var savedAnimal = animalRepository.save(animal);

        assertThat(savedAnimal.getName()).isEqualTo("Sürgün");
    }

//    @Test
//    void deleteAnimal() {
//        Assertions.assertThatThrownBy(() -> animalRepository.deleteById(1L))
//                .hasCauseInstanceOf(DataIntegrityViolationException.class);
//    }


}
