package entities;

import dtos.DinnerEventDTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dinnerEvent")
    private List<Assignment> assignmentList;


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
        this.assignmentList = new ArrayList<>();
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

    public List<Assignment> getAssignmentList() {
        return assignmentList;
    }

    public void setAssignmentList(List<Assignment> assignmentList) {
        this.assignmentList = assignmentList;
    }

    public void addAssignment(Assignment a){
        if(a != null){
            a.setDinnerEvent(this);
            this.assignmentList.add(a);
        }
    }
}