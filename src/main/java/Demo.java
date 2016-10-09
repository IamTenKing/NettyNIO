import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.SynchronousQueue;


public class Demo {
	
	
	
	
	
	static class ForkJoinDemo extends RecursiveTask<Integer>{

		private static final int THRESHOLD=2;
		private int start;
		private int end;
		public ForkJoinDemo(int start,int end) {
			this.start=start;
			this.end=end;
		}
		
		@Override
		protected Integer compute() {
			int sum=0;
			boolean flag = (end-start)<=THRESHOLD;
			if(flag){
				for (int i = start; i <= end; i++) {
					sum+=i;
				}
			}else{
				int middle = (start+end)/2;
				ForkJoinDemo left= new ForkJoinDemo(start,middle);
				ForkJoinDemo right= new ForkJoinDemo(middle+1,end);
				left.fork();
				right.fork();
				int leftResult= left.join();
				int rightResult=right.join();
				sum = leftResult + rightResult;
			}
			return sum;
		}
		
		
	}

	
	public static void main(String[] args) throws Exception {
		
		ForkJoinPool forkJoinPool=new ForkJoinPool();
		ForkJoinDemo demo2 = new ForkJoinDemo(1, 10);
		Future<Integer> submit = forkJoinPool.submit(demo2);
		Integer integer = submit.get();
		System.out.println(integer);
//		final SynchronousQueue<String> queue = new SynchronousQueue<String>();
//		final List<String> list = new ArrayList<String>();
//		for (int i = 1; i <= 100; i++) {
//			list.add(i+"");
//		}
//		final ArrayBlockingQueue<String> arrqueue = new ArrayBlockingQueue<String>(list.size(), true, list);
//		final BlockingQueue<String> queue = new ArrayBlockingQueue<String>(list.size()+1);
//		User user = new User(1,"aa",new Date());
//		User user2 = new User(2,"bb",new Date());
//		User user3 = new User(3,"cc",new Date());
//		User user4 = new User(4,"dd",new Date());
//		User user5 = new User(5,"ee",new Date());
//		list.add(user);
//		list.add(user2);
//		list.add(user3);
//		list.add(user4);
//		list.add(user5);
		
//		int availableProcessors = Runtime.getRuntime().availableProcessors();
//		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(availableProcessors+1);
//		final Demo demo = new Demo();
//		long currentTimeMillis = System.currentTimeMillis();
//		for (String str : list) {
//			demo.getUser(str);
//		}
//		System.out.println("普通用时："+(System.currentTimeMillis()-currentTimeMillis));
//		long currentTimeMillis2 = System.currentTimeMillis();
//		for (int i = 0; i < list.size(); i++) {
//			for (int j = 0; j <5; j++) {
//				newFixedThreadPool.execute(new Runnable() {
//					
//					public void run() {
//						try {
//							String take = arrqueue.take();
//							demo.getUser(take);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//					}
//				});
//			}
//			
//		}
//		new Thread(new Runnable() {
//			
//			public void run() {
//				for (int i = 0; i < list.size(); i++) {
//					try {
//						System.out.println("放入的线程="+Thread.currentThread().getName());
//						queue.put(list.get(i));
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//				
//			}
//		}).start();
		
//		
//		System.out.println("多线程用时："+(System.currentTimeMillis()-currentTimeMillis2));
//		
//		newFixedThreadPool.shutdown();
//		
		
	}
	
	
	
	public  void getUser(String user){
			System.out.println("当前线程="+Thread.currentThread().getName()+",id="+user);
	}
	
	public static class User{
		
		
		public User(Integer id, String name, Date date) {
			this.id = id;
			this.name = name;
			this.date = date;
		}
		private Integer id;
		private String name;
		private Date date;
		/**
		 * @return the id
		 */
		public Integer getId() {
			return id;
		}
		/**
		 * @param id the id to set
		 */
		public void setId(Integer id) {
			this.id = id;
		}
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
		/**
		 * @return the date
		 */
		public Date getDate() {
			return date;
		}
		/**
		 * @param date the date to set
		 */
		public void setDate(Date date) {
			this.date = date;
		}
		
	}

}


