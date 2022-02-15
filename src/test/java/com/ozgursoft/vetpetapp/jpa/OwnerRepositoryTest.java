package com.ozgursoft.vetpetapp.jpa;

import com.ozgursoft.vetpetapp.model.Owner;
import com.ozgursoft.vetpetapp.repository.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
public class OwnerRepositoryTest {

    @Autowired
    private OwnerRepository ownerRepository;
    @Test
    void getOwners() {
        var ownerList = ownerRepository.findAll();
        assertThat(ownerList.size()).isEqualTo(7);
    }
    @Test
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
    void getOwner() {
        var ownerOptional = ownerRepository.findById(1L);
        assertThat(ownerOptional).isNotEmpty();
    }

    @Test
    void updateOwner() {
        var owner = ownerRepository.findById(1L).orElse(null);
        assertThat(owner).isNotNull();

        owner.setName("Mehmet");
        var savedOwner = ownerRepository.save(owner);

        assertThat(savedOwner.getName()).isEqualTo("Mehmet");
    }


}
