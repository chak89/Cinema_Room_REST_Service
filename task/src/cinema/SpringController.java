package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Iterator;
import java.util.List;

@RestController
public class SpringController {

    TheatreModel model = new TheatreModel();
    View view = new View();

    @GetMapping("/seats")
    public TheatreModel getSeats() {
        return model;
    }

    @PostMapping("/purchase")
    public Object purchase(@RequestBody RequestDataModel requestData) {
        int row = requestData.getRow();
        int column = requestData.getColumn();

        List<Seats> available_seats = model.getAvailable_seats();

        if(row > 9 || row < 1 || column > 9 || column < 1) {
            //throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The number of a row or a column is out of bounds!");
            System.out.println("row : " + row);
            System.out.println("column : " + column);
            System.out.println("The number of a row or a column is out of bounds!");
            System.out.println();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new responseMessage("The number of a row or a column is out of bounds!"));
        }

        Iterator<Seats> i = available_seats.iterator();
        while (i.hasNext()) {
            Seats seat = i.next();
            if (seat.getRow() == row && seat.getColumn() == column) {
                System.out.println("row : " + row);
                System.out.println("column : " + column);
                System.out.println("removed : " + seat);
                System.out.println();
                i.remove();
                return ResponseEntity.status(HttpStatus.OK).body(seat);
            }
        }

        //throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The ticket has been already purchased!");
        System.out.println("row : " + row);
        System.out.println("column : " + column);
        System.out.println("The ticket has been already purchased!");
        System.out.println();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new responseMessage("The ticket has been already purchased!"));
    }
}


class RequestDataModel {
    int row;
    int column;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}

class responseMessage {
    String error;

    public responseMessage(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
