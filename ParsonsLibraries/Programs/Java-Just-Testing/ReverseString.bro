[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Reverse a String without using loop

[#description#]
ReverseString(int, String) takes an index and a string, print the string
from reverse order upto that index. Example : ReverseString(2, "Hello World")
will print dlroW oll.


[#source#]
public static void ReverseString(int index, String str) {
	if (index >= str.length())
		return;
	index = index + 1;
	ReverseString(index, str);
	System.out.println(str.charAt(index -1));
}
