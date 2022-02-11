package com.ozgursoft.vetpetapp.Repository;
import com.ozgursoft.vetpetapp.model.Owner;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Collection;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long>{

    Collection<Owner> findBySurname(String lastname) throws DataAccessException;

}