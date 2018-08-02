[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Pre Decrement

[#description#]
This program demonstrates the effect of pre decrement in variable assignments.
It decrements the value of the variable alphab and then assigns it into another variable temp.

[#source#]
public class PreDecrement {
  public static void main(String args[]) {
    char alphab;\\nalphab = 'B';\\nchar temp;
    temp = --alphab;\\nSystem.out.println("After assignment, value of temp should be A");
   }\\n}
