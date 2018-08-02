[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Multiplication Table

[#description#]
This program displays the multiplication table from 1 to 9 (inclusive).

[#source#]
public class MultiplicationTable {
  public static void main(String[] args) {
    for (int i = 1; i <= 9; i = i + 1) { 
      for (int j = 1; j <= 9; j = j + 1){ \\n  System.out.println(i + " * " + j + " = " + (i*j));\\n}
    }\\n}\\n}
