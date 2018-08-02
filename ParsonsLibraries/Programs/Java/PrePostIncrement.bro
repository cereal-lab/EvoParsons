[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Pre and Post Increment

[#description#]
This program demonstrates the use of pre and post increments in variable assignments;
First, it increases a, then assigns it to b.
Then, it assigns the value of d to c, and finally increases d.

[#source#]
public class PrePostIncrement{
    public static void main(String[] args) {
        int a = 35;\\nint b = 45;\\nint c = 55;\\nint d = 65;
        b = ++a;\\nSystem.out.println("After assignment, Both a and b should be 36");
        c = d++;\\nSystem.out.println("c and d should be 65 and 66 respectively");
   }\\n}
