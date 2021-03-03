package ru.handy.handy.controllers.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.handy.handy.models.PrincipalEntity;
import ru.handy.handy.service.AdministrationsService;
import ru.handy.handy.service.PrincipalUserDetailsService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/api/update/admin")
public class UpdatableAdminController {

    private final PrincipalUserDetailsService principalService;
    private final AdministrationsService administrationsService;

    public UpdatableAdminController(PrincipalUserDetailsService principalService, AdministrationsService administrationsService) {
        this.principalService = principalService;
        this.administrationsService = administrationsService;
    }

    @ModelAttribute
    public void addCommonObjectsToModel(Model model, Principal principal){
        PrincipalEntity principalEntity = principalService.findPrincipalEntityByUsername(principal.getName());

        model.addAttribute("person", principalEntity);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/new_lead")
    public String makeNewLead(Model model){

        List<String> experts = principalService.findAllExpertNames();
        model.addAttribute("experts", experts);

        return "/admin/new_lead";
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/announcement")
    public String makeAnnouncement(@RequestParam(value = "announcement") String announcement){

//        List<String> experts = principalService.findAllExpertNames();
//        model.addAttribute("experts", experts);

        administrationsService.setNewAnnouncement(1L, announcement);

        return "redirect:/api/home/admin/filters/start";
    }
}
