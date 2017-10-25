package comp5216.sydney.edu.au.SI.Tag;

public class Word {
	
	String value;
	int count;
	
	public Word(String value, int count){
		this.value = value;
		this.count = count;
	}
	
	public Word(String value){
		this.value = value;
	}
	
	public Word(){
		this.value = "";
		this.count = 0;
	}
	
	public String getValue(){
		return value;
	}
	
	public void setValue(String value){
		this.value = value;
	}
	
	public void setCount(int count){
		this.count = count;
	}
	
	public void addCount(){
		count++;
	}

}
