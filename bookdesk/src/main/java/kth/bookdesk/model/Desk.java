package kth.bookdesk.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Entity(name = "desk") //default is class name
@Table(name = "desk")
public class Desk implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "pk",
            updatable = false
    )
    private int pk;
    @Column(name = "id")
    private int deskId;
    @Column(name = "starttime")
    private Timestamp startTime;
    @Column(name = "endtime")
    private Timestamp endTime;
    private int booked; //1 is booked, 0 is available

    public Desk(int id, Timestamp startTime, Timestamp endTime, int booked) {
        this.deskId = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.booked = booked;
    }

    public Desk(int i) {
        this.deskId = i;
        this.booked = 0;
    }
    protected Desk(){}

    public String getStart(){
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdfDate.format(startTime);
        SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm:ss a");
        String timeStr = sdfTime.format(startTime);
        return dateStr + " " + timeStr;
    }
    public String getEnd(){
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdfDate.format(endTime);
        SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm:ss a");
        String timeStr = sdfTime.format(endTime);
        return dateStr + " " + timeStr;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public int getBooked() {
        return booked;
    }

    public void setBooked(int booked) {
        this.booked = booked;
    }

    public int getDeskId() {
        return deskId;
    }

    public void setDeskId(int deskId) {
        this.deskId = deskId;
    }

    @Override
    public String toString() {
        return "Desk{" +
                "deskId=" + deskId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", booked=" + booked +
                '}';
    }
}
