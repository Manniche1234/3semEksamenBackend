package dtos;

import entities.Assignment;
import entities.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AssignmentDTO {

    private String familyName;
    private Date date;
    private String contactInfo;

    public static List<AssignmentDTO> getDtos(List<Assignment> a){
        List<AssignmentDTO> asDTOSdtos = new ArrayList();
        a.forEach(am -> asDTOSdtos.add(new AssignmentDTO(am)));
        return asDTOSdtos;
    }
    public AssignmentDTO(Assignment assignment){
        this.familyName = assignment.getFamilyName();
        this.date = assignment.getDate();
        this.contactInfo = assignment.getContactInfo();
    }

    public AssignmentDTO(String familyName,String contactInfo) {
        this.familyName = familyName;
        this.contactInfo = contactInfo;
        this.date = new Date();
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
}
