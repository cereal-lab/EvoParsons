[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Chinese Zodiac

[#description#]
This program reads an integer from the user, representing a year.
It then displays the corresponding Chinese Zodiac for that year by applying a modulo 12.
Results for values 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 are respectively monkey, rooster, dog, pig, rat, ox, tiger, rabbit, dragon,snake, horse and sheep.
For your program to be accepted as correct, you need to consider the cases in ascending numerical order.


[#source#]
import java.util.Scanner;
public class ChineseZodiac {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);\\nSystem.out.print("Enter a year: ");\\nint year = input.nextInt();
    switch (year % 12) {
      case 0: \\n  System.out.println("monkey");\\n  break;
      case 1: \\n  System.out.println("rooster");\\n  break;
      case 2: \\n  System.out.println("dog");\\n  break;
      case 3: \\n  System.out.println("pig");\\n  break;
      case 4: \\n  System.out.println("rat");\\n  break;
      case 5: \\n  System.out.println("ox");\\n  break;
      case 6: \\n  System.out.println("tiger");\\n  break;
      case 7: \\n  System.out.println("rabbit");\\n  break;
      case 8: \\n  System.out.println("dragon");\\n  break;
      case 9: \\n  System.out.println("snake");\\n  break;
      case 10: \\n  System.out.println("horse");\\n   break;
      case 11: \\n  System.out.println("sheep");\\n  break;
     }\\n}\\n}
