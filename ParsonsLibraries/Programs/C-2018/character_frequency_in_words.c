/*
[#language#]
C

[#title#]
Character Frequency

[#description#]
This program implements the frequency of a given letter in
any string. Function frequency computes the count
of the "letter" in the "string" and returns it. read_string_input reads
string input from user. Please implement the functions in the following
order; main, read_string_input and frequency.

[#source#]
*/
#include<stdio.h>\\n#define SIZE 80
int frequency(const char, const char *);
char * read_string_input();\\nint main() {  \\nprintf("%d\n", frequency(getchar(), read_string_input()));  \\n}
char * read_string_input() {  \\nstatic char str[SIZE], ch;  \\nint j = 0;  \\nwhile ((ch = getchar()) != '\n')  \\nstr[j++] = ch;  \\nstr[j] = '\0';  \\nreturn str;  \\n} //end of read_string_input
int frequency(const char letter, const char * string){
  int n = 0;
  if(string) {
      while(*string){
        if (*string == letter)
          n++;
        string++;
      } //end of 'while' inside frequency
  } //end of 'if' inside frequency
return n;
}
