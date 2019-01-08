/*
[#author#]
EvoParsons Team

[#language#]
java

[#title#]
StringsTokenizer

[#description#]
This program takes an example string and uses the method tokenize to identify and display each word in it.
The tokenize method uses one or more space as delimiter between words.
It displays all the words it finds in the String parameter. 
Implement these method in the following order; main, tokenize.

[#source#]
*/
public class StringsTokenizer {\\n   public static void main(String[] args){   \\nString input = "The result of tokenizing this string is...";\\nSystem.out.println(input);\\ntokenize(input);\\n} //end of main method
   public static void tokenize(String s){
         int index = 0, howmany = 0, len = s.length();
      do{
         String word="";
         while((index < len) && (s.charAt(index) == ' '))
            index++;
         while((index < len) && (s.charAt(index) != ' '))
            word += s.charAt(index++);
         System.out.println("Word # " + ++howmany + " = " + word);
      }while(index < s.length());
   } // end of tokenize method \\n} //end of class