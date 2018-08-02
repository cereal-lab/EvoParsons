[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Balancing Bracket

[#description#]
This function returns true if brackets are balanced, false otherwise. For example, IsBalanced("(()()[][") returns false

[#source#]

public static boolean IsBalanced(String expression) {
	Stack stack = new Stack();
	for (int i = 0; i < bracket.length(); i++) {
		if ((bracket.charAt(i) == '(' || bracket.charAt(i) == '[')) 
		    stack.push(bracket.charAt(i));			
		else if (bracket.charAt(i) == ')') 
		    return stack.pop() == '(';		
		else if (bracket.charAt(i) == ']') 
		    return stack.pop() == '[';		
	}
	if (!stack.IsEmpty()) return false;
return true;
}




