package frontend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import frontend.api.GarageApi;
import frontend.model.Comment;
import frontend.model.Garage;
import frontend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class GarageController extends AbstractController {

    @Autowired
    private GarageApi garageApi;

    @GetMapping("/garage")
    public String list(Model model,
                       @RequestParam(required = false) String name,
                       @RequestParam(required = false) String address) {

        Map<String, String> filters = new HashMap<>();
        if (name != null && !name.isEmpty())
            filters.put("name", name);
        if (address != null && !address.isEmpty())
            filters.put("address", address);

        model.addAttribute("garages", garageApi.get(filters));
        return "garage/index";
    }

    @GetMapping("/garage/{garageId}")
    public String get(@ModelAttribute Comment comment, Model model, @PathVariable Integer garageId) {
        model.addAttribute("garage", garageApi.find(garageId));
        model.addAttribute("comment", comment);
        return "garage/show";
    }

    @GetMapping("/garage/new")
    public String create(Model model) {
        if (!isGranted("ROLE_PARTNER")) {
            return "redirect:/login";
        }
        Garage garage = new Garage();
        model.addAttribute("garage", garage);
        return "garage/new";
    }

    @GetMapping("/garage/edit/{garageId}")
    public String edit(Model model, @PathVariable Integer garageId) {
        if (!isGranted("ROLE_PARTNER")) {
            return "redirect:/login";
        }
        model.addAttribute("garage", garageApi.find(garageId));
        return "garage/edit";
    }

    @PostMapping("/garage/add")
    public String post(@ModelAttribute Garage garage) throws JsonProcessingException {
        if (!isGranted("ROLE_PARTNER")) {
            return "redirect:/login";
        }
        User user = getUser();
        garage.setId_partner(user.getId());
        garage.setPartner(user.getFirst_name() + " " + user.getName());
        garageApi.create(garage);
        return "redirect:/garage/manage";
    }

    @PostMapping("/garage/edit/{garageId}")
    public String put(@ModelAttribute Garage garage, @PathVariable Integer garageId) throws JsonProcessingException {
        if (!isGranted("ROLE_PARTNER")) {
            return "redirect:/login";
        }
        Garage actualGarage = garageApi.find(garageId);
        User user = getUser();
        garage.setId_partner(user.getId());
        garage.setId(garageId);
        garage.setPartner(user.getFirst_name() + " " + user.getName());
        garage.setComments(actualGarage.getComments());
        garage.setCoordinates(actualGarage.getCoordinates());
        garage.getAddress().setId(actualGarage.getAddress().getId());
        garageApi.edit(garage);
        return "redirect:/garage/manage/";
    }

    @GetMapping("/garage/manage")
    public String manage(Model model) {
        if (!isGranted("ROLE_PARTNER")) {
            return "redirect:/login";
        }
        User user = getUser();
        Map<String, String> filters = new HashMap<>();
        filters.put("partner", String.valueOf(user.getId()));
        Garage[] garages = garageApi.get(filters);
        model.addAttribute("garages", garages);
        return "garage/manage";
    }

    @PostMapping("/garage/comment/{garageId}")
    public String comment(@ModelAttribute Comment comment, @PathVariable Integer garageId) throws JsonProcessingException {
        if (!isGranted("ROLE_MEMBER")) {
            return "redirect:/login";
        }
        comment.setClient_id(getUser().getId());
        Garage garage = garageApi.find(garageId);
        garage.getComments().add(comment);
        try {
            garageApi.edit(garage);
        } catch (Exception e) {
            return "redirect:/garage/" + garageId + "?comment";
        }
        return "redirect:/garage/" + garageId;
    }

    @GetMapping("/garage/delete/{garageId}")
    public String delete(@PathVariable Integer garageId) {
        if (!isGranted("ROLE_PARTNER")) {
            return "redirect:/login";
        }

        if (garageApi.find(garageId).getId_partner() != getUser().getId()) {
            return "redirect:/garage/manage";
        }

        garageApi.delete(garageId);
        return "redirect:/garage/manage";
    }
}
