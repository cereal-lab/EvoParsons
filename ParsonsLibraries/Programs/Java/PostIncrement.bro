[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Post Increment

[#description#]
This program demonstrates the effect of post increment in variable assignment.
It first assigns the value of the variable alphab into another variable temp.
Then, it increases the value of alphab.

[#source#]
public class PostIncrement {
  public static void main(String args[]) {
    char alphab;\\nalphab = 'A';\\nchar temp;
    temp = alphab++;\\nSystem.out.println("After assignment, value of temp should be A");
   }\\n}
