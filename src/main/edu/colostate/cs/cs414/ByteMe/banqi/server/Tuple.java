package main.edu.colostate.cs.cs414.ByteMe.banqi.server;

public class Tuple<S1, S2> {
	public final S1 s1;
	public final S2 s2;
	public Tuple(S1 s1, S2 s2) {
		this.s1 = s1;
		this.s2 = s2;
	}
	public S1 getIp() {
		return s1;
	}
	
	public S2 getPort() {
		return s2;
	}
}