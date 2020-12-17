/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.randi;

import java.io.Serializable;
import java.util.List;
import longtr.image.ImageDTO;
import longtr.room.RoomDTO;

/**
 *
 * @author Admin
 */
public class RandIDTO implements Serializable{
    private RoomDTO roomDto;
    private List<ImageDTO> listImage;
    private double sum;

    public RandIDTO() {
    }

    public RandIDTO(RoomDTO roomDto, List<ImageDTO> listImage, double sum) {
        this.roomDto = roomDto;
        this.listImage = listImage;
        this.sum = sum;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    
    public RoomDTO getRoomDto() {
        return roomDto;
    }

    public void setRoomDto(RoomDTO roomDto) {
        this.roomDto = roomDto;
    }

    public List<ImageDTO> getListImage() {
        return listImage;
    }

    public void setListImage(List<ImageDTO> listImage) {
        this.listImage = listImage;
    }
    
    
}
