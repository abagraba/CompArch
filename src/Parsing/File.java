package Parsing;

import java.util.LinkedList;



public class File extends Entry {

	private LinkedList<Room>	rooms	= new LinkedList<Room>();

	public File() {

	}

	public void addRoom(Room room) {
		rooms.add(room);
	}

	@Override
	public void debug(int indent) {
		for (Room r : rooms)
			r.debug(indent + 1);
	}

	@Override
	public void codeGen(int indent) {
		for (Room r : rooms)
			r.codeGen(indent + 1);
	}

}
