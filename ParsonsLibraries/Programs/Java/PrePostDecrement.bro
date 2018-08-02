[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Pre and Post Decrement

[#description#]
This program demonstrates the use of pre and post decrements in variable assignments;
First it decreases the value of a, then assigns it to b.
Second it assigns the value of d to c and then decreases the value of d.

[#source#]
public class PrePostDecrement{
    public static void main(String[] args) {
        int a = 35;\\nint b = 45;\\nint c = 55;\\nint d = 65;
        b = --a;\\nSystem.out.println("After assignment, Both a and b should be 34");
        c = d--;\\nSystem.out.println("After assignment, c  and d should be 65 and 64 respectively");
   }\\n}
