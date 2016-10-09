
public class Demo4 {

	public static void main(String[] args) throws Exception {
		Thread ta = new Thread(new A());
		Thread tb = new Thread(new B());
		ta.start();
		tb.start();
		ta.join();
		tb.join();
		System.out.println("main 线程运行");
	}
	
	private static class A implements Runnable{

		public void run() {
			for (int i = 0; i < 10; i++) {
				
				System.out.println("A 开始执行");
			}
		}
		
	}
	
	private static class B implements Runnable{

		public void run() {
			for (int i = 0; i < 10; i++) {
				
				System.out.println("B 开始执行");
			}
			
		}
		
	}
}


