[#author#]
Alessio Gaspar

[#language#]
java 

[#title#]
Integer Palindromes

[#description#]
This method tests if the digits of a given integer form a palindrome.

[#source#]
public boolean isPalindrome(int number) {
	int palindrome = number;
	int reverse = 0;

	while (palindrome != 0) {
		int remainder = palindrome % 10;
		reverse = reverse * 10 + remainder;
		palindrome = palindrome / 10;
	}

	if (number == reverse) {
		return true;
	}
	return false;
}
