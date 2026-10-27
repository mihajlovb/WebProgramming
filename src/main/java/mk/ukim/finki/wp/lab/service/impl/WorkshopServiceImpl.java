package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Workshop;
import mk.ukim.finki.wp.lab.repository.WorkshopRepository;
import mk.ukim.finki.wp.lab.service.WorkshopService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkshopServiceImpl implements WorkshopService {

    private final WorkshopRepository workshopRepository;

    public WorkshopServiceImpl(WorkshopRepository workshopRepository) {
        this.workshopRepository = workshopRepository;
    }

    @Override
    public List<Workshop> listAll() {
        return workshopRepository.findAll();
    }

    @Override
    public List<Workshop> search(String text, Double maxPrice) {
        return workshopRepository.search(text, maxPrice);
    }
}
