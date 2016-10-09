public class Demo7 {
	public static void main(String[] args){
//		HashMap<K, V>
		MyHashMap map = new MyHashMap();
		map.put("1", 1);
		map.put("2", 2);
		map.put("2", "a");
		 Object object2 = map.get("1");
		Object object = map.get("2");
		Object object3 = map.get("3");
		int size = map.size();
		boolean containsValue = map.containsValue("b");
		boolean containsKey = map.containsKey("5");
		System.out.println(object+"  "+object2+" "+object3+" "+size+" "+containsValue+" "+containsKey);
//		HashMap<String, String> map = new HashMap<>();
//		map.put("1", "1");
//		map.put("2", "2");
//		map.put("2", "a");
//		String string = map.get("1");
//		String string2 = map.get("2");
//		System.out.println(string+" "+string2);
		
	}

}
 class MyHashMap {  
    private static final int SIZE = 16;  
    private Entry table[] = new Entry[SIZE];
    private int count = 0; //用于计算size大小
  
    /** 
     * 定义一个用于存放真实数据的结构
     */  
    final class Entry { 
 
        final String key;  
        Object value;  
        Entry next;  
  
        Entry(String k, Object v) {  
            key = k;  
            value = v;  
        }  
  
        public  Object getValue() {  
            return value;  
        }  
  
        public void setValue(Object value) {  
            this.value = value;  
        }  
  
        public String getKey() {  
                return key;  
        }  
    }  
  
    /**
     * put一个值
     * @param key
     * @param v
     */
    public void put(String key, Object v) {  
        int hash=hash(key);  
        Entry e=table[hash];  
        if(e!=null){
        	//判断当前没有链表时数组中的key是否是一样的，如果一样那么替换
        	if(e.key.equals(key)){
                e.value=v;  
                return ;  
        	}
            //检查链表中是否有相同的key
            while(e.next!=null){  
            	//重复的key，替换原来的value值
                if(e.key.equals(key)){  
                    e.value=v;  
                    return ;  
                }  
                e=e.next;  
            }  
            //在链的末尾添加新的元素  
            Entry end=new Entry(key,v);  
            e.next=end;  
        }else{  
        //如果该hash位置没有值，直接放入值
        Entry newValue=new Entry(key,v);  
        table[hash]=newValue;  
        }
        count++;
  
    } 
    
    /**
     * 计算hashCode值
     * @param key
     * @return
     */
    public final int hash(String key){
    	return key.hashCode()%SIZE;
    }
    /**
     * get值
     * @param key
     * @return
     */
    public final Object get(String key) {
    	//通过hash值找到数组中对应的数据位置
        Entry e = table[hash(key)];
        //用于需找链表的中的数据
        while(e != null) {//如果当前hash位置有值，但是equals不同，所以要继续找，用循环
        	//对应位置有值，并且是唯一返回
            if(e.key.equals(key)) {  
                return e.value;  
            }
            //如果该位置的hashCode相同，但是key的equals方法不同，那么说明用hashCode冲突，继续查找链表
            e = e.next;  
        }
        //都没有找到，证明没有对应的值，放回null
        return null;  
    }  
  
    public final int size(){
    	return count;
    }
    
    public boolean containsValue(Object obj){
    	Entry datas[];
    	//判断数组是否为null
        if((datas = table) != null && size() > 0) {
        	//循环数组中的元素
            for(int i = 0; i < datas.length; i++){
                for(Entry node = datas[i]; node != null; node = node.next){
                    Object obj1;
                    //找到对应的值相同，
                    if((obj1 = node.value) == obj || obj != null && obj.equals(obj1))
                        return true;
                }

            }

        }
    	return false;
    }
    
    public boolean containsKey(String key){
    	return get(key) != null;
    }
   
 }
