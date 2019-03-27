//<fragment>
public class StringsTokenizer {
   public static void main(String[] args){   
String input = "The result of tokenizing this string is...";
System.out.println(input);
tokenize(input);
} //end of main method
//</fragment>
//<fragment>
   public static void tokenize(String s){
//</fragment>
//<fragment>
      int index = 0, howmany = 0, len = s.length();
//</fragment>
//<fragment>
      do{ 
 String word=""; 
 while((index < len) && (s.charAt(index) == ' ')) { 
 index++ ; 
 }
//</fragment>
//<fragment>
         while((index < len) && (s.charAt(index) != ' ')) { 
 word += s.charAt(index++); 
 }
//</fragment>
//<fragment>
         System.out.println("Word # " + ++howmany + " = " + word);
//</fragment>
//<fragment>
      }while(index < len); 
 } // end of tokenize method 
} //end of class
//</fragment>
