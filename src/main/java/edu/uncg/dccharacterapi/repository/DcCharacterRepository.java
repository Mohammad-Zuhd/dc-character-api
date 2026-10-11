package edu.uncg.dccharacterapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.uncg.dccharacterapi.entity.DcCharacter;

@Repository
public interface DcCharacterRepository
        extends JpaRepository<DcCharacter, Long> {

    List<DcCharacter> findByUniverseIgnoreCase(String universe);

    List<DcCharacter> findByNameContainingIgnoreCase(String name);
}
