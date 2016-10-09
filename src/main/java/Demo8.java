import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;
/**
 * 这是网上找的，还有一种用RandomAccessFile 读取文件的
 * @author acer
 *
 */
public class Demo8 {
	public static void main(String[] args) throws Exception {
		Map<String, Integer> count = count("F:\\count.txt");
		
		for (Entry<String, Integer> entrySet : count.entrySet()) {
			System.out.println(entrySet.getKey()+" : "+entrySet.getValue());
		}
	}
	
	public static Map<String, Integer> count(String path) throws Exception{
		Map<String, Integer> map = new TreeMap<String, Integer>();
		int countkey = 0;
		
		try(FileInputStream inputStream = new FileInputStream(path);
				Scanner sc = new Scanner(inputStream, "UTF-8");) {
		    while (sc.hasNextLine()) {
		    	//获取前1000，
		    	for (Entry<String, Integer> entrySet : map.entrySet()) {
					if(++countkey>=3){
						return map;
					}
				}
		        String line = sc.nextLine();
		        String[] split = line.split(" ");
		        map.put(split[0], map.containsKey(split[0])? map.get(split[0])+1 : 1);
//		         System.out.println(line);
//		         System.out.println(split[0]+", "+split[1]);
		    }
		}catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	

}


//这是我在网上看到的读取打文件，可能是没有考虑内存
class MappedBiggerFileReader {
    private MappedByteBuffer[] mappedBufArray;
    private int count = 0;
    private int number;
    private FileInputStream fileIn;
    private long fileLength;
    private int arraySize;
    private byte[] array;

    public MappedBiggerFileReader(String fileName, int arraySize) throws IOException {
        this.fileIn = new FileInputStream(fileName);
        FileChannel fileChannel = fileIn.getChannel();
        this.fileLength = fileChannel.size();
        this.number = (int) Math.ceil((double) fileLength / (double) Integer.MAX_VALUE);
        this.mappedBufArray = new MappedByteBuffer[number];// 内存文件映射数组
        long preLength = 0;
        long regionSize = (long) Integer.MAX_VALUE;// 映射区域的大小
        for (int i = 0; i < number; i++) {// 将文件的连续区域映射到内存文件映射数组中
            if (fileLength - preLength < (long) Integer.MAX_VALUE) {
                regionSize = fileLength - preLength;// 最后一片区域的大小
            }
            mappedBufArray[i] = fileChannel.map(FileChannel.MapMode.READ_ONLY, preLength, regionSize);
            preLength += regionSize;// 下一片区域的开始
        }
        this.arraySize = arraySize;
    }

    public int read() throws IOException {
        if (count >= number) {
            return -1;
        }
        int limit = mappedBufArray[count].limit();
        int position = mappedBufArray[count].position();
        if (limit - position > arraySize) {
            array = new byte[arraySize];
            mappedBufArray[count].get(array);
            return arraySize;
        } else {// 本内存文件映射最后一次读取数据
            array = new byte[limit - position];
            mappedBufArray[count].get(array);
            if (count < number) {
                count++;// 转换到下一个内存文件映射
            }
            return limit - position;
        }
    }

    public void close() throws IOException {
        fileIn.close();
        array = null;
    }

    public byte[] getArray() {
        return array;
    }

    public long getFileLength() {
        return fileLength;
    }

    public static void main(String[] args) throws IOException {
        MappedBiggerFileReader reader = new MappedBiggerFileReader("/home/zfh/movie.mkv", 65536);
        long start = System.nanoTime();
        while (reader.read() != -1) ;
        long end = System.nanoTime();
        reader.close();
        System.out.println("MappedBiggerFileReader: " + (end - start));
    }
}
