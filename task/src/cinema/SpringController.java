package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@RestController
public class SpringController {

    TheatreModel model = new TheatreModel();
    MapOfTickets mapOfTicket = new MapOfTickets();
    Stats stats = new Stats(0,81,0);
    private final String PASSWORD = "super_secret";

    @GetMapping("/seats")
    public TheatreModel getSeats() {
        return model;
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchase(@RequestBody RequestDataModel requestData) {
        int row = requestData.getRow();
        int column = requestData.getColumn();

        List<Seats> available_seats = model.getAvailable_seats();

        if(row > 9 || row < 1 || column > 9 || column < 1) {
            //throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The number of a row or a column is out of bounds!");
            //printData(row, column,"OutOfBound");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new responseMessage("The number of a row or a column is out of bounds!"));
        }

        Iterator<Seats> i = available_seats.iterator();
        while (i.hasNext()) {
            Seats seat = i.next();
            if (seat.getRow() == row && seat.getColumn() == column) {


                UUID uuid=UUID.randomUUID();
                Tickets ticket = new Tickets(uuid, seat);
                mapOfTicket.getMapOfTickets().put(uuid,ticket);
                i.remove();
                //printData(row, column, "Removed");
                return ResponseEntity.status(HttpStatus.OK).body(ticket);
            }
        }

        //throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The ticket has been already purchased!");
        //printData(row, column, "AlreadyPurchased");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new responseMessage("The ticket has been already purchased!"));
    }

    @PostMapping("/return")
    public ResponseEntity<?> refund(@RequestBody RequestTokens requestToken) {

        UUID token = requestToken.getToken();

        if (mapOfTicket.getMapOfTickets().containsKey(token)) {

            Seats seat = mapOfTicket.getMapOfTickets().get(token).getTicket();
            mapOfTicket.getMapOfTickets().remove(token);
            List<Seats> available_seats = model.getAvailable_seats();
            available_seats.add(seat);

            ReturnedTickets returnedTickets = new ReturnedTickets(seat);

            return ResponseEntity.status(HttpStatus.OK).body(returnedTickets);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new responseMessage("Wrong token!"));
    }


    @PostMapping("/stats")
    public ResponseEntity<?> getStats(@RequestParam String password) {
        if(password.equals(PASSWORD)) {

            List<Seats> available_seats = model.getAvailable_seats();
            int number_of_available_seats = available_seats.size();

            int number_of_purchased_tickets = mapOfTicket.getMapOfTickets().size();

            int current_income = 0;

            for (UUID uuid : mapOfTicket.getMapOfTickets().keySet()) {
                current_income = current_income + mapOfTicket.getMapOfTickets().get(uuid).ticket.getPrice();
            }

            stats.setCurrent_income(current_income);
            stats.setNumber_of_available_seats(number_of_available_seats);
            stats.setNumber_of_purchased_tickets(number_of_purchased_tickets);

            return ResponseEntity.status(HttpStatus.OK).body(stats);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new responseMessage("The password is wrong!"));
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<responseMessage> handleMissingParams(MissingServletRequestParameterException ex) {
        //String name = ex.getParameterName();
        //System.out.println(name + " parameter is missing");
        // Actual exception handling
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new responseMessage("The password is wrong!"));

    }

    public void printData(int row, int column, String ops) {
        System.out.println("row : " + row);
        System.out.println("column : " + column);

        switch (ops) {
            case "OutOfBound":
                System.out.println("The number of a row or a column is out of bounds!");
                break;
            case "Removed":
                System.out.println("REMOVED a seat");
                break;
            case "AlreadyPurchased":
                System.out.println("The ticket has been already purchased!");
                break;
        }
        System.out.println();
    }
}




