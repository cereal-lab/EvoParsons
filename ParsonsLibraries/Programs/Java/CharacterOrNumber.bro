[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Character Or Number

[#description#]
This program determines whether a character is an uppercase letter, a lower case letter, a digit, or something else.
For your probram to be considered correct, you will have to test these cases in that order.

[#source#]
public class CharacterOrNumber {
  public static void main(String[] args) {
    char ch;\\nch = 'Z';
    if (ch >= 'A' && ch <= 'Z'){\\nSystem.out.println(ch + " is an UPPERCASE letter");\\n}
    else if (ch >= 'a' && ch <= 'z'){\\nSystem.out.println(ch + " is a lowercase letter");\\n}
    else if (ch >= '0' && ch <= '9'){\\nSystem.out.println(ch + " is a digit");\\n}
    else { \\nSystem.out.println(ch + " is something else");\\n}
   }\\n}
