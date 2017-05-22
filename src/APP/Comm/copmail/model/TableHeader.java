package APP.Comm.copmail.model;

public class TableHeader {

	private String name;
	private String width;
	private String height;
	private String backGroudColor;
	
	public TableHeader(){
		
	}
	public TableHeader(String name, String backGroudColor) {
		super();
		this.name = name;
		this.backGroudColor = backGroudColor;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getBackGroudColor() {
		return backGroudColor;
	}
	public void setBackGroudColor(String backGroudColor) {
		this.backGroudColor = backGroudColor;
	}

	
}
