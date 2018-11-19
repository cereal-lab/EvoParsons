/*
[#author#]
EvoParsons Team

[#language#]
java

[#title#]
StringsRemoveExtraSpaces

[#description#]
This program replaces consecutive spaces in a string by a single space.
The main method calls the removeExtraSpaces method on a series of strings used as tests.
It displays a message only if one of the tests fails.
In the main method, declare inputs before to declare outputs.
Implement these method in the following order; main, removeExtraSpaces.

[#source#]
*/
public class StringsRemoveExtraSpaces {\\n  public static void main(String[] args){   
      String[] inputs = { "   something", "something  ", "some     thing"};\\nString[] outputs = {" something", "something ", "some thing"};
      for(int i = 0 ; i < inputs.length ; i++){
         String result = removeExtraSpaces(inputs[i]);
         if(result.compareTo(outputs[i]) != 0)
            System.out.println("Test #" + i + " failed, result was " + result);
      } // end of for loop \\n} //end of main method
   public static String removeExtraSpaces(String s){
      String result = ""; 
      for(int index = 0; index < s.length()-1 ; index++){
         if(!((s.charAt(index) == ' ')&&(s.charAt(index+1) == ' ')))
            result += s.charAt(index);         
       } // end of while loop\\n result += s.charAt(s.length()-1);\\nreturn result;\\n} // end of removeExtraSpaces method\\n} // end of class