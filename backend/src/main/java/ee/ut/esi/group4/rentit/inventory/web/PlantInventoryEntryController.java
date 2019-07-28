package ee.ut.esi.group4.rentit.inventory.web;

import ee.ut.esi.group4.rentit.inventory.domain.model.PlantInventoryEntry;
import ee.ut.esi.group4.rentit.inventory.domain.repository.PlantInventoryEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PlantInventoryEntryController {
    @Autowired
    PlantInventoryEntryRepository repo;

    @GetMapping("/plants")
    @Secured({"ROLE_CLIENT", "ROLE_CLERK"})
    public String list(Model model){
        model.addAttribute("plants", repo.findAll());
        return "plants/list";
    }

    @GetMapping(value="/plants/form")
    @Secured({"ROLE_CLIENT", "ROLE_CLERK"})
    public String form(Model model){
        model.addAttribute("plant", PlantInventoryEntry.of(0L, null, null, null));
        return "plants/create";
    }

    @PostMapping(value="/plants")
    @Secured({"ROLE_CLIENT", "ROLE_CLERK"})
    public String create(PlantInventoryEntry plant){
        repo.save(plant);
        return "redirect:/plants";
    }
}
