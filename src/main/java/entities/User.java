package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import dtos.UserDTO;
import org.mindrot.jbcrypt.BCrypt;

@Entity
@Table(name = "users")
public class User implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "user_name", length = 25)
  private String username;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 255)
  @Column(name = "user_pass")
  private String password;
  @JoinTable(name = "user_roles", joinColumns = {
    @JoinColumn(name = "user_name", referencedColumnName = "user_name")}, inverseJoinColumns = {
    @JoinColumn(name = "role_name", referencedColumnName = "role_name")})
  @ManyToMany
  private List<Role> roleList = new ArrayList<>();
  private String address;
  private String phone;
  private String email;
  private String birthYear;
  private int account;



  public List<String> getRolesAsStrings() {
    if (roleList.isEmpty()) {
      return null;
    }
    List<String> rolesAsStrings = new ArrayList<>();
    roleList.forEach((role) -> {
        rolesAsStrings.add(role.getRoleName());
      });
    return rolesAsStrings;
  }

  public User() {}

  public User(UserDTO userDTO) {
    this.username = userDTO.getUsername();
    this.password = BCrypt.hashpw(userDTO.getPassword(),BCrypt.gensalt());
    this.address = userDTO.getAddress();
    this.phone = userDTO.getPhone();
    this.email = userDTO.getEmail();
    this.birthYear = userDTO.getBirthYear();
    this.account = userDTO.getAccount();
  }

  //TODO Change when password is hashed
   public boolean verifyPassword(String pw){
        return BCrypt.checkpw(pw,this.password);
    }

  public User(String username, String password, String address, String phone, String email, String birthYear, int account) {
    this.username = username;
    this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    this.address = address;
    this.phone = phone;
    this.email = email;
    this.birthYear = birthYear;
    this.account = account;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String userName) {
    this.username = userName;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String userPass) {
    this.password = userPass;
  }

  public List<Role> getRoleList() {
    return roleList;
  }

  public void setRoleList(List<Role> roleList) {
    this.roleList = roleList;
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

  public void addRole(Role userRole) {
    roleList.add(userRole);
  }

}
