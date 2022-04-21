package JobBoard.controller;

import JobBoard.model.*;
import JobBoard.service.CandidateService;
import JobBoard.service.JobOfferService;
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


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/jobOffers/new")
    public String addJobOffer(Model model) {
        model.addAttribute("jobOffer", new JobOffer());
        return "jobOffers/new";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/jobOffers/new")
    public String saveJobOffer(@Valid JobOffer jobOffer, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "jobOffers/new";
        }

        JobOffer savedJobOffer = jobOfferService.saveJobOffer(jobOffer);
        model.addAttribute("jobOffer", savedJobOffer);

        return "redirect:/jobOffers/"+savedJobOffer.getId();
    }

    @GetMapping("/jobOffers/{id}")
    public String viewJobOffer(@PathVariable("id") int id, Model model, Authentication authentication){
        model.addAttribute("jobOffer", jobOfferService.getJobOffer(id));
        ArrayList<Integer> appliedJobOffersIds = new ArrayList<>();
        for(JobOffer jobOffer : candidateService.getCandidateByUsername(authentication.getName()).getAppliedJobs()){
            appliedJobOffersIds.add(jobOffer.getId());
        }
        model.addAttribute("appliedJobOffers", appliedJobOffersIds);
        return "jobOffers/view";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addJobRequirement/{operation}")
    public String addJobRequirement(@ModelAttribute("jobOffer") JobOffer jobOffer, @PathVariable("operation") String operation) {
         jobOfferService.addJobRequirement(jobOffer);
        return "jobOffers/" + operation + " :: requirements";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/removeJobRequirement/{operation}")
    public String removeJobRequirement(JobOffer jobOffer,  @PathVariable("operation") String operation, @RequestParam("removeDynamicRow") Integer requirementIndex) {
        jobOfferService.removeJobRequirement(jobOffer, requirementIndex);
        return "jobOffers/" + operation + " :: requirements";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addJobTechnology/{operation}")
    public String addJobTechnology(JobOffer jobOffer, @PathVariable("operation") String operation) {
        jobOfferService.addJobTechnology(jobOffer);
        return "jobOffers/" + operation +" :: technologies";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/removeJobTechnology/{operation}")
    public String removeJobTechnology(JobOffer jobOffer,  @PathVariable("operation") String operation, @RequestParam("removeDynamicRow") Integer requirementIndex) {
        jobOfferService.removeJobTechnology(jobOffer, requirementIndex);
        return "jobOffers/" + operation + " :: technologies";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/jobOffers/{id}/edit")
    public String editJobOffer(@PathVariable("id") int id, Model model){
        model.addAttribute("jobOffer", jobOfferService.getJobOffer(id));
        return "jobOffers/edit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/jobOffers/{id}/edit")
    public String saveEditedJobOffer(@Valid JobOffer jobOffer, BindingResult result, Model model){
        if (result.hasErrors()) {
            return "jobOffers/edit";
        }

        JobOffer savedJobOffer = jobOfferService.saveJobOffer(jobOffer);
        model.addAttribute("jobOffer", savedJobOffer);

        return "redirect:/jobOffers/"+savedJobOffer.getId();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
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
