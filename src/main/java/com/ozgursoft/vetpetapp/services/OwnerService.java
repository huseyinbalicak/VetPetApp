package com.ozgursoft.vetpetapp.services;

import com.ozgursoft.vetpetapp.exception.VetPetRuntimeException;
import com.ozgursoft.vetpetapp.repository.OwnerRepository;
import com.ozgursoft.vetpetapp.model.Owner;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;

    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public Page<Owner> getOwners(int pageNum, String sortField, String sortDir) {
        Pageable pageable = PageRequest.of(pageNum - 1, 5,
                sortDir.equals("asc") ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending()
        );
        return ownerRepository.findAll(pageable);
    }

    public void save(Owner owner) {
        ownerRepository.save(owner);
    }

    public Optional<Owner> findById(Long id) {
        return ownerRepository.findById(id);
    }

    public Owner getOwnerById(long id) {
        Optional<Owner> optional = ownerRepository.findById(id);
        Owner owner;
        if (optional.isPresent()) {
            owner = optional.get();
        } else {
            throw new VetPetRuntimeException(" owner not found for id :: " + id);
        }
        return owner;
    }

    public void delete(Long id) {
        ownerRepository.deleteById(id);
    }

    public Collection<Owner> findOwnerByLastName(String lastname) throws DataAccessException {
        return ownerRepository.findBySurname(lastname);
    }


}