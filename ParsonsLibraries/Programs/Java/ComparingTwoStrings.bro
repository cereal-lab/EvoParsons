[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Comparing Two Strings

[#description#]
This program uses three different ways to compare two arbitrary strings and display, for each, the outcome.

[#source#]
public class ComparingTwoStrings {
	  public static void main(String[] args) {
	    String s1,s2;\\ns1 = "Hello World";\\ns2 = "Hello Worlds";
	    System.out.println("First, we use s1.equals(s2): " + (s1.equals(s2) ? "yes" : "no"));
	    System.out.println("Second, we use s1 == s2: " + ((s1 == s2)  ? "yes" : "no"));
	    System.out.println("Third, we use s1.compareTo(s2): "+ (s1.compareTo(s2) == 0 ? "yes" : "no"));
   }\\n}
