package entities;

import dtos.DinnerEventDTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Table(name = "dinner_event")
@Entity
public class DinnerEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;
    private Date date;
    private String location;
    private String dish;
    private int pricePrPerson;


    public DinnerEvent() {
    }

    public DinnerEvent(DinnerEventDTO dinnerEventDTO){
        this.location = dinnerEventDTO.getLocation();
        this.dish = dinnerEventDTO.getDish();
        this.pricePrPerson = dinnerEventDTO.getPricePrPerson();
        this.date = new Date();
    }

    public DinnerEvent(String location, String dish, int pricePrPerson) {
        this.location = location;
        this.dish = dish;
        this.pricePrPerson = pricePrPerson;
        this.date = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public int getPricePrPerson() {
        return pricePrPerson;
    }

    public void setPricePrPerson(int pricePrPerson) {
        this.pricePrPerson = pricePrPerson;
    }
}