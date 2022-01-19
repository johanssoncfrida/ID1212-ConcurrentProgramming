package kth.bookdesk.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "booker") //default is class name
@Table(name = "booker")
public class Booker implements Serializable{
    @Id
    @Column(name="id", updatable = false)
    private int id;

    @Column(nullable = false)
    private String company;
    @Column(name="deskid", nullable = false)
    private int deskId;

    @OneToOne
    @PrimaryKeyJoinColumn(name="id", referencedColumnName = "idbooker")
    private User user;

    @OneToOne
    @PrimaryKeyJoinColumn(name = "deskid", referencedColumnName = "pk")
    private Desk desk;

    public Booker(int id, String company, int deskId, User user, Desk d) {
        this.id = id;
        this.company = company;
        this.deskId = deskId;
        this.user = user;
        this.desk = d;
    }

    protected Booker() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getDeskId() {
        return deskId;
    }

    public void setDeskId(int deskId) {
        this.deskId = deskId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return String.format("Booker [id='%d', company='%s', deskId='%d']",
                id,company,deskId);
    }

}
