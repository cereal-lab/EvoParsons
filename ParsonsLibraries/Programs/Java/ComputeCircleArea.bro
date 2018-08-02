[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Compute Circle Area

[#description#]
This program displays the area of a circle based on a radius value provided by the user.
The formula for calculating the area of a circle is 3.14159 * radius * radius

[#source#]
import java.util.Scanner;
public class ComputeCircleArea {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);\\ndouble radius;\\ndouble area;\\nSystem.out.print("Enter a number for radius: ");\\nradius = input.nextDouble();
    area = radius * radius * 3.14159;
    System.out.println("The area for the circle of radius " +radius + " is " + area);
   }\\n}
