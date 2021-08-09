package cinema;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringController {

    TheatreModel model = new TheatreModel();
    View view = new View();


    @GetMapping("/seats")
    public TheatreModel getSeats() {
        return model;
    }
}
