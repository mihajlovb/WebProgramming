package mk.ukim.finki.wp.lab.repository.impl;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Workshop;
import mk.ukim.finki.wp.lab.repository.WorkshopRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Repository
public class InMemoryWorkshopRepository implements WorkshopRepository {

    @Override
    public List<Workshop> findAll() {
        return DataHolder.workshops;
    }

    @Override
    public Optional<Workshop> findByTitle(String title) {
        if (title == null) {
            return Optional.empty();
        }
        return DataHolder.workshops.stream()
                .filter(workshop -> workshop.getTitle().equals(title))
                .findFirst();
    }

    @Override
    public List<Workshop> search(String text, Double maxPrice) {
        String normalizedText = text == null ? null : text.trim().toLowerCase(Locale.ROOT);

        return DataHolder.workshops.stream()
                .filter(workshop -> normalizedText == null || normalizedText.isEmpty()
                        || workshop.getTitle().toLowerCase(Locale.ROOT).contains(normalizedText)
                        || workshop.getCategory().toLowerCase(Locale.ROOT).contains(normalizedText))
                .filter(workshop -> maxPrice == null || workshop.getPrice() <= maxPrice)
                .toList();
    }
}
