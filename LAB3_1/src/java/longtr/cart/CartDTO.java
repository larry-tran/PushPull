/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.cart;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class CartDTO {
    private Map<String, Integer> items;

    public Map<String, Integer> getRooms() {
        return items;
    }

    public int getNumberOfRoom() {
        int size = 0;
        if (this.items != null) {
            size = this.items.size();
        }
        return size;
    }

    public void addRoomToCart(String roomID, int numRoom) {
        if (this.items == null) {
            this.items = new HashMap<>();
        }
        int quantity = numRoom;
        if (this.items.containsKey(roomID)) {
            quantity = this.items.get(roomID) + numRoom;
        }
        this.items.put(roomID, quantity);
    }

    public void removeSelectedRoom(String roomID) {
        if (this.items == null) {
            return;
        }
        if (this.items.containsKey(roomID)) {
            this.items.remove(roomID);
            if (this.items.isEmpty()) {
                this.items = null;
            }
        }
    }
    
    public void updateCart(String roomID , int quantity){
        if(this.items.containsKey(roomID)){
            this.items.replace(roomID, quantity);
        }
    }
}
