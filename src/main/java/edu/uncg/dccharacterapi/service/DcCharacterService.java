
package edu.uncg.dccharacterapi.service;

import edu.uncg.dccharacterapi.entity.DcCharacter;
import edu.uncg.dccharacterapi.repository.DcCharacterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DcCharacterService {

    private final DcCharacterRepository repository;

    public DcCharacterService(DcCharacterRepository repository) {
        this.repository = repository;
    }

    public List<DcCharacter> getAllCharacters() {
        return repository.findAll();
    }

    public List<DcCharacter> getCharactersByRole(String role) {
        return repository.findByRoleIgnoreCase(role);
    }

      // Combines optional filters. Either argument may be null.
    public List<DcCharacter> searchCharacters(String name, String role) {

        String searchName = name == null ? "" : name.trim();
        String searchRole = role == null ? "" : role.trim();

        boolean hasName = !searchName.isBlank();
        boolean hasRole = !searchRole.isBlank();

        if (hasName && hasRole) {
            return repository.findByNameContainingIgnoreCaseAndRoleIgnoreCase(
                    searchName, searchRole);
        }
        if (hasName) {
            return repository.findByNameContainingIgnoreCase(searchName);
        }
        if (hasRole) {
            return repository.findByRoleIgnoreCase(searchRole);
        }
        return repository.findAll();
    }

    public DcCharacter getCharacterById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public DcCharacter createCharacter(DcCharacter character) {
        return repository.save(character);
    }

    public DcCharacter updateCharacter(Long id, DcCharacter updatedCharacter) {
        DcCharacter existing = getCharacterById(id);

        if (existing == null) {
            return null;
        }

        existing.setName(updatedCharacter.getName());
        existing.setDescription(updatedCharacter.getDescription());
        existing.setRole(updatedCharacter.getRole());
        existing.setSpecies(updatedCharacter.getSpecies());
        existing.setLocation(updatedCharacter.getLocation());
        existing.setPowers(updatedCharacter.getPowers());
        existing.setUniverse(updatedCharacter.getUniverse());
        existing.setImageUrl(updatedCharacter.getImageUrl());

        return repository.save(existing);
    }

    public boolean deleteCharacter(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }

    public List<DcCharacter> searchCharactersByName(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }
}
