package frontend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import frontend.api.GarageApi;
import frontend.model.Garage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class GarageController {

    @Autowired
    private GarageApi garageApi;

    @GetMapping("/garage")
    public String list(Model model,
                       @RequestParam(required = false) String name,
                       @RequestParam(required = false) String adresse) {

        Map<String,String> filters = new HashMap<>();
        filters.put("name", name);
        filters.put("adresse", adresse);

        model.addAttribute("garages", garageApi.get(filters));
        return "garage/index";
    }

    @GetMapping("/garage/{garageId}")
    public String get(Model model, @PathVariable Integer garageId) {
        model.addAttribute("garage", garageApi.find(garageId));
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
        model.addAttribute("garage", garageApi.find(garageId));
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
