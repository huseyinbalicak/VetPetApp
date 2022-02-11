package com.ozgursoft.vetpetapp.services;

import com.ozgursoft.vetpetapp.Repository.AnimalRepository;
import com.ozgursoft.vetpetapp.model.Animal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public Page<Animal> getAnimals(int pageNum, String sortField, String sortDir) {

        Pageable pageable = PageRequest.of(pageNum - 1, 5,
                sortDir.equals("asc") ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending()
        );
        return animalRepository.findAll(pageable);
    }


    public void save(Animal animal) {

        animalRepository.save(animal);
    }


    public Optional<Animal> findById(Long id) {
        return animalRepository.findById(id);
    }

    public Animal getPetById(long id) {
        Optional<Animal> optional = animalRepository.findById(id);
        Animal pet;
        if (optional.isPresent()) {
            pet = optional.get();
        } else {
            throw new RuntimeException(" pet not found for id :: " + id);
        }
        return pet;
    }


    public void delete(Long id) {
        animalRepository.deleteById(id);
    }

    public List<Animal> findAnimalByAnimalName(String animalName) {
        return animalRepository.findByName(animalName);
    }


}
