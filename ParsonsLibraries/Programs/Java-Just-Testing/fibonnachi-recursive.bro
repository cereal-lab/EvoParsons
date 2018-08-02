[#author#]
Alessio Gaspar

[#language#]
java 

[#title#]
Fibonnachi Numbers: Recursive Implementation

[#description#]
This program consists of one public method which will take the following parameters; 
<ul>
<li>n a positive integer value</li>
</ul>
It will then compute the nth Fibonnachi number and return it as a long.

[#source#]
public long fib(int n) {
	if(n == 0)
		return 0;
	if(n == 1)
		return 1;
	return fib(n-1) + fib(n-2);
}
