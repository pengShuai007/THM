package APP.Comm.DotNet;
/**
 * comments:辅助DotNet迁移类
 * 
 * sjl modify 2013-11-7下午10:14:58
 */
public class HrpStringBuilder  {
	
	StringBuilder builder=null;
	
	public HrpStringBuilder() {
		super();
		// TODO Auto-generated constructor stub
		builder=new StringBuilder();
	}

	public HrpStringBuilder(String value) {
		builder=new StringBuilder(value);
	}

	
	
	public  HrpStringBuilder Clear(){
		builder.delete(0, builder.length());
		return this;
	}
	
	public  HrpStringBuilder delete(int start,int length){
		builder.delete(start, length);
		return this;
	}
	
	
	public HrpStringBuilder append(String value){
		builder.append(value);
		return this;
	}
	
	public String toString(){
		return builder.toString();
	}

	public StringBuilder getBuilder() {
		return builder;
	}

	public void setBuilder(StringBuilder builder) {
		this.builder = builder;
	}
	
}
