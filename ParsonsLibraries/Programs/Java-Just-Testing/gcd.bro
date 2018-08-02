[#author#]
Alessio Gaspar

[#language#]
java 

[#title#]
Greater Common Divider

[#description#]
This program consists of one public method which will take the following parameters; 
<ul>
<li>x a positive integer value</li>
<li>y a positive integer value</li>
</ul>
It will then compute the GCD for its two parameters and return the resulting value. 

[#source#]
public int gcd(int x, int y){
	if(number2 == 0){
		return number1;
	}
	return gcd(y, x%y);
}
