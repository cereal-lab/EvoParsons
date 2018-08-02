[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Sum Of Two Integers

[#description#]
This program first defines and then uses a method named sumOfTwo that takes two integer parameters.
This method adds those two integers and returns their sum to the main method. 

[#source#]
public class SumOfTwoIntegers {\\npublic static int sumOfTwo(int numberOne, int numberTwo) {
    int sum = numberOne + numberTwo;
    return sum;\\n}
  public static void main(String[] args) {
    int sum = sumOfTwo(25, 35);
    System.out.println("Sum of 25 and 35 is " + sum);
   }\\n}




