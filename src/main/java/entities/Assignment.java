package entities;

import dtos.AssignmentDTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "assignment")
@Entity
public class Assignment {
    @Id
    @NotNull
    private String familyName;

    private Date date;
    private String contactInfo;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<User> userList;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private DinnerEvent dinnerEvent;


    public Assignment(String familyName, String contactInfo) {
        this.familyName = familyName;
        this.contactInfo = contactInfo;
        this.userList = new ArrayList<>();
        this.date = new Date();
    }

    public Assignment(AssignmentDTO assignmentDTO){
        this.familyName = assignmentDTO.getFamilyName();
        this.date = assignmentDTO.getDate();
        this.contactInfo = assignmentDTO.getContactInfo();
    }


    public Assignment() {
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContactInfo() {
        return contactInfo;
    }


    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public void addUser(User user){
        this.userList.add(user);
    }

    public DinnerEvent getDinnerEvent() {
        return dinnerEvent;
    }

    public void setDinnerEvent(DinnerEvent dinnerEvent) {
        this.dinnerEvent = dinnerEvent;
    }
}