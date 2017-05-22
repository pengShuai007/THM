package APP.Model;
public class HosRecordForFTL{
	String index;
	String name;
	String number;
	
	public void setIndex(String index){
		this.index=index;
	}
	public String getIndex(){
		return index;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
	public void setNumber(String number){
		this.number = number;
	}
	public String getNumber(){
		return number;
	}
	public HosRecordForFTL(String index,String name,String number){
		this.index = index;
		this.name = name;
		this.number = number;
	}
	
	public void print(){
		System.out.println(index+","+name+","+number);
	}
}