package JobBoard.service;


import JobBoard.Repository.CandidateProfileRepository;
import JobBoard.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CandidateService {

    @Autowired
    CandidateProfileRepository candidateProfileRepository;

    @Autowired
    JobOfferService jobOfferService;

    @Autowired
    CustomUserService customUserService;

    public void SaveCandidate(CustomUser customUser){
        candidateProfileRepository.save(new CandidateProfile(customUser));
    }

    public CandidateProfile getCandidate(int id){
        return candidateProfileRepository.getById(id);
    }

    public CandidateProfile getCandidateByUserId(int id){
        return candidateProfileRepository.findByUserId(id);
    }

    public CandidateProfile updateUserProfile(CandidateProfile candidateProfile){
        return candidateProfileRepository.save(candidateProfile);
    }

    public void addCandidateTechnology(CandidateProfile candidateProfile) {
        CandidateTechnology candidateTechnology = new CandidateTechnology();
        candidateTechnology.setCandidateProfile(candidateProfile);
        candidateProfile.getTechnologies().add(candidateTechnology);
    }

    public void removeCandidateTechnology(CandidateProfile candidateProfile, Integer requirementIndex) {
        candidateProfile.getTechnologies().remove(requirementIndex.intValue());
    }

    public void addAppliedJob(int jobOfferId, int candidateId){
        CandidateProfile candidateProfile = getCandidate(candidateId);
        JobOffer jobOffer = jobOfferService.getJobOffer(jobOfferId);
        candidateProfile.getAppliedJobs().add(jobOffer);
        candidateProfileRepository.save(candidateProfile);
    }
}
