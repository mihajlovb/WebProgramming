package mk.ukim.finki.wp.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.stereotype.Component;
import mk.ukim.finki.wp.lab.model.Workshop;
import mk.ukim.finki.wp.lab.model.Registration;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class DataHolder {

    public static List<Workshop> workshops = null;
    public static List<Registration> registrations = null;

    @PostConstruct
    public void init() {
        workshops = new ArrayList<>();

        workshops.add(new Workshop(
                "Java Basics", "Programming", 1500, 20));
        workshops.add(new Workshop(
                "Spring Boot", "Programming", 2500, 15));
        workshops.add(new Workshop(
                "Web Development", "Web", 2000, 10));
        workshops.add(new Workshop(
                "Database Design", "Databases", 1800, 12));
        workshops.add(new Workshop(
                "Python Fundamentals", "Programming", 1600, 0));
        workshops.add(new Workshop(
                "UI/UX Design", "Design", 2200, 8));
        workshops.add(new Workshop(
                "Cybersecurity", "Security", 3000, 5));
        workshops.add(new Workshop(
                "Data Science", "Data", 2800, 7));
        workshops.add(new Workshop(
                "Mobile Development", "Mobile", 2400, 9));
        workshops.add(new Workshop(
                "Cloud Computing", "Cloud", 3200, 6));

        registrations = new ArrayList<>();
    }
}