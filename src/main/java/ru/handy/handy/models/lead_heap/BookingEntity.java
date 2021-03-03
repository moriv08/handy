package ru.handy.handy.models.lead_heap;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tb_bookings")
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;
    private String price;
    private String square;
    private String rooms;
    private String handing;

    private LocalDate bookingStart;
    private LocalDate bookingEnd;

    @OneToOne
    @JoinColumn(name = "lead_id")
    private LeadEntity lead;

    public BookingEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSquare() {
        return square;
    }

    public void setSquare(String square) {
        this.square = square;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public String getHanding() {
        return handing;
    }

    public void setHanding(String handing) {
        this.handing = handing;
    }

    public LocalDate getBookingStart() {
        return bookingStart;
    }

    public void setBookingStart(LocalDate bookingStart) {
        this.bookingStart = bookingStart;
    }

    public LocalDate getBookingEnd() {
        return bookingEnd;
    }

    public void setBookingEnd(LocalDate bookingEnd) {
        this.bookingEnd = bookingEnd;
    }

    public LeadEntity getLead() {
        return lead;
    }

    public void setLead(LeadEntity lead) {
        this.lead = lead;
    }


}
