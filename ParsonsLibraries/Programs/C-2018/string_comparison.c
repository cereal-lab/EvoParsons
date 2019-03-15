/*[#author#]
EvoParsons Team

[#language#]
C

[#title#]
String Comparison

[#description#]
This program compares two strings for equality. Two strings are equal if they
contain same character in the same position.
Function get_length calculates length of a string.
Function str_cmp first checks if the length of the two strings are equal. If their
length are  different then it returns -1. Else, it checks both strings character
by character to the end. It returns 1 if both strings are equal. For example,
"ab" and "bc" are not equal but "ab" and "ab" are equal. You should implement the
functions in the following order; main, get_length, str_cmp.


[#source#]
*/

#include<stdio.h>\\n#define SIZE 80\\nint get_length(const char *);\\nint str_cmp(const char *, const char *);
int main() {  \\nchar s1[SIZE], s2[SIZE];  \\nprintf("%d\n", str_cmp(gets(s1), gets(s2)));  \\nreturn 0;  \\n} //end of main
int get_length(const char *s) {  \\nint i = 0;  \\nfor (; s[i] != '\0'; i++);  \\nreturn i;  \\n} //end of get_length
int str_cmp(const char* s1, const char* s2){
  if(get_length(s1) != get_length(s2)) { \\n return -1; \\n }
  else{
    while( *s1 && *s2) { \\n if(*s1++ != *s2++) return -1; \\n } \\n } //end of else inside str_cmp
  return 1; \\n }//end of str_cmp
