[#author#]
Alessio Gaspar

[#language#]
java 

[#title#]
Fibonnachi Numbers: Iterative Version

[#description#]
This program consists of one public method which will take the following parameters; 
<ul>
<li>n a positive integer value</li>
</ul>
It will then compute the nth Fibonnachi number and return it as a long.

[#source#]
public long fib(int n) {
    int fib = 0;
    int a = 1;
    for(int i=0; i<n; i++) {
       fib = fib + a;
       a = fib;
    }
    return fib;
}
