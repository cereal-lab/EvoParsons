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
   while (*s1) { \\n s1++; \\n }
   while ((*s1 = *s2) != '\0') { \\n s1++;\\n s2++; \\n }
   *s1 = '\0'; \\n } //end of str_concat
