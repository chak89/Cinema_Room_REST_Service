package cinema;

import java.util.*;

public class TheatreModel {
    int total_rows;
    int total_columns;
    List<Seats> available_seats;

    public TheatreModel() {
        this.total_rows = 9;
        this.total_columns = 9;
        available_seats = new ArrayList<>();

        for (int i = 0; i < this.total_rows; i++) {
            for (int j = 0; j < this.total_columns; j++) {
                available_seats.add(new Seats(i,j));
            }
        }
    }

    public int getTotal_rows() {
        return total_rows;
    }

    public void setTotal_rows(int total_rows) {
        this.total_rows = total_rows;
    }

    public int getTotal_columns() {
        return total_columns;
    }

    public void setTotal_columns(int total_columns) {
        this.total_columns = total_columns;
    }

    public List<Seats> getAvailable_seats() {
        return available_seats;
    }

    public void setAvailable_seats(List<Seats> available_seats) {
        this.available_seats = available_seats;
    }
}

class MapOfTickets {
    Map<UUID, Tickets> mapOfTickets;

    public MapOfTickets() {
        this.mapOfTickets = new HashMap<>();
    }

    public Map<UUID, Tickets> getMapOfTickets() {
        return mapOfTickets;
    }

    public void setMapOfTickets(Map<UUID, Tickets> mapOfTickets) {
        this.mapOfTickets = mapOfTickets;
    }
}

class Tickets {
    UUID token;
    Seats ticket;

    public Tickets(UUID token, Seats ticket) {
        this.token = token;
        this.ticket = ticket;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public Seats getTicket() {
        return ticket;
    }

    public void setTicket(Seats ticket) {
        this.ticket = ticket;
    }
}

class ReturnedTickets {
    Seats returned_ticket;

    public ReturnedTickets(Seats returned_ticket) {
        this.returned_ticket = returned_ticket;
    }

    public Seats getReturned_ticket() {
        return returned_ticket;
    }

    public void setReturned_ticket(Seats returned_ticket) {
        this.returned_ticket = returned_ticket;
    }
}


class Seats {
    int row;
    int column;
    int price;

    Seats(int row, int column){
        this.row = row + 1;
        this.column = column + 1;

        if (this.row <= 4) {
            this.price = 10;
        } else {
            this.price = 8;
        }
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

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

class RequestTokens {
    UUID token;

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
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
