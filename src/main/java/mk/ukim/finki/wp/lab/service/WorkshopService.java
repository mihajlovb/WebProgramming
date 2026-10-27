package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Workshop;

import java.util.List;

public interface WorkshopService {
    List<Workshop> listAll();
    List<Workshop> search(String text, Double maxPrice);
}
