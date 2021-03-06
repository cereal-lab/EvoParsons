
/*[#author#]
EvoParsons Team

[#language#]
C

[#title#]
Palindrome

[#description#]

This program checks if a string is a palindrome. For example, racecar is a palindrome because it reads
same in both directions. Function is_palindrome finds out the length of my_string first. Then it
checks if the first element and the last element are same, second element and the second last
are same and so on. If any of the first element is not same as its respective last element, then
the string is not a palindrome. Please implement main first, then is_palindrome.

[#source#]

*/

#include<stdio.h>\\n#define SIZE 80\\nint is_palindrome(char * );
int main() {  \\nchar data[SIZE];  \\nprintf("%d\n", is_palindrome(gets(data)));  \\nreturn 0;  \\n}//end of main
int is_palindrome(char * my_string) {
  int i = 0, len = 0;
  for(; my_string[i]; i++)
  len = i;  i = 0;
   while(i < len) {
      if (*(my_string + i++) != *(my_string + len--)) { \\n return -1; \\n } \\n }
  return 1; \\n }//end of is_palindrome
