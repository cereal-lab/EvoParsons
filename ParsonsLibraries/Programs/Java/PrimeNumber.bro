[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Prime Number

[#description#]
This program displays the first 50 prime numbers.
It displays 10 prime numbers in each line.
So, it will print 5 lines each containing 10 prime numbers  separated by space.

[#source#]
public class PrimeNumber {
    public static void main(String[] args){
      int max=50;\\nint width=10;\\nint count=0;\\nint number=2;
      while (count < max) {
        boolean isPrime = true;
        for (int divisor = 2; divisor <= number / 2; divisor = divisor + 1) {
          if (number % divisor == 0){\\n    isPrime=false;\\n    break;\\n}
        }
        if (isPrime){ \\ncount = count + 1;
          System.out.print(number + " ");
          if (count % width == 0){\\n    System.out.println();\\n } \\n }
          number = number + 1;
   }\\n}\\n}

