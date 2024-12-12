package dao;

public class UserOBJ {
	private String id, name, ts;
	
	public UserOBJ(String id, String name, String ts) {
		this.id = id;
		this.name = name;
		this.ts = ts;
	}
	
	public String getId() { return this.id; }
	public String getName() { return this.name; }
	public String getTs() { return this.ts; }
}
