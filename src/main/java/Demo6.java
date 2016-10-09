import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Demo6{
	private Lock lock = new ReentrantLock();
	private Condition getCondition = lock.newCondition();
	private Condition putCondition = lock.newCondition();
	private Object datas[] = new Object[10];
	int put,get,count;
	
	public void put(Object value){
		try {
			lock.lock();
			while(count==datas.length){
				try {
					putCondition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			datas[put]=value;
			if(++put==datas.length){
				put=0;
			}
			++count;
			getCondition.signal();
			
		} finally {
			lock.unlock();
		}
		
		
		
	}
	
	public Object get(){
		try {
			lock.lock();
			while (count==0) {
				try {
					getCondition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			Object value = datas[get];
			if(++get==datas.length)
				get=0;
			--count;
			putCondition.signal();
			
			return value;
		} finally{
			lock.unlock();
		}
		
	}
}