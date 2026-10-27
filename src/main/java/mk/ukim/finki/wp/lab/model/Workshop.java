package mk.ukim.finki.wp.lab.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Workshop {
    String title;
    String category;
    double price;
    int capacity;
}
