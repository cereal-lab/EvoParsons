package evoparsons.antlr.ex;
/**
 * @author Evoparsons team 
 * @Sum Of Integers
 * @description This program repeatedly prompts the user to enter integer values until a zero is entered.
It then displays the sum of all values that were entered.
 */
import java.util.Scanner;
public class SumOfIntegers {
    public static void main(String[] args){
        int data, sum;
        Scanner input = new Scanner(System.in);
        sum = 0;
        do{
            System.out.print("Please enter an integer value (0 to exit): ");
            data = input.nextInt();
            sum += data;
        } while (data != 0);
        System.out.println("The sum is " + sum);
    }
}
