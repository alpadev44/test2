package com.example.Backend.Service;

import com.example.Backend.Exception.NotFoundException;
import com.example.Backend.Model.DataTransferObject.BookingDTO;
import com.example.Backend.Model.DataTransferObject.CategoryDTO;
import com.example.Backend.Model.Entity.Booking;
import com.example.Backend.Model.Entity.Category;

import java.util.List;
import java.util.Optional;


public interface IBookingService {
    void addBooking(BookingDTO bookingDTO);
    List<BookingDTO> allBookings();
    void updateBooking(BookingDTO bookingDTO);
    void deleteBooking(Long id) throws NotFoundException;
    Optional<BookingDTO> findBooking(Long id) throws NotFoundException;
    Booking changeBookingDTOToBooking(BookingDTO bookingDTO);
    BookingDTO changeBookingToBookingDTO(Booking booking);
}
