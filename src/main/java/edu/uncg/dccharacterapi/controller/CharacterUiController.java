
package edu.uncg.dccharacterapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.uncg.dccharacterapi.entity.DcCharacter;
import edu.uncg.dccharacterapi.service.DcCharacterService;
import jakarta.validation.Valid;

@Controller
public class CharacterUiController {

    private final DcCharacterService service;

    public CharacterUiController(DcCharacterService service) {
        this.service = service;
    }

  
    // Gallery and search
    @GetMapping("/")
    public String index(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String role,
            Model model) {

        model.addAttribute("characters",
                service.searchCharacters(name, role));

        model.addAttribute("name", name == null ? "" : name);
        model.addAttribute("role", role == null ? "" : role);

        return "index";
    }


    // ----- Details -----
    @GetMapping("/characters/{id}")
    public String details(@PathVariable Long id, Model model) {

        DcCharacter character = service.getCharacterById(id);

        if (character == null) {
            model.addAttribute("message", "Character not found.");
            return "error";
        }

        model.addAttribute("character", character);

        return "details";
    }

    // ----- Add -----
    @GetMapping("/characters/new")
    public String newForm(Model model) {

        model.addAttribute("character", new DcCharacter());
        model.addAttribute("formTitle", "Add a Character");
        model.addAttribute("formAction", "/characters");

        return "character-form";
    }

    @PostMapping("/characters")
    public String createCharacter(
            @Valid @ModelAttribute("character") DcCharacter character,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("formTitle", "Add a Character");
            model.addAttribute("formAction", "/characters");
            model.addAttribute("validationError",
                    "Please enter a valid name and description.");
            return "character-form";
        }

        DcCharacter created = service.createCharacter(character);

        redirectAttributes.addFlashAttribute(
                "message", created.getName() + " was added.");

        return "redirect:/";
    }
}
