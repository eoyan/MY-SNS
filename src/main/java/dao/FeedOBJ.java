package dao;

public class FeedOBJ {
	private String id, content, ts, images;
	
	public FeedOBJ(String id, String content, String ts, String images) {
		
		this.id = id;
		this.content = content;
		this.ts = ts;
		this.images = images;
	}
	
	public String getId() { return this.id; }
	public String getContent() { return this.content; }
	public String getTs() { return this.ts; }
	public String getImages() { return this.images; }
}
