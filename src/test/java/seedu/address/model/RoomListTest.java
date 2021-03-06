package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalRooms.ROOM7_PATIENT_ALICE_NO_TASK;

import java.util.Optional;
import java.util.PriorityQueue;

import org.junit.jupiter.api.Test;

import seedu.address.model.room.Room;

class RoomListTest {

    @Test
    public void getRoomWithRoomNumber_roomNotInList_returnsEmptyOptional() {
        RoomList roomList = new RoomList();

        // Positive room number
        Optional<Room> optionalRoom = roomList.getRoomWithRoomNumber(20);
        assertTrue(optionalRoom.isEmpty());

        // Negative room number
        optionalRoom = roomList.getRoomWithRoomNumber(-20);
        assertTrue(optionalRoom.isEmpty());
    }

    @Test
    public void getRoomWithRoomNumber_roomInList_returnsRoom() {
        RoomList roomList = new RoomList();
        roomList.addRooms(ROOM7_PATIENT_ALICE_NO_TASK);
        Optional<Room> optionalRoom = roomList.getRoomWithRoomNumber(7);
        assertTrue(optionalRoom.map(room -> room.getRoomNumber() == 7).orElse(false));
    }

    @Test
    void testEquals() {
        PriorityQueue<Room> rooms = new PriorityQueue<>();
        Room[] arrayOfRooms = new Room[10];
        RoomList roomList1 = new RoomList(rooms, 10);
        RoomList roomList2 = new RoomList(rooms, 10);
        //same object -> returns true
        assertTrue(roomList1.equals(roomList1));

        //diff object, but same fields -> returns true
        assertTrue(roomList1.equals(roomList2));

        //null object -> returns false
        assertFalse(roomList1.equals(null));

        roomList2 = new RoomList(rooms, 10);
        PriorityQueue<Room> rooms1 = new PriorityQueue<>();
        rooms1.add(new Room(1));
        roomList2.setRooms(rooms1);
        //different Priority Queue of rooms -> returns false
        assertFalse(roomList1.equals(roomList2));

        roomList2 = new RoomList(rooms, 10);
        roomList2.setNumOfRooms(100);
        //different number of rooms -> returns false
        assertFalse(roomList1.equals(roomList2));
    }

    @Test
    void testPriorityQueueEquals() {
        RoomList roomList = new RoomList();
        PriorityQueue<Room> rooms1 = new PriorityQueue<>();
        PriorityQueue<Room> rooms2 = new PriorityQueue<>();

        //same PriorityQueue -> returns true
        assertTrue(roomList.equals(rooms1, rooms1));

        //2 empty PriorityQueue of rooms -> returns true
        assertTrue(roomList.equals(rooms1, rooms2));

        for (int i = 0; i < 10; i++) {
            Room room = new Room(i);
            rooms1.add(room);
            rooms2.add(room);
        }


        //2 PriorityQueue of rooms with same content -> returns true
        assertTrue(roomList.equals(rooms1, rooms2));

        for (int i = 0; i < 10; i++) {
            Room room = new Room(i);
            rooms1.add(room);
            rooms2.add(room);
        }

        Room room = rooms2.poll();

        //2 PriorityQueue of different size -> returns false
        assertFalse(roomList.equals(rooms1, rooms2));

        for (int i = 0; i < 10; i++) {
            Room r = new Room(i);
            rooms1.add(r);
            rooms2.add(r);
        }
        rooms2.add(room);
        rooms1.poll();
        rooms1.add(new Room(100));
        //2 PriorityQueue of different content -> returns false
        assertFalse(roomList.equals(rooms1, rooms2));

    }
}
