import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Demo5 {
	public static void main(String[] args) {
		
		f(5);
		
		
		/*final A a = new A();
		new Thread(new Runnable() {
			
			public void run() {
				try {
					a.run1();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		},"thread1").start();
		
		new Thread(new Runnable() {
			
			public void run() {
				try {
					a.run2();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		},"thread2").start();*/
	}
	
	
	public static int f(int n) {
		if(n==0){
			return 0;
		}
        if (n <= 2) {
			return 1;
        } else {
        	return (f(n - 1)+f(n-2));
        }
    }
	
	private static class A {
		private boolean flag = true;//如果改成false 会是什么效果。如果用int变量控制是否更好
		private Lock lock = new ReentrantLock();
		private Condition run1 = lock.newCondition();
		private Condition run2 = lock.newCondition();
		public void run1() throws Exception {
			lock.lock();
			while(!flag){
				run1.await();
			}
			for (int i = 0; i < 10; i++) {
				
				System.out.println("run1 开始执行");
			}
			flag = false;
			run2.signal();
			lock.unlock();
		}
		
		public void run2() throws Exception {
			lock.lock();
			while(flag){
				
				run2.await();
			}
			for (int i = 0; i < 10; i++) {
				
				System.out.println("run2 开始执行");
			}
			flag = true;
			run1.signal();
			lock.unlock();
		}
	}
	

}
