package JobBoard.controller;

import JobBoard.model.*;
import JobBoard.service.CandidateService;
import JobBoard.service.JobOfferService;
import JobBoard.service.TechnologyProfileService;
import JobBoard.service.TechnologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class JobOfferController {

    @Autowired
    JobOfferService jobOfferService;

    @Autowired
    CandidateService candidateService;

    @Autowired
    TechnologyService technologyService;

    @Autowired
    TechnologyProfileService technologyProfileService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/jobOffers/new")
    public String addJobOffer(Model model) {
        model.addAttribute("jobOffer", new JobOffer());
        model.addAttribute("technologyProfiles", technologyProfileService.getAllTechnologyProfiles());
        return "jobOffers/new";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/jobOffers/new")
    public String saveJobOffer(@Valid JobOffer jobOffer, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("fixedTechnologies", technologyService.getAllTechnologies());
            model.addAttribute("technologyProfiles", technologyProfileService.getAllTechnologyProfiles());
            return "jobOffers/new";
        }
        for(JobRequirement jobRequirement : jobOffer.getRequirements()){
            jobRequirement.setJobOffer(jobOffer);
        }
        for(JobTechnology jobTechnology : jobOffer.getTechnologies()){
            jobTechnology.setJobOffer(jobOffer);
        }

        JobOffer savedJobOffer = jobOfferService.saveJobOffer(jobOffer);
        return "redirect:/jobOffers/"+savedJobOffer.getId();
    }

    @GetMapping("/jobOffers/{id}")
    public String viewJobOffer(@PathVariable("id") int id, Model model, Authentication authentication){
        model.addAttribute("jobOffer", jobOfferService.getJobOffer(id));
        ArrayList<Integer> appliedJobOffersIds = new ArrayList<>();

        int userId = ((CustomUser)authentication.getPrincipal()).getId();
        for(JobOffer jobOffer : candidateService.getCandidateByUserId(userId).getAppliedJobs()){
            appliedJobOffersIds.add(jobOffer.getId());
        }
        model.addAttribute("appliedJobOffers", appliedJobOffersIds);
        return "jobOffers/view";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addJobRequirement/{operation}")
    public String addJobRequirement(Model model, @ModelAttribute("jobOffer") JobOffer jobOffer,
                                    @PathVariable("operation") String operation) {
        model.addAttribute("fixedTechnologies", technologyService.getAllTechnologies());
        model.addAttribute("technologyProfiles", technologyProfileService.getAllTechnologyProfiles());
        jobOfferService.addJobRequirement(jobOffer);
        return "jobOffers/" + operation + " :: requirements";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/removeJobRequirement/{operation}")
    public String removeJobRequirement(JobOffer jobOffer,  Model model, @PathVariable("operation") String operation,
                                       @RequestParam("removeDynamicRow") Integer requirementIndex) {
        jobOfferService.removeJobRequirement(jobOffer, requirementIndex);
        model.addAttribute("fixedTechnologies", technologyService.getAllTechnologies());
        model.addAttribute("technologyProfiles", technologyProfileService.getAllTechnologyProfiles());
        return "jobOffers/" + operation + " :: requirements";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addJobTechnology/{operation}")
    public String addJobTechnology(JobOffer jobOffer, Model model, @PathVariable("operation") String operation) {
        jobOfferService.addJobTechnology(jobOffer);
        model.addAttribute("fixedTechnologies", technologyService.getAllTechnologies());
        model.addAttribute("technologyProfiles", technologyProfileService.getAllTechnologyProfiles());
        return "jobOffers/" + operation +" :: technologies";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/removeJobTechnology/{operation}")
    public String removeJobTechnology(JobOffer jobOffer, Model model, @PathVariable("operation") String operation,
                                      @RequestParam("removeDynamicRow") Integer requirementIndex) {
        jobOfferService.removeJobTechnology(jobOffer, requirementIndex);
        model.addAttribute("fixedTechnologies", technologyService.getAllTechnologies());
        model.addAttribute("technologyProfiles", technologyProfileService.getAllTechnologyProfiles());
        return "jobOffers/" + operation + " :: technologies";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/jobOffers/{id}/edit")
    public String editJobOffer(@PathVariable("id") int id, Model model){
        model.addAttribute("jobOffer", jobOfferService.getJobOffer(id));
        model.addAttribute("fixedTechnologies", technologyService.getAllTechnologies());
        model.addAttribute("technologyProfiles", technologyProfileService.getAllTechnologyProfiles());
        return "jobOffers/edit";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/jobOffers/{id}/edit")
    public String saveEditedJobOffer(@Valid JobOffer jobOffer, BindingResult result, Model model){
        if (result.hasErrors()) {
            return "jobOffers/edit";
        }

        JobOffer savedJobOffer = jobOfferService.saveJobOffer(jobOffer);
        model.addAttribute("jobOffer", savedJobOffer);
        model.addAttribute("fixedTechnologies", technologyService.getAllTechnologies());
        model.addAttribute("technologyProfiles", technologyProfileService.getAllTechnologyProfiles());

        return "redirect:/jobOffers/"+savedJobOffer.getId();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/jobOffers/{id}/delete")
    public String deleteJobOffer(@PathVariable("id") int id){
        jobOfferService.deleteJobOffer(id);
        return "redirect:/jobOffers/all";
    }

    @GetMapping("/jobOffers/all")
    public String showAllJobOffers(Model model){
        List<JobOffer> jobOffers = jobOfferService.findAll();
        model.addAttribute("jobOffers", jobOffers);
        return "jobOffers/viewAll";
    }
}
