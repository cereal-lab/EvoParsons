[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Order Two Cities

[#description#]
This program determines the alphabetic order of two cities which names are stored in string variables.


[#source#]
public class OrderTwoCities {
  public static void main(String[] args) {
    String cityOne, cityTwo;\\ncityOne = "Tampa";\\ncityTwo = "Orlando";
    if (cityOne.compareTo(cityTwo) == 0){ \\nSystem.out.println("City names are the same");\\n}
    else if (cityOne.compareTo(cityTwo) < 0){ \\nSystem.out.println("In alphabetical order, cityOne comes before cityTwo");\\n}
    else { \\nSystem.out.println("In alphabetical order, cityTwo comes before cityOne");\\n}
   }\\n}
