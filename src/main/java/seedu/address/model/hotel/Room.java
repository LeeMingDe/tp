package seedu.address.model.hotel;

import java.util.Objects;
import seedu.address.model.person.Person;

/**
 * Represents Room in the app
 */
public class Room implements Comparable<Room> {
    private int roomNumber;
    private boolean isOccupied;
    private Person person;

    public Room(int roomNumber, boolean isOccupied) {
        this.roomNumber = roomNumber;
        this.isOccupied = isOccupied;
    }

    public Room(int roomNumber) {
        this.roomNumber = roomNumber;
        this.isOccupied = false;
    }
    public int getRoomNumber() {
        return roomNumber;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    /**
     * Returns true if both rooms have the same identity and data fields.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return roomNumber == room.roomNumber &&
                isOccupied == room.isOccupied;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber, isOccupied);
    }

    @Override
    public int compareTo(Room room) {
        if(room.isOccupied == this.isOccupied) {
            if (room.roomNumber < this.roomNumber) {
                return 1;
            }else{
                return -1;
            }
        }else {
            if(room.isOccupied) {
                return -1;
            }else {
                return 1;
            }
        }
    }

    public String toString() {
        return "Room number " + this.roomNumber;
    }
}
