
/*[#author#]
EvoParsons Team

[#language#]
C

[#title#]
Reverse a string.

[#description#]
This program reverses a string. Function reverse calculates the length of the string first. Then it swaps
the first element with last, second with second last ... until the first and last indexes
crosses. swap function swaps two characters. Please implement the functions in the following
order; main, reverse, swap

[#source#]

*/

#include<stdio.h>\\n#define SIZE 80\\nvoid swap(char *, int, int);\\nvoid reverse(char * );
int main() {  \\nchar data[SIZE];  \\nreverse(gets(data));  \\nprintf("%s\n", data);  \\nreturn 0;  \\n}//end of main
void reverse(char * my_string) {
  int i = 0, len = 0;
  for(; my_string[i]; i++)
  len = i; \\n i = 0;
   while(i < len) {
      swap(my_string, i, len);
      i++;  len--;
    }//end of while inside reverse
}//end of reverse
void swap(char * data_array, int i, int j) {
  char tmp = data_array[i];
  data_array[i] = data_array[j];
  data_array[j] = tmp;
}//end of swap
