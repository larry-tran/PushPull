/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.handi;

import java.io.Serializable;
import java.util.List;
import longtr.hotel.HotelDTO;
import longtr.image.ImageDTO;

/**
 *
 * @author Admin
 */
public class HandIDTO implements Serializable{
    private HotelDTO hotelDto;
    private List<ImageDTO> listImage;

    public HandIDTO() {
    }

    public HandIDTO(HotelDTO hotelDto, List<ImageDTO> listImage) {
        this.hotelDto = hotelDto;
        this.listImage = listImage;
    }

    public HotelDTO getHotelDto() {
        return hotelDto;
    }

    public void setHotelDto(HotelDTO hotelDto) {
        this.hotelDto = hotelDto;
    }

    public List<ImageDTO> getListImage() {
        return listImage;
    }

    public void setListImage(List<ImageDTO> listImage) {
        this.listImage = listImage;
    }
    
    
}
