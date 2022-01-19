package kth.bookdesk.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity(name = "logger")
@Table(name = "logger")
public class Logger implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id",
            updatable = false
    )
    private int id;
    private int bookerid;
    private Timestamp date;

    protected Logger(){}

    public Logger(int bookerid, Timestamp date) {
        this.bookerid = bookerid;
        this.date = date;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookerid() {
        return bookerid;
    }

    public void setBookerid(int bookerid) {
        this.bookerid = bookerid;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Logger{" +
                "id=" + id +
                ", bookerid=" + bookerid +
                ", date=" + date +
                '}';
    }
}

