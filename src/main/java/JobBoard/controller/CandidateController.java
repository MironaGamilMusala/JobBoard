package JobBoard.controller;

import JobBoard.model.CandidateProfile;
import JobBoard.model.CandidateTechnology;
import JobBoard.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CandidateController {

    @Autowired
    CandidateService candidateService;

    @GetMapping("/login")
    public ModelAndView login(){
        return new ModelAndView("login");
    }

    @GetMapping("/signup")
    public ModelAndView signup(){
        return new ModelAndView("signup");
    }

    @PostMapping("/signup")
    public void signup(@ModelAttribute("username") String username, @ModelAttribute("password") String password){
        candidateService.SaveCandidate(username, password);
    }

    @GetMapping("/candidateProfiles/{username}")
    public ModelAndView viewProfile(@PathVariable("username") String username){
        Map<String, Object> model = new HashMap<>();
        CandidateProfile candidateProfile = candidateService.getCandidateByUsername(username);
        model.put("user", candidateProfile);
        return new ModelAndView("userProfile", model);
    }

    @GetMapping("/candidateProfiles/{username}/edit")
    public ModelAndView editProfile(@PathVariable("username") String username){
        Map<String, Object> model = new HashMap<>();
        CandidateProfile candidateProfile = candidateService.getCandidateByUsername(username);
        CandidateTechnology candidateTechnology = new CandidateTechnology();
        candidateTechnology.setCandidateProfile(candidateProfile);
        model.put("user", candidateProfile);
        model.put("technology", candidateTechnology);
        return new ModelAndView("editUserProfile", model);
    }

    @PostMapping("/candidateProfiles/save")
    public ModelAndView updateProfile(@ModelAttribute("user") CandidateProfile candidateProfile){
        candidateService.updateUserProfile(candidateProfile);
        return new ModelAndView("redirect:/candidateProfiles/"+ candidateProfile.getUsername());
    }

    @PostMapping("/addTechnology")
    public ModelAndView addTechnologyToUser(@ModelAttribute("technology") CandidateTechnology candidateTechnology){
        candidateService.addTechnologyToUser(candidateTechnology);
        return new ModelAndView("redirect:/candidateProfiles/"+ candidateTechnology.getCandidateProfile().getUsername()+"/edit");
    }


}
