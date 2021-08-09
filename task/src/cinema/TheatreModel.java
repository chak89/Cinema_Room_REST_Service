package cinema;

import java.util.ArrayList;
import java.util.List;

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

