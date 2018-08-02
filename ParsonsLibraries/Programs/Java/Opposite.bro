[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Opposite sign of a number

[#description#]
This program first defines and then uses a method named oppositeSign that takes one integer parameter.
This method returns -1 if its parameter is positive (> 0), 1 if it is negative (< 0), and 0 if the number is 0.

[#source#]
public class Opposite {\\npublic static int oppositeSign(int number) {
    if (number > 0){
      return -1;
    } else if (number < 0){
      return 1;
    } else { \\nreturn 0;\\n}
  }\\npublic static void main(String[] args) {
    int sign = oppositeSign(75);
    System.out.println("Opposite sign of 75 is " + sign);
}\\n}




