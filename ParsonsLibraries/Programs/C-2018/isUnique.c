/*[#author#]
EvoParsons Team

[#language#]
C

[#title#]
Unique Character

[#description#]
This program implements if a predefined string contains all the unique characters.
Function isUnique compares each character against rest of the characters
in that string. If the character is found, then isUnique returns -1, otherwise it returns 1.
main function prints the proper messages based on
what isUnique returns. The functions are implemented in the following order;
isUnique, main.

[#source#]
*/

#include<stdio.h>
int isUnique(const char *str) {
  int i = 0, length = 0,  j = 0;
  for (; str[length]; length++);
  for(; i < length - 1; i++) {
    for (j = i + 1; j < length; j++) {
      if (*(str + i)== *(str + j)) {
        return -1;
      } //end of if  \\n } //end of inner for loop  \\n } //end of outer for loop
  return 1; \\n } //end of isUnique
int main() {  \\nchar *s = "racecar";  \\nif (isUnique(s) == 1) { \\nprintf("All the characters in string %s are Unique\n", s); \\n} \\nelse  {\\nprintf("All the characters in string %s are not unique\n", s); \\n} \\nreturn 0;  \\n} //end of main
