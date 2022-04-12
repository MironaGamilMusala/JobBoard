package JobBoard.service;

import JobBoard.Repository.JobOfferRepository;
import JobBoard.model.JobOffer;
import JobBoard.model.JobRequirement;
import JobBoard.model.JobTechnology;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobOfferService {


    @Autowired
    private JobOfferRepository jobOfferRepository;

    public JobOffer saveJobOffer(JobOffer jobOffer) {
        return jobOfferRepository.save(jobOffer);
    }

    public JobOffer getJobOffer(int id){
        return jobOfferRepository.getById(id);
    }

    public void addJobRequirement(JobOffer jobOffer) {
        JobRequirement jobRequirement = new JobRequirement();
        jobRequirement.setJobOffer(jobOffer);
        jobOffer.getRequirements().add(jobRequirement);
    }

    public void removeJobRequirement(JobOffer jobOffer, Integer requirementIndex) {
        jobOffer.getRequirements().remove(requirementIndex.intValue());
    }

    public void addJobTechnology(JobOffer jobOffer) {
        JobTechnology jobTechnology = new JobTechnology();
        jobTechnology.setJobOffer(jobOffer);
        jobOffer.getTechnologies().add(jobTechnology);
    }

    public void removeJobTechnology(JobOffer jobOffer, Integer requirementIndex) {
        jobOffer.getTechnologies().remove(requirementIndex.intValue());
    }

    public void deleteJobOffer(int id){
        jobOfferRepository.deleteById(id);
    }

    public List<JobOffer> findAll(){
        return jobOfferRepository.findAll();
    }
}
