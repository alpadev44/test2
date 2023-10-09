package com.example.Backend.Controller;

import com.example.Backend.Exception.NotFoundException;
import com.example.Backend.Model.DataTransferObject.BookingDTO;
import com.example.Backend.Service.Implementation.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/bookings")
@CrossOrigin(origins = "*")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping("/addBooking")
    public ResponseEntity<?> addBooking(@RequestBody BookingDTO booking) {
        bookingService.addBooking(booking);
        URI uri = URI.create("/bookings/" + booking.getId());
        return ResponseEntity.created(uri).body(booking);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/allBookings")
    public List<BookingDTO> allBookings() { return bookingService.allBookings(); }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @DeleteMapping("/deleteBooking/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Long id) throws NotFoundException {
        bookingService.deleteBooking(id);
        return ResponseEntity.ok("ID " + id + " was deleted");
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PutMapping("/updateBooking")
    public ResponseEntity<?> updateBooking(@RequestBody BookingDTO bookingDTO) {
        bookingService.updateBooking(bookingDTO);
        return ResponseEntity.ok(bookingDTO);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/findBooking/{id}")
    public ResponseEntity<?> findBooking(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(bookingService.findBooking(id));
    }
}
