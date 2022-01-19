package kth.bookdesk.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "user") //default is class name
@Table(
        name = "user",
        uniqueConstraints = {
                @UniqueConstraint(name = "user_email_unique", columnNames = "email"),
                @UniqueConstraint(name = "user_name_unique", columnNames = "username")
        })
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "idbooker",
            updatable = false
    )
    private int idbooker; //Primary key

    @Column(nullable = false)
    private String firstname;
    @Column(nullable = false)
    private String lastname;
    @Column(nullable = false)
    private String id;
    @Column(nullable = false)
    private String mobile;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    public User( String firstName, String lastName, String id, String mobile, String email,
                 String userName, String password) {
        this.firstname = firstName;
        this.lastname = lastName;
        this.id = id;
        this.mobile = mobile;
        this.email = email;
        this.username = userName;
        this.password = password;
    }

    public User(){} //default constructor

    public int getIdBooker() {
        return idbooker;
    }
    public void setIdBooker(int idBooker) {
        this.idbooker = idBooker;
    }
    public String getFirstName() {
        return firstname;
    }
    public void setFirstName(String firstName) {
        this.firstname = firstName;
    }
    public String getLastName() {
        return lastname;
    }
    public void setLastName(String lastName) {
        this.lastname = lastName;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getUserName() {
        return username;
    }
    public void setUserName(String userName) {
        this.username = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format("User [firstname='%s', lastname='%s', id='%s', mobile='%s', email='%s', username='%s',password='%s']",
                 firstname, lastname,id,mobile,email,username,password);
    }


}
