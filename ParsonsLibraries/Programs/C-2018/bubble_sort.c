/*[#author#]
EvoParsons Team

[#language#]
C

[#title#]
Bubble Sort

[#description#]
This program sorts an array in ascending order using bubble sort.
Function populate_array takes integer input from user and populates
an array. swap function swaps the value of two elements in an array.
display shows the contents of the array. sort function does the bubble
sort. It uses two loops. The outer loop iterates the array from start
to end. For each iteration of outer loop, the inner loop compares each
adjacent elements and make their order in ascending. So, the inner loop
places the largest element at the end of the array, then the second
largest and so on. Please implement the functions in the following order:
main, populate_array, swap, sort, display

[#source#]
*/

#include<stdio.h>\\n#define SIZE 5\\nvoid populate_array(int []);\\nvoid sort(int *);\\nvoid display(int *);\\nvoid swap(int *, int, int);
int main() {  \\nint arr[SIZE] = {0};  \\npopulate_array(arr);  \\nsort(arr);  \\nprintf("After Sorting\n");  \\ndisplay(arr);  \\nreturn 0;  \\n}//end of main
void populate_array(int * data) {  \\nint i = 0;  \\nfor (; i < SIZE;) { \\nscanf("%d", &data[i++]); \\n} \\n}//end of populate_array
void swap(int * data_array, int i, int j) {
  int tmp = data_array[i];
  data_array[i] = data_array[j];
  data_array[j] = tmp;
}//end of swap
void sort(int * data_array) {
   int k, l;
   for (k = 0; k < SIZE; k++) {
      for(l = 0; l < SIZE - 1 - k; l++) {
         if (*(data_array + l) > *(data_array + l + 1)) { \\n  swap(data_array, l, l + 1); \\n } \\n } //end of inner for loop \\n } // end of outer for loop \\n }//end of sort
void display(int * values) {  \\nint j = 0;  \\nfor (; j < SIZE; j++)  { \\nprintf("arr[%d]: [%d]\n", j, values[j]); \\n} \\n}//end of display
