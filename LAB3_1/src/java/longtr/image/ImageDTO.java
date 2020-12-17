/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.image;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class ImageDTO implements Serializable{
    private int imageID;
    private String imageData;
    private String emailOfOwner;
    private String address;
    private String type;
    private String status;

    public ImageDTO() {
    }

    public ImageDTO(int imageID, String imageData, String emailOfOwner, String address, String type, String status) {
        this.imageID = imageID;
        this.imageData = imageData;
        this.emailOfOwner = emailOfOwner;
        this.address = address;
        this.type = type;
        this.status = status;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getImage() {
        return imageData;
    }

    public void setImage(String image) {
        this.imageData = image;
    }

    public String getEmailOfOwner() {
        return emailOfOwner;
    }

    public void setEmailOfOwner(String emailOfOwner) {
        this.emailOfOwner = emailOfOwner;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    
}
