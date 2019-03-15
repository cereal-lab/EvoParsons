/*[#author#]
EvoParsons Team

[#language#]
C

[#title#]
Multiplication of largest and the second largest element

[#description#]
This program finds the maximum and second maximum elements in an array. Then it simply prints
the multiplication of those two numbers. Function maximum takes an array of integers and returns maximum
element of that array. get_second_max takes array of integers and the maximum element.
Then it creates another array of integers where the maximum of original array is excluded.
get_second_max calls maximum to get the second largest element. Please implement the functions
in the following order; main, get_second_max, maximum

[#source#]
*/


#include<stdio.h> \\n#define SIZE 5\\nint maximum (int * );\\nint get_second_max(int *, int);
int main() {  \\nint data[SIZE] = {-20, -30, 100, 1, 2};  \\nint first_max = maximum(data);  \\nint second_max = get_second_max(data, first_max);  \\nprintf("%d\n", first_max*second_max);  \\nreturn 0;  \\n}//end of main
int  get_second_max(int * actual_data, int max) {  \\nint i = 0, j = 0;  \\nstatic int r_data[SIZE - 1];  \\nwhile(i < SIZE) {  \\nif (actual_data[i] != max) {  \\nr_data[j++] = actual_data[i]; \\n}  \\ni++;  \\n}//end of while insed get_second_max  \\nreturn maximum(r_data);  \\n}//end of get_second_max
int maximum(int * data) {
  int i = 1, max = *data;
  for (; i < SIZE; i++) {
    if (data[i] > max) {
       max = data[i]; } //end of if statement \\n } //end of for loop
  return max; \\n } //end of maximum
