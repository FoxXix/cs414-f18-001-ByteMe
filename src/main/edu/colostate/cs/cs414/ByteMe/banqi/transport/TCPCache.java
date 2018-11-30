package main.edu.colostate.cs.cs414.ByteMe.banqi.transport;

import java.util.HashMap;
import java.util.Map;

public class TCPCache {
	
	private Map<Integer, TCPConnection> cache;
	
	public TCPCache() {
		this.cache = new HashMap<Integer, TCPConnection>();
	}
	
	public void addMap(Integer id, TCPConnection c) {
		this.cache.put(id, c);
	}
	
	public TCPConnection getById(int id) {
		return this.cache.get(id);
	}
	
	public void remove (int id) {
		this.cache.remove(id);
	}

}