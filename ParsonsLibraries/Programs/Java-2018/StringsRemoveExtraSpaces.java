/*
[#author#]
EvoParsons Team

[#language#]
java

[#title#]
StringsRemoveExtraSpaces

[#description#]
This program replaces consecutive spaces in a string by a single space.
The main method calls the removeExtraSpaces method on a series of strings.
Then it displays the strings after extra spaces have been removed.
Implement these method in the following order; main, removeExtraSpaces.

[#source#]
*/
public class StringsRemoveExtraSpaces {\\n  public static void main(String[] args){
      String[] inputs = { "   something", "something  ", "some     thing"};
      for(int i = 0 ; i < inputs.length ; i++){ \\n String result = removeExtraSpaces(inputs[i]); \\n System.out.println(result);\\n } // end of for loop \\n} //end of main method
   public static String removeExtraSpaces(String s){
      String result = "";
      for(int index = 0; index < s.length()-1 ; index++){
         if(!((s.charAt(index) == ' ')&&(s.charAt(index+1) == ' '))) { \\n result += s.charAt(index); \\n }
       } // end of for loop\\n result += s.charAt(s.length()-1);\\nreturn result;\\n} // end of removeExtraSpaces method\\n} // end of class
