[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
BMI Interpreter

[#description#]
This program stores an arbitrary Body Mass Index (BMI) value in a local variable.
It then looks at the value and display a message indicating whether it corresponds to being underweight (< 18.5), normal (< 25), overweight (< 30), or obese. 

[#source#]
public class BMIInterpreter {
  public static void main(String[] args) {
    double bmi;\\nbmi = 24.65;
    if (bmi < 18.5){ \\n  System.out.println("Underweight");\\n}
    else if (bmi < 25){ \\n  System.out.println("Normal");\\n}
    else if (bmi < 30){ \\n  System.out.println("Overweight");\\n}
    else { \\nSystem.out.println("Obese");\\n}
   }\\n}



