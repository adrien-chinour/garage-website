package frontend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import frontend.api.GarageApi;
import frontend.model.Garage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GarageController {

    @Autowired
    private GarageApi garageApi;

    @GetMapping("/garage")
    public String list(Model model) {
        model.addAttribute("garages", garageApi.getAll());
        return "garage/index";
    }

    @GetMapping("/garage/{garageId}")
    public String get(Model model, @PathVariable Integer garageId) {
        model.addAttribute("garage", garageApi.get(garageId));
        return "garage/show";
    }

    @GetMapping("/garage/new")
    public String create(Model model) {
        Garage garage = new Garage();
        model.addAttribute("garage", garage);
        return "garage/new";
    }

    @GetMapping("/garage/edit/{garageId}")
    public String edit(Model model, @PathVariable Integer garageId) {
        model.addAttribute("garage", garageApi.get(garageId));
        return "garage/edit";
    }

    @PostMapping("/garage/add")
    public String post(@ModelAttribute Garage garage) throws JsonProcessingException {
        garageApi.create(garage);
        return "redirect:/garage";
    }

    @PostMapping("/garage/edit")
    public String put(@ModelAttribute Garage garage) throws JsonProcessingException {
        garageApi.edit(garage);
        return "redirect:/garage" + garage.getId();
    }
}
