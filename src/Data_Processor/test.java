package Data_Processor;

public class test {
	public test(){
		
	}
	
	public static void main(String args[]){
		test t = new test();
		
		String st1 = "SONG ORIGIN HAND MOVEMENT DANC";
		String st2 = "hand";
		
		if(st1.contains(st2)){
			System.out.println( " Yes ");
		}else{
			System.out.println( " No ");
		}
	}

}
