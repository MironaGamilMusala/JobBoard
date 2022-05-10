package JobBoard.controller;

import JobBoard.model.*;
import JobBoard.service.CandidateService;
import JobBoard.service.CustomUserService;
import JobBoard.service.TechnologyProfileService;
import JobBoard.service.TechnologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CandidateController {

    @Autowired
    CandidateService candidateService;

    @Autowired
    CustomUserService customUserService;

    @Autowired
    TechnologyService technologyService;

    @Autowired
    TechnologyProfileService technologyProfileService;

    @GetMapping("/login")
    public String login(){
        return "users/login";
    }

    @GetMapping("/signup")
    public String signup(){
        return "users/signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("username") String username, @ModelAttribute("password") String password){
        CustomUser customUser = customUserService.saveCustomUser(username, password);
        candidateService.SaveCandidate(customUser);
        return "redirect:/";
    }

    @GetMapping("/candidateProfiles/{id}")
    public String viewProfile(@PathVariable("id") int id, Model model){
        CandidateProfile candidateProfile = candidateService.getCandidateByUserId(id);
        model.addAttribute("user", candidateProfile);
        return "users/view";
    }

    @PreAuthorize("#id == authentication.getPrincipal().getId()")
    @GetMapping("/candidateProfiles/{id}/edit")
    public String editProfile(@PathVariable("id") int id, Model model){
        CandidateProfile candidateProfile = candidateService.getCandidateByUserId(id);
        model.addAttribute("user", candidateProfile);
        model.addAttribute("fixedTechnologies", technologyService.getAllTechnologies());
        model.addAttribute("technologyProfiles", technologyProfileService.getAllTechnologyProfiles());
        return "users/edit";
    }

    @PostMapping("/candidateProfiles/{id}/edit")
    public String updateProfile(@Valid @ModelAttribute("user") CandidateProfile candidateProfile, BindingResult result, Model model){
        if (result.hasErrors()) {
            model.addAttribute("fixedTechnologies", technologyService.getAllTechnologies());
            model.addAttribute("technologyProfiles", technologyProfileService.getAllTechnologyProfiles());
            return "users/edit";
        }
        candidateService.updateUserProfile(candidateProfile);
        return "redirect:/candidateProfiles/"+ candidateProfile.getUser().getId();
    }

    @PostMapping("/addCandidateTechnology")
    public String addCandidateTechnology(Model model, @ModelAttribute("user") CandidateProfile candidateProfile) {
        candidateService.addCandidateTechnology(candidateProfile);
        model.addAttribute("fixedTechnologies", technologyService.getAllTechnologies());
        model.addAttribute("technologyProfiles", technologyProfileService.getAllTechnologyProfiles());
        return "users/edit :: technologies";
    }

    @PostMapping("/removeCandidateTechnology")
    public String removeCandidateTechnology(Model model, @ModelAttribute("user") CandidateProfile candidateProfile,
                                            @RequestParam("removeDynamicRow") Integer requirementIndex) {
        candidateService.removeCandidateTechnology(candidateProfile, requirementIndex);
        model.addAttribute("fixedTechnologies", technologyService.getAllTechnologies());
        model.addAttribute("technologyProfiles", technologyProfileService.getAllTechnologyProfiles());
        return "users/edit :: technologies";
    }

    @PostMapping("/applyForJob")
    public String applyForJob(@RequestParam(value = "jobOfferId") int jobOfferId,
                              @RequestParam(value = "candidateId") int candidateId){
        candidateService.addAppliedJob(jobOfferId, candidateId);
        int userId = candidateService.getCandidate(candidateId).getUser().getId();
        return "redirect:/candidateProfiles/"+ userId;
    }
}
