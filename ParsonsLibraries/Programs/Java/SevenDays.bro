[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Seven Days

[#description#]
This program reads from the user an integer value representing the number of a week day (0 for sunday, up to 6 for saturday).
It then displays the name of the corresponding day of the week.
To be considered correct, your program will have to consider the cases in ascending numerical order, starting at 0 and going up to 6.

[#source#]
import java.util.Scanner;
public class SevenDays {
  public static void main (String[] args) {
    Scanner input = new Scanner(System.in);\\nint day;\\nday = input.nextInt();
    switch (day) {
      case 0: \\n  System.out.println("Sunday");\\n  break;
      case 1: \\n  System.out.println("Monday");\\n  break;
      case 2: \\n  System.out.println("Tuesday");\\n  break;
      case 3: \\n  System.out.println("Wednesday");\\n  break;
      case 4: \\n  System.out.println("Thursday");\\n  break;
      case 5: \\n  System.out.println("Friday");\\n  break;
      case 6: \\n  System.out.println("Saturday");\\n  break;
      default: \\n  System.out.println("Invalid input");\\n  break;
   }\\n}\\n}
