package edu.uncg.dccharacterapi.repository;

import edu.uncg.dccharacterapi.entity.DcCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DcCharacterRepository
        extends JpaRepository<DcCharacter, Long> {

    List<DcCharacter> findByRoleIgnoreCase(String role);

    List<DcCharacter> findByNameContainingIgnoreCase(String name);
}
