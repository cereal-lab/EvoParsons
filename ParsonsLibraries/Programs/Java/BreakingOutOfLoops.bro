[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Breaking Out Of Loops

[#description#]
This program features a loop that sums the numbers between 1 and 20 (inclusive).
However, it breaks out of the loop as soon as the sum is larger than 30.


[#source#]
public class BreakingOutOfLoops{
  public static void main(String[] args) {
    int sum, number;\\nsum = 0;\\nnumber = 1;
    while (number <= 20){ \\nif (sum > 30){ \\nbreak;\\n} \\nsum = sum + number;\\nnumber++;\\n}
   }\\n}
