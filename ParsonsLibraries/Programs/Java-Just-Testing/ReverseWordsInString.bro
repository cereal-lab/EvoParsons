[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Reverse every words in a string

[#description#]
The String is restricted to alphanumeric only. ReverseWords(String) calls
reverse(..,..,..) which calls swap(..,..,..)


[#source#]
public static String ReverseWords(String str) {
	StringBuilder sb = new StringBuilder(str); //This line must be written before line X
	Pattern p = Pattern.compile("[A-Za-z0-9]+"); //This is line X
	Matcher m = p.matcher(str); 
	while(m.find()) 						
		reverse(sb, m.start(), m.end() - 1);
	return sb.toString();
}
public static void reverse(StringBuilder sb, int start, int end) {
	while (start < end)
		swap(sb, start++, end--);
}
public static void swap(StringBuilder sb, int start, int end) {		
	char temp = sb.charAt(start);
	sb.setCharAt(start, sb.charAt(end));
	sb.setCharAt(end, temp);
}
