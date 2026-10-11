package edu.uncg.dccharacterapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.uncg.dccharacterapi.entity.DcCharacter;
import edu.uncg.dccharacterapi.repository.DcCharacterRepository;

@Service
public class DcCharacterService {

    private final DcCharacterRepository repository;

    public DcCharacterService(DcCharacterRepository repository) {
        this.repository = repository;
    }

    public List<DcCharacter> getAllCharacters() {
        return repository.findAll();
    }

    public DcCharacter getCharacterById(Long id) {
        return repository.findById(id)
                .orElse(null);
    }

    public DcCharacter createCharacter(DcCharacter character) {
        return repository.save(character);
    }

    public DcCharacter updateCharacter(Long id, DcCharacter updatedCharacter) {
        return repository.findById(id)
                .map(character -> {
                    character.setName(updatedCharacter.getName());
                    character.setDescription(updatedCharacter.getDescription());
                    character.setUniverse(updatedCharacter.getUniverse());
                    character.setPowers(updatedCharacter.getPowers());
                    return repository.save(character);
                })
                .orElse(null);
    }

    public boolean deleteCharacter(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }

    public List<DcCharacter> getCharactersByUniverse(String universe) {
        return repository.findByUniverseIgnoreCase(universe);
    }

    public List<DcCharacter> searchCharactersByName(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }
}
