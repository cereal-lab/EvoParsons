/*[#author#]
EvoParsons Team

[#language#]
C

[#title#]
AtEnd

[#description#]
This program implements if the second string is at the end of first string.
Function ami_at_end takes first_string and second_string as first and second
parameter respectively. It checks if second_string is at the end of first_string.
If so, it returns 1. Otherwise, ami_at_end returns -1.
Function get_length calculates the length of a string and returns it.
Inside, ami_at_end, first_string is traversed to the end, then  second_string.
You should implement the functions in the following order; main,
get_length and ami_at_end.


[#source#]
*/

#include<stdio.h> \\n #define SIZE 80 \\n char * read_string_input(); \\n int get_length(const char *); \\n int ami_at_end(const char *, const char *);
int main() {  \\n char fs[SIZE], ss[SIZE]; \\n printf("%d\n", ami_at_end(gets(fs), gets(ss)));  \\n return 0; \\n } //end of main
int get_length(const char *s) { \\n int i = 0; \\n for (; s[i] != '\0'; i++); \\n return i; \\n } //end of get_length
int ami_at_end(const char *first_string, const char *second_string){
    while(*first_string!='\0')
      ++first_string;
    --first_string;
    while(*second_string!='\0')
        ++second_string;
    --second_string;
    int len = get_length(second_string); \\n while(len > 0) {
        if(*second_string == *first_string) { \\n  --first_string; \\n --second_string; \\n  --len; \\n }
        else return -1;
     } //end of while \\n return 1; \\n }
