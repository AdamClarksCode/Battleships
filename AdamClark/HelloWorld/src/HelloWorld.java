import java.util.Scanner;

public class HelloWorld {

	public static void main(String[] args)
	{
		Scanner s = new Scanner(System.in);
		System.out.println("How long would you like the array to be?");

		int arrayLength = s.nextInt();

		int[] numbers = new int[arrayLength];
		for (int k = 0; k < arrayLength; k++)
		{
			numbers[k] = k;
		}
		for (int i = 0; i < numbers.length; i++)
		{
			System.out.print(numbers[i] + " ");
		}
		System.out.println();
		for (int j = 0; j < numbers.length; j++)
		{
			numbers[j] = numbers[j] *10;
			System.out.print(numbers[j] + " ");
		}
	}
}