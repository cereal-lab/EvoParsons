[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
HiFive Or HiTwo

[#description#]
This program reads an integer value from the user.
It then displays HiFive if the value is divisible by 5.
If not, it displays HiTwo if it is divisible by 2

[#source#]
import java.util.Scanner;
public class HiFiveOrHiTwo {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);\\nSystem.out.println("Enter an integer: ");\\nint number = input.nextInt();
    if (number % 5 == 0){ \\nSystem.out.println("HiFive");\\n}
    else if (number % 2 == 0){ \\nSystem.out.println("HiTwo");\\n}
  }\\n}
