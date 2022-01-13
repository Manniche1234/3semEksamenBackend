package dtos;

import entities.Assignment;
import entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {

    private String username;
    private String password;
    private String address;
    private String phone;
    private String email;
    private String birthYear;
    private int account;
    private List<AssignmentDTO> assignments;


    public static List<UserDTO> getDtos(List<User> u){
        List<UserDTO> userDTOSdtos = new ArrayList();
        u.forEach(um -> userDTOSdtos.add(new UserDTO(um)));
        return userDTOSdtos;
    }

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.address = user.getAddress();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.birthYear = user.getBirthYear();
        this.account = user.getAccount();
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public List<AssignmentDTO> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<AssignmentDTO> assignments) {
        this.assignments = assignments;
    }
}
