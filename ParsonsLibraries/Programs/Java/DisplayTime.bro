[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Display Time

[#description#]
This program reads an integer value representing a number of seconds from the user.
It then displays how many full minutes and remaining seconds it represents.
For example, if you enter the value 183 it will display:
183 seconds is 3 minutes and 3 seconds

[#source#]
import java.util.Scanner;
public class DisplayTime {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);\\nint seconds, minutes, remainingSeconds;\\nSystem.out.println("Enter an integer for seconds: ");\\nseconds = input.nextInt();
    minutes = seconds / 60;\\nremainingSeconds = seconds % 60;
    System.out.println(seconds + " seconds is " + minutes + " minutes and " + remainingSeconds + " seconds");
   }\\n}
