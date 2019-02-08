/*[#author#]
EvoParsons Team

[#language#]
C

[#title#]
Anagram 

[#description#]
This program checks if two strings are anagram. Please note that the strings
contain only small alphabet letters. Function is_anagram counts the frequency
of letters of string separately and store them in two separate array of integers.
Then it checks if the the frequency of each character in one string is equal to
that of other string. The program returns 1 if the strings are anagram, -1 otherwise.
Please implement main, then is_anagram.


[#source#]
*/


#include<stdio.h>\\n#define SIZE 26\\nint is_anagram(char *, char *);
int main() {\\nchar fs[SIZE], ss[SIZE];\\nprintf("%d\n", is_anagram(gets(fs), gets(ss)));\\nreturn 0;\\n}//end of main
int is_anagram(char *first, char *second) {
  int c = 0, frequency_first_string[SIZE] = {0}, frequency_second_string[SIZE] = {0};
  while(*first)
    frequency_first_string[*first++ - 'a']++;
  for(c = 0 ; second[c] ; c++)
    frequency_second_string[second[c] - 'a']++;
  for (c = 0; c < SIZE; c++)
    if (frequency_first_string[c] != frequency_second_string[c])
      return -1;
  return 1;
}//end of is_anagram
