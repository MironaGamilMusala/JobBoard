package JobBoard.service;


import JobBoard.Repository.CandidateProfileRepository;
import JobBoard.Repository.CandidateTechnologyRepository;
import JobBoard.model.CandidateProfile;
import JobBoard.model.CandidateTechnology;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class CandidateService {

    @Autowired
    JdbcUserDetailsManager jdbcUserDetailsManager;

    @Autowired
    CandidateProfileRepository candidateProfileRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void SaveCandidate(String username, String password){
        jdbcUserDetailsManager.createUser(
                User.withUsername(username).password(passwordEncoder.encode(password)).roles("USER").build());
        candidateProfileRepository.save(new CandidateProfile(username));
    }

    public CandidateProfile getCandidateByUsername(String username){;
        return candidateProfileRepository.findByUsername(username);
    }

    public void updateUserProfile(CandidateProfile candidateProfile){
        candidateProfileRepository.save(candidateProfile);
    }

    public void addCandidateTechnology(CandidateProfile candidateProfile) {
        CandidateTechnology candidateTechnology = new CandidateTechnology();
        candidateTechnology.setCandidateProfile(candidateProfile);
        candidateProfile.getTechnologies().add(candidateTechnology);
    }

    public void removeCandidateTechnology(CandidateProfile candidateProfile, Integer requirementIndex) {
        candidateProfile.getTechnologies().remove(requirementIndex.intValue());
    }

}
