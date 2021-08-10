package cinema;

import io.micrometer.core.instrument.util.JsonUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class SpringController {

    TheatreModel model = new TheatreModel();
    View view = new View();
    MapOfTickets mapOfTicket = new MapOfTickets();

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




