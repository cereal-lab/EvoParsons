[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Future Tuition

[#description#]
In the following, we assume an initial tuition of 10,000 dollars and an increase of seven percent per year.
This programs then uses this data to display how many years it will take for the tuition to double i.e. become greater than (>) 20,000 dollars.

[#source#]
public class FutureTuition {
  public static void main(String[] args) { 
    double tuition;\\nint year;\\ntuition = 10000;\\nyear = 1;
    while (tuition <= 20000) { \\ntuition = tuition * 1.07;\\nyear++;\\n}
    System.out.println("Tuition will be Double in "  + year + " years");
   }\\n}

