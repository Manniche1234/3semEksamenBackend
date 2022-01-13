package dtos;

import entities.DinnerEvent;
import entities.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DinnerEventDTO {

    private Long id;
    private Date date;
    private String location;
    private String dish;
    private int pricePrPerson;


    public static List<DinnerEventDTO> getDtos(List<DinnerEvent> dinnerEvents){
        List<DinnerEventDTO> dinnerDTOSdtos = new ArrayList();
        dinnerEvents.forEach(din -> dinnerDTOSdtos.add(new DinnerEventDTO(din)));
        return dinnerDTOSdtos;
    }

    public DinnerEventDTO(String location, String dish, int pricePrPerson) {
        this.location = location;
        this.dish = dish;
        this.pricePrPerson = pricePrPerson;
        this.date = new Date();
    }

    public DinnerEventDTO(DinnerEvent dinnerEvent) {
       this.id = dinnerEvent.getId();
       this.date = dinnerEvent.getDate();
       this.location = dinnerEvent.getLocation();
       this.dish = dinnerEvent.getDish();
       this.pricePrPerson = dinnerEvent.getPricePrPerson();
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
