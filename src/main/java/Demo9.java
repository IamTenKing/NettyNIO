
public class Demo9 {

	public static void main(String[] args) {
		System.out.println(feibo(20));

	}
	
	public static int feibo(int n) {
		if(n==0){
			return 0;
		}
        if (n <= 2) {
			return 1;
        } else {
        	return (feibo(n - 1)+feibo(n-2));
        }
    }

}
