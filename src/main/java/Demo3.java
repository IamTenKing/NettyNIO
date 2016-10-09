import java.util.concurrent.CopyOnWriteArraySet;

public class Demo3 {
	public static void main(String[] args) {
		new Child("tom");
	}

}

class Parent{
	static{
		System.out.println("parent");
	}
	public Parent(){
		System.out.println("1");
	}
	public Parent(String name){
		System.out.println("2");
	}
}
class Child extends Parent{
	static{
		System.out.println("child");
	}
	private Parent parent;
	private String name;
	public Child(){
		System.out.println("4");
	}
	public Child(String name){
		System.out.println("3");
		this.name=name;
		parent=new Parent(name);
	}
}
