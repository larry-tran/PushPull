/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.history;

import java.io.Serializable;
import java.util.List;
import longtr.bookedroom.BookedRoomDTO;
import longtr.booking.BookingDTO;

/**
 *
 * @author Admin
 */
public class HistoryBookingDTO implements Serializable{
    private List<BookedRoomDTO> listBookedRoom;
    private BookingDTO bookingDto;

    public HistoryBookingDTO() {
    }

    public HistoryBookingDTO(List<BookedRoomDTO> listBookedRoom, BookingDTO bookingDto) {
        this.listBookedRoom = listBookedRoom;
        this.bookingDto = bookingDto;
    }

    public List<BookedRoomDTO> getListBookedRoom() {
        return listBookedRoom;
    }

    public void setListBookedRoom(List<BookedRoomDTO> listBookedRoom) {
        this.listBookedRoom = listBookedRoom;
    }

    public BookingDTO getBookingDto() {
        return bookingDto;
    }

    public void setBookingDto(BookingDTO bookingDto) {
        this.bookingDto = bookingDto;
    }
    
}
