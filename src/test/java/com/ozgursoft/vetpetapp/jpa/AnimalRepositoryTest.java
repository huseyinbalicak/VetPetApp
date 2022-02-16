package com.ozgursoft.vetpetapp.jpa;

import com.ozgursoft.vetpetapp.model.Animal;
import com.ozgursoft.vetpetapp.model.Owner;
import com.ozgursoft.vetpetapp.repository.AnimalRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class AnimalRepositoryTest {

    @Autowired
    private AnimalRepository animalRepository;

    @Test
    @Order(2)
    void getAnimals() {
        var animalList = animalRepository.findAll();
        assertThat(animalList.size()).isEqualTo(7);
    }

    @Test
    @Order(1)
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
    @Order(3)
    void getAnimal() {
        var animalOptional = animalRepository.findById(1L);
        assertThat(animalOptional).isNotEmpty();
    }

    @Test
    @Order(4)
    void updateAnimal() {
        var animal = animalRepository.findById(1L).orElse(null);
        assertThat(animal).isNotNull();

        animal.setName("Sürgün");
        var savedAnimal = animalRepository.save(animal);

        assertThat(savedAnimal.getName()).isEqualTo("Sürgün");
    }


    @Test
    @Order(5)
    void deleteAnimal() {

        Animal animal=animalRepository.getById(1L);
        animalRepository.delete(animal);
        Animal animal1=null;
        Optional<Animal> foundAnimal=animalRepository.findById(1L);

        if(foundAnimal.isPresent())
        {
            animal1=foundAnimal.get();
        }

        assertThat(animal1).isNull();
    }



}
