/*[#author#]
EvoParsons Team

[#language#]
C

[#title#]
Anagram

[#description#]
This program checks if two strings are anagram. Please note that the strings
contain only small alphabet letters. Function is_anagram counts the frequency
of character in the given strings and stores them in two separate array of integers.
is_anagram calculates the frequency of first_string first then second_string.
After that, it checks if the the frequency of each character in one array is equal to
that of other array. The program returns 1 if the strings are anagram, -1 otherwise.
Please implement main, then is_anagram.


[#source#]
*/


#include<stdio.h>\\n#define SIZE 26\\nint is_anagram(char *, char *);
int main() {\\nchar fs[SIZE], ss[SIZE];\\nprintf("%d\n", is_anagram(gets(fs), gets(ss)));\\nreturn 0;\\n}//end of main
int is_anagram(char *first, char *second) { int c = 0; \\n int frequency_first_string[SIZE] = {0}; \\n int frequency_second_string[SIZE] = {0};
  while(*first) { \\n frequency_first_string[*first++ - 'a']++; \\n }
  for(c = 0 ; second[c] ; c++) { \\n frequency_second_string[second[c] - 'a']++; \\n }
  for (c = 0; c < SIZE; c++) { \\n if (frequency_first_string[c] != frequency_second_string[c]) { \\n return -1; \\n } \\n }
  return 1; \\n }//end of is_anagram
