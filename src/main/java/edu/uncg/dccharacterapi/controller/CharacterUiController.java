
package edu.uncg.dccharacterapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.uncg.dccharacterapi.entity.DcCharacter;
import edu.uncg.dccharacterapi.service.DcCharacterService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class CharacterUiController {

    private final DcCharacterService service;

    public CharacterUiController(DcCharacterService service) {
        this.service = service;
    }

    // ----- Gallery and Search -----
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
    public ModelAndView details(@PathVariable Long id) {

        DcCharacter character = service.getCharacterById(id);

        if (character == null) {
            return new ModelAndView("error", HttpStatus.NOT_FOUND);
        }

        ModelAndView modelAndView = new ModelAndView("details");
        modelAndView.addObject("character", character);

        return modelAndView;
    }

    // ----- Add Character -----
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

    // ----- Edit Character -----
    @GetMapping("/characters/{id}/edit")
    public String editForm(
            @PathVariable Long id,
            Model model,
            HttpServletResponse response) {

        DcCharacter character = service.getCharacterById(id);

        if (character == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "error";
        }

        model.addAttribute("character", character);
        model.addAttribute("formTitle", "Edit Character");
        model.addAttribute("formAction", "/characters/" + id);

        return "character-form";
    }

    @PostMapping("/characters/{id}")
    public String updateCharacter(
            @PathVariable Long id,
            @Valid @ModelAttribute("character") DcCharacter character,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes,
            HttpServletResponse response) {

        if (service.getCharacterById(id) == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "error";
        }

        if (result.hasErrors()) {
            character.setId(id);

            model.addAttribute("formTitle", "Edit Character");
            model.addAttribute("formAction", "/characters/" + id);
            model.addAttribute("validationError",
                    "Please enter a valid name and description.");

            return "character-form";
        }

        DcCharacter updated = service.updateCharacter(id, character);

        redirectAttributes.addFlashAttribute(
                "message", updated.getName() + " was updated.");

        return "redirect:/characters/" + id;
    }

    // ----- Delete Character -----
    @PostMapping("/characters/{id}/delete")
    public String deleteCharacter(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes,
            HttpServletResponse response) {

        DcCharacter character = service.getCharacterById(id);

        if (character == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "error";
        }

        String characterName = character.getName();

        service.deleteCharacter(id);

        redirectAttributes.addFlashAttribute(
                "message", characterName + " was removed.");

        return "redirect:/";
    }

    // ----- About Page -----
    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
