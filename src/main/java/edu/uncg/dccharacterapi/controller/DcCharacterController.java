
package edu.uncg.dccharacterapi.controller;

import edu.uncg.dccharacterapi.entity.DcCharacter;
import edu.uncg.dccharacterapi.service.DcCharacterService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/characters")
public class DcCharacterController {

    private final DcCharacterService service;

    public DcCharacterController(DcCharacterService service) {
        this.service = service;
    }

    @GetMapping
    public List<DcCharacter> getAllCharacters(
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String name) {

        if (role != null && !role.isBlank()) {
            return service.getCharactersByRole(role);
        }

        if (name != null && !name.isBlank()) {
            return service.searchCharactersByName(name);
        }

        return service.getAllCharacters();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DcCharacter> getCharacterById(@PathVariable Long id) {
        DcCharacter character = service.getCharacterById(id);

        if (character == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(character);
    }

    @PostMapping
    public ResponseEntity<DcCharacter> createCharacter(
            @Valid @RequestBody DcCharacter character) {

        DcCharacter created = service.createCharacter(character);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DcCharacter> updateCharacter(
            @PathVariable Long id,
            @Valid @RequestBody DcCharacter updatedCharacter) {

        DcCharacter character = service.updateCharacter(id, updatedCharacter);

        if (character == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(character);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCharacter(@PathVariable Long id) {
        boolean deleted = service.deleteCharacter(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
