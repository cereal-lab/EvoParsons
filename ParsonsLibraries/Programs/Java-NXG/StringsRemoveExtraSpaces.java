//<fragment>
public class StringsRemoveExtraSpaces {
  public static void main(String[] args){
//</fragment>
//<fragment>
      String[] inputs = { "   something", "something  ", "some     thing"};
String[] outputs = {" something", "something ", "some thing"};
//</fragment>
//<fragment>
      for(int i = 0 ; i < inputs.length ; i++){ 
 String result = removeExtraSpaces(inputs[i]);
//</fragment>
//<fragment>
         if(result.compareTo(outputs[i]) != 0) { 
 System.out.println("Test #" + i + " failed, result was " + result); 
 } 
 } // end of for loop 
} //end of main method
//</fragment>
//<fragment>
   public static String removeExtraSpaces(String s){
//</fragment>
//<fragment>
      String result = "";
//</fragment>
//<fragment>
      for(int index = 0; index < s.length()-1 ; index++){
//</fragment>
//<fragment>
         if(!((s.charAt(index) == ' ')&&(s.charAt(index+1) == ' '))) { 
 result += s.charAt(index); 
 }
//</fragment>
//<fragment>
       } // end of for loop
 result += s.charAt(s.length()-1);
return result;
} // end of removeExtraSpaces method
} // end of class
//</fragment>
