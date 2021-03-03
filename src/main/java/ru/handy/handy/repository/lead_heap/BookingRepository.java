package ru.handy.handy.repository.lead_heap;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.handy.handy.models.lead_heap.BookingEntity;
import ru.handy.handy.models.lead_heap.LeadEntity;

import java.time.LocalDate;

@Repository
public interface BookingRepository extends CrudRepository<BookingEntity, Long> {

    BookingEntity findByLeadId(Long id);

    @Modifying
    @Query("update BookingEntity booking set booking.location = ?2 where booking.id = ?1")
    void setBookingLocation(Long id, String location);
    @Modifying
    @Query("update BookingEntity booking set booking.price = ?2 where booking.id = ?1")
    void setBookingPrice(Long id, String price);
    @Modifying
    @Query("update BookingEntity booking set booking.handing = ?2 where booking.id = ?1")
    void setBookingHanding(Long id, String handing);
    @Modifying
    @Query("update BookingEntity booking set booking.square = ?2 where booking.id = ?1")
    void setBookingSquare(Long id, String bookSquare);
    @Modifying
    @Query("update BookingEntity booking set booking.rooms = ?2 where booking.id = ?1")
    void setBookingRooms(Long id, String rooms);

    @Modifying
    @Query("update BookingEntity booking set booking.bookingStart = ?2 where booking.id = ?1")
    void setBookingDate(Long id, LocalDate setBookingDate);
    @Modifying
    @Query("update BookingEntity booking set booking.bookingEnd = ?2 where booking.id = ?1")
    void setBookingEndDate(Long id, LocalDate setBookingEndDate);
}
