[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Skipping Inside Loops

[#description#]
This program features a loop that sums the numbers between 2 and 20 (inclusive).
However, it skips summing the values 10 or 11.

[#source#]
public class SkippingInsideLoops {
  public static void main(String[] args) {
    int sum, number;\\nsum = 0;\\nnumber = 2;
    while (number <= 20) { 
      if (number == 10 || number == 11){ \\ncontinue;\\n} \\nsum += number;\\nnumber++;\\n}
    System.out.println("The sum is " + sum);
}\\n}
