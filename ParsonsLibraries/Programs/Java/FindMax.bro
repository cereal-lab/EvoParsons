[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Find Maximum

[#description#]
This program first defines and then uses a method named maximum that takes two integer parameters. 
This method finds the maximum between its two parameters then returns it.
If the two parameter values are the same then the method returns the first integer.

[#source#]
public class FindMax {\\npublic static int maximum(int firstNumber, int secondNumber) {
    int max;
    if (firstNumber > secondNumber){
      max = firstNumber;
    } else if (secondNumber > firstNumber){
      max = secondNumber;
    } else { 
      max = firstNumber;\\n}
    return max;\\n}
  public static void main(String[] args) {
    int maximum = maximum(25, 85);
    System.out.println("Maximum between 25 and 85 is " + maximum);
   }\\n}




