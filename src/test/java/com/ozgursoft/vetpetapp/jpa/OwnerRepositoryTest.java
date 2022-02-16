package com.ozgursoft.vetpetapp.jpa;

import com.ozgursoft.vetpetapp.model.Owner;
import com.ozgursoft.vetpetapp.repository.OwnerRepository;
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
public class OwnerRepositoryTest {

    @Autowired
    private OwnerRepository ownerRepository;
    @Test
    @Order(2)
    void getOwners() {
        var ownerList = ownerRepository.findAll();
        assertThat(ownerList.size()).isEqualTo(7);
    }

    @Test
    @Order(1)
    void insertOwner() {

        var owner = new Owner();
        owner.setName("Vladimir");
        owner.setSurname("Lenin");
        owner.setEmail("lenin@gmail.com");
        owner.setAddress("Bursa");
        owner.setPhoneNumber("65468464");

        ownerRepository.save(owner);
        var ownerList = ownerRepository.findAll();

        assertThat(ownerList.size()).isEqualTo(7);
    }


    @Test
    @Order(3)
    void getOwner() {
        var ownerOptional = ownerRepository.findById(1L);
        assertThat(ownerOptional).isNotEmpty();
    }

    @Test
    @Order(4)
    void updateOwner() {
        var owner = ownerRepository.findById(1L).orElse(null);
        assertThat(owner).isNotNull();

        owner.setName("Mehmet");
        var savedOwner = ownerRepository.save(owner);

        assertThat(savedOwner.getName()).isEqualTo("Mehmet");
    }


    @Test
    @Order(5)
    void deleteOwner() {

        Owner owner=ownerRepository.getById(1L);
        ownerRepository.delete(owner);
        Owner owner1=null;
        Optional<Owner> foundOwner=ownerRepository.findById(1L);

        if(foundOwner.isPresent())
        {
            owner1=foundOwner.get();
        }

        assertThat(owner1).isNull();
    }




}
