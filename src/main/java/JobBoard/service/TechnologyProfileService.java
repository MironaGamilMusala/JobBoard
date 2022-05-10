package JobBoard.service;

import JobBoard.Repository.TechnologyProfileRepository;
import JobBoard.model.TechnologyProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnologyProfileService {

    @Autowired
    TechnologyProfileRepository technologyProfileRepository;

    public List<TechnologyProfile> getAllTechnologyProfiles(){
        return technologyProfileRepository.findAll();
    }
}
