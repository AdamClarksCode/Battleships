package testing;
import static org.junit.Assert.*;


public class Testmain {
	public static void main(String[] args) {
		String testName = getName("Test");
		System.out.println(testName);
		TestgetName();
	}
	
	public static String getName(String Name){
		return Name;
	}
	
	public static void TestgetName(){
		assertEquals("Test", getName("Test"));
	}
}
