[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Sum Of Floats

[#description#]
The program displays the result of summing the float values between 0.01f and 1.0f (inclusive) at increments of 0.01f.

[#source#]
public class SumOfFloats {
  public static void main(String[] args) {
    float sum, value;\\nsum = 0;
    for(value = 0.01f ; value <= 1.0f ; value += 0.01f) {\\n  sum = sum + value;\\n}
    System.out.println("The sum is " + sum);
    }\\n}

