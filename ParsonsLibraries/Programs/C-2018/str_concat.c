/*[#author#]
EvoParsons Team

[#language#]
C

[#title#]
String Concatenation

[#description#]
This program concatenates second_string at the end of first_string.
Function str_concat takes first_string and second_string as its first
and second parameter respectively. Then it concatenates second_string
at the end of first_string string. main function calls str_concat
and prints the result of concatenation. Please implement main first
then str_concat.
[#source#]
*/

#include<stdio.h>\\n#define SIZE 80\\nvoid str_concat(char *, char *);
int main() {  \\nchar first_string[SIZE], second_string[SIZE];  \\nstr_concat(gets(first_string), gets(second_string));  \\nprintf("%s\n", first_string);  \\n} //end of main
void str_concat(char* s1, char* s2){
   while (*s1)
      s1++;
   while ((*s1 = *s2) != '\0') {
      s1++;
      s2++;
    } //end of while inside str_concat
   *s1 = '\0';
} //end of str_concat
