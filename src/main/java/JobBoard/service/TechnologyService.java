package JobBoard.service;

import JobBoard.Repository.TechnologyRepository;
import JobBoard.model.Technology;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnologyService {

    @Autowired
    TechnologyRepository technologyRepository;

    public List<Technology> getAllTechnologies(){
        return technologyRepository.findAll();
    }
}
