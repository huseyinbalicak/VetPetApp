package com.ozgursoft.vetpetapp.Repository;
import com.ozgursoft.vetpetapp.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    List<Animal> findByName(@Param("name") String name);


}