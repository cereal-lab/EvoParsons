[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Fahrenheit to Celcius

[#description#]
This program reads from the user a double value representing a temperature expressed in degree Fahrenheit.
It then displays its conversion in degree Celcius.
The conversion formula is C = (5.0/9) * (F - 32) where F and C refers to degree in Fahrenheit and Celcius respectively.

[#source#]
import java.util.Scanner;
public class FahrenheitToCelsius {
  public static void main(String[] args) {
      Scanner input = new Scanner(System.in);\\ndouble fahrenheit, celsius;\\nSystem.out.print("Enter a degree in Fahrenheit: ");\\nfahrenheit = input.nextDouble();
      celsius = (5.0 / 9) * (fahrenheit - 32);
      System.out.println("Fahrenheit " + fahrenheit + " is " + celsius + " in Celsius");
   }\\n}
