package com.ozgursoft.vetpetapp.repository;
import com.ozgursoft.vetpetapp.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    List<Animal> findByName(String name);


}