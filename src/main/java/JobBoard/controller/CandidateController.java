package JobBoard.controller;

import JobBoard.model.CandidateProfile;
import JobBoard.model.CandidateTechnology;
import JobBoard.model.JobOffer;
import JobBoard.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class CandidateController {

    @Autowired
    CandidateService candidateService;

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
        candidateService.SaveCandidate(username, password);
        return "redirect:/";
    }

    @GetMapping("/candidateProfiles/{username}")
    public String viewProfile(@PathVariable("username") String username, Model model){

        CandidateProfile candidateProfile = candidateService.getCandidateByUsername(username);
        model.addAttribute("user", candidateProfile);
        return "users/view";
    }

    @PreAuthorize("#username == authentication.name")
    @GetMapping("/candidateProfiles/{username}/edit")
    public String editProfile(@PathVariable("username") String username, Model model){
        CandidateProfile candidateProfile = candidateService.getCandidateByUsername(username);
        CandidateTechnology candidateTechnology = new CandidateTechnology();
        candidateTechnology.setCandidateProfile(candidateProfile);
        model.addAttribute("user", candidateProfile);
        model.addAttribute("technology", candidateTechnology);
        return "users/edit";
    }

    @PostMapping("/candidateProfiles/{username}/edit")
    public String updateProfile(@Valid @ModelAttribute("user") CandidateProfile candidateProfile, BindingResult result){
        if (result.hasErrors()) {
            return "users/edit";
        }
        candidateService.updateUserProfile(candidateProfile);
        return "redirect:/candidateProfiles/"+ candidateProfile.getUsername();
    }

    @PostMapping("/addCandidateTechnology")
    public String addCandidateTechnology(@ModelAttribute("user") CandidateProfile candidateProfile) {
        candidateService.addCandidateTechnology(candidateProfile);
        return "users/edit :: technologies";
    }

    @PostMapping("/removeCandidateTechnology")
    public String removeCandidateTechnology(@ModelAttribute("user") CandidateProfile candidateProfile, @RequestParam("removeDynamicRow") Integer requirementIndex) {
        candidateService.removeCandidateTechnology(candidateProfile, requirementIndex);
        return "users/edit :: technologies";
    }

    @PostMapping("/applyForJob")
    public String applyForJob(@RequestParam(value = "jobOfferId") int jobOfferId, @RequestParam(value = "username") String username){
        candidateService.addAppliedJob(jobOfferId, username);
        return "redirect:/candidateProfiles/"+ username;
    }
}
