package JobBoard.controller;

import JobBoard.model.*;
import JobBoard.service.JobOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class JobOfferController {

    @Autowired
    JobOfferService jobOfferService;


    @GetMapping("/jobOffers/new")
    public String addJobOffer(Model model) {
        model.addAttribute("jobOffer", new JobOffer());
        return "addJobOffer";
    }

    @PostMapping("/addJobOffer")
    public String saveJobOffer(@Valid JobOffer jobOffer, Model model) {

        JobOffer savedJobOffer = jobOfferService.saveJobOffer(jobOffer);
        model.addAttribute("jobOffer", savedJobOffer);

        return "redirect:/jobOffers/"+savedJobOffer.getId();
    }

    @GetMapping("/jobOffers/{id}")
    public String viewJobOffer(@PathVariable("id") int id, Model model){
        model.addAttribute("jobOffer", jobOfferService.getJobOffer(id));
        return "viewJobOffer";
    }

    @PostMapping("/addJobRequirement")
    public String addJobRequirement(JobOffer jobOffer) {
         jobOfferService.addJobRequirement(jobOffer);
        return "addJobOffer :: requirements";
    }

    @PostMapping("/removeJobRequirement")
    public String removeJobRequirement(JobOffer jobOffer, @RequestParam("removeDynamicRow") Integer requirementIndex) {
        jobOfferService.removeJobRequirement(jobOffer, requirementIndex);
        return "addJobOffer :: requirements";
    }

    @PostMapping("/addJobTechnology")
    public String addJobTechnology(JobOffer jobOffer) {
        jobOfferService.addJobTechnology(jobOffer);
        return "addJobOffer :: technologies";
    }

    @PostMapping("/removeJobTechnology")
    public String removeJobTechnology(JobOffer jobOffer, @RequestParam("removeDynamicRow") Integer requirementIndex) {
        jobOfferService.removeJobTechnology(jobOffer, requirementIndex);
        return "addJobOffer :: technologies";
    }

    @GetMapping("/jobOffers/{id}/edit")
    public String editJobOffer(@PathVariable("id") int id, Model model){
        model.addAttribute("jobOffer", jobOfferService.getJobOffer(id));
        return "editJobOffer";
    }

    @PostMapping("/editJobOffer")
    public String saveEditedJobOffer(JobOffer jobOffer, Model model){
        JobOffer savedJobOffer = jobOfferService.saveJobOffer(jobOffer);
        model.addAttribute("jobOffer", savedJobOffer);

        return "redirect:/jobOffers/"+savedJobOffer.getId();
    }

    @GetMapping("/jobOffers/{id}/delete")
    public String deleteJobOffer(@PathVariable("id") int id){
        jobOfferService.deleteJobOffer(id);
        return "redirect:/jobOffers/all";
    }

    @GetMapping("/jobOffers/all")
    public String showAllJobOffers(Model model){
        List<JobOffer> jobOffers = jobOfferService.findAll();
        model.addAttribute("jobOffers", jobOffers);
        return "viewAllJobOffers";
    }
}
