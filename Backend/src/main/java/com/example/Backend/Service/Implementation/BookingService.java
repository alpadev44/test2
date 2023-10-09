package com.example.Backend.Service.Implementation;

import com.example.Backend.Exception.NotFoundException;
import com.example.Backend.Model.DataTransferObject.*;
import com.example.Backend.Model.Entity.*;
import com.example.Backend.Repository.IBookingRepository;
import com.example.Backend.Repository.IImageRepository;
import com.example.Backend.Repository.IProductRepository;
import com.example.Backend.Repository.IUserRepository;
import com.example.Backend.Service.IBookingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@AllArgsConstructor
@Service
public class BookingService implements IBookingService {
    @Autowired
    private IProductRepository iProductRepository;
    @Autowired
    private IUserRepository iUserRepository;
    @Autowired
    private IBookingRepository iBookingRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    @Override
    public void addBooking(BookingDTO bookingDTO) {
        Booking booking = changeBookingDTOToBooking(bookingDTO);
        iBookingRepository.save(booking);
    }

    public void createBooking(User user, Booking booking) {

        Optional<Booking> searchBooking = iBookingRepository.findById(booking.getId());
        Optional<User> searchUser = iUserRepository.findById(booking.getUsers().getId());

        if(searchBooking.isPresent()  && searchUser.isPresent()) {
            Booking managedBooking = searchBooking.get();
            User managedUser = searchUser.get();

            managedUser.getBookings().add(managedBooking);
            iUserRepository.save(managedUser);
        }
    }

    /*
    public void generateBooking(Long userId, Long productId, BookingDTO bookingDTO) {
        Optional<User> user = iUserRepository.findById(userId);
        Optional<Product> product = iProductRepository.findById(productId);

        if (user.isPresent() && product.isPresent()) {
            Booking booking = changeBookingDTOToBooking(bookingDTO);

            booking.setUsers(user.get());
            booking.setProducts(product.get());

            iBookingRepository.save(booking);
        }
    }

     */

    @Override
    public List<BookingDTO> allBookings() {
        List<Booking> bookingList = iBookingRepository.findAll();
        List<BookingDTO> bookingDTOList = new ArrayList<>();

        for (Booking bookinglists :bookingList) {
            bookingDTOList.add(changeBookingToBookingDTO(bookinglists));
        }
        return bookingDTOList;
    }

    @Override
    public void updateBooking(BookingDTO bookingDTO) {
        Optional<Product> searchProduct = iProductRepository.findById(bookingDTO.getProducts_id());
        Optional<User> searchUser = iUserRepository.findById(bookingDTO.getUsers_id());
        if(searchProduct.isPresent() && searchUser.isPresent()) {
            changeBookingToBookingDTO(iBookingRepository.save(changeBookingDTOToBooking(bookingDTO)));
        }
    }

    @Override
    public void deleteBooking(Long id) throws NotFoundException {
        Optional<Booking> searchBooking  = iBookingRepository.findById(id);
        if(searchBooking.isPresent()) {
            iBookingRepository.deleteById(id);
        }
        else {
            throw  new NotFoundException("ID " + id + " Not Found");
        }
    }

    @Override
    public Optional<BookingDTO> findBooking(Long id) throws NotFoundException {
        Optional<Booking> searchBooking  = iBookingRepository.findById(id);
        if(searchBooking.isPresent()) {
            return Optional.of(changeBookingToBookingDTO(searchBooking.get()));
        }
        else {
            throw  new NotFoundException("ID " + id + " Not Found");
        }
    }

    @Override
    public Booking changeBookingDTOToBooking(BookingDTO bookingDTO) {
        Booking booking = new Booking();
        Product product = new Product();
        User user = new User();

        booking.setId(bookingDTO.getId());
        booking.setStart_date(bookingDTO.getStart_date());
        booking.setFinish_date(bookingDTO.getFinish_date());
        product.setId(bookingDTO.getProducts_id());
        user.setId(bookingDTO.getUsers_id());

        booking.setProducts(product);
        booking.setUsers(user);

        return booking;
    }

        @Override
        public BookingDTO changeBookingToBookingDTO(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();

        bookingDTO.setId(booking.getId());
        bookingDTO.setStart_date(booking.getStart_date());
        bookingDTO.setFinish_date(booking.getFinish_date());
        bookingDTO.setProducts_id(booking.getProducts().getId());
        bookingDTO.setUsers_id(booking.getUsers().getId());

        Set<ProductDTO> productDTOSet = new HashSet<>();
        if(booking.getProducts() != null) {
            for (Booking bookings : booking.getProducts().getBookings()) {
                productDTOSet.add(productService.changeProductToProductDTO(booking.getProducts()));
            }
        }
        bookingDTO.setProductDTO(productDTOSet);

        Set<UserDTO> userDTOSet = new HashSet<>();
        if(booking.getUsers().getBookings() != null) {
            for (Booking bookings : booking.getUsers().getBookings()) {
                userDTOSet.add(userService.changeUserToUserDTO(booking.getUsers()));
            }
        }
        bookingDTO.setUserDTO(userDTOSet);


        return bookingDTO;
    }
}
