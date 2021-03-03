package ru.handy.handy.service.lead_heap;

import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.handy.handy.models.lead_heap.BookingEntity;
import ru.handy.handy.models.lead_heap.LeadEntity;
import ru.handy.handy.repository.lead_heap.BookingRepository;

import java.time.LocalDate;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Transactional
    @ReadOnlyProperty
    public BookingEntity findBookingByLeadId(Long id){
        return bookingRepository.findByLeadId(id);
    }

    @Transactional
    public void createNewBooking(BookingEntity booking){
        bookingRepository.save(booking);
    }

    @Transactional
    public void setBookingLocationByBookingId(Long id, String location){
        bookingRepository.setBookingLocation(id, location);
    }
    @Transactional
    public void setBookingPriceByBookingId(Long id, String price){
        bookingRepository.setBookingPrice(id, price);
    }
    @Transactional
    public void setBookingHandingByBookingId(Long id, String handing){
        bookingRepository.setBookingHanding(id, handing);
    }
    @Transactional
    public void setBookingSquareByBookingId(Long id, String bookSquare){
        bookingRepository.setBookingSquare(id, bookSquare);
    }
    @Transactional
    public void setBookingRoomsByBookingId(Long id, String rooms){
        bookingRepository.setBookingRooms(id, rooms);
    }


    @Transactional
    public void setBookingStartDateByBookingId(Long id, LocalDate date){
        bookingRepository.setBookingDate(id, date);
    }
    @Transactional
    public void setBookingEndDateByBookingId(Long id, LocalDate date){
        bookingRepository.setBookingEndDate(id, date);
    }

}
