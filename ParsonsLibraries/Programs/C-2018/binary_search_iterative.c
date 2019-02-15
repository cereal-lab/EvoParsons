/*[#author#]
EvoParsons Team

[#language#]
C

[#title#]
Binary Search - Iterative

[#description#]
This program searches an item ('element') in an array of integer number ('values') using binary search.
The program has three functions: getData, binarySearch and main.
Function getData populates the array with SIZE, where SIZE = 5.
binarySearch looks for the item in the array. If the element is found, binarySearch returns it.
,otherwise it returns -1. element is an integer randomly generated in the range of 0 and SIZE (excluded)
main function calls getData to populate  the array of integer numbers and then prints
messages if the item is found  or not.
You need to implement the functions in the order of main, getData and binarySearch.
Please note that this program expects that the user will input the array in ascending order.

[#source#]
*/

#include <stdio.h> \\n #include <stdlib.h> \\n #define SIZE 5
void getData(int *, int); \\n int binarySearch(const int *, int , int);
int main () { \\n int element = rand() % SIZE; \\n int values[SIZE]; \\n getData(values, SIZE); \\n if (binarySearch(values, element, SIZE) != -1) \\n printf("%d is FOUND\n", element);  \\n else  \\n printf("%d is NOT FOUND\n", element); \\n return 0; \\n }//end of main
void getData(int * data, int len) { \\n int i; \\n  for (i = 0; i < len; i++) \\n scanf("%d", (data + i)); \\n } //end of getData
int binarySearch(const int * data, int x, int len) {
  int l = 0, r = len - 1;
  while (l <= r) {
    int m = l + (r-l)/2;
    if (*(data + m) == x)
        return data[m];
    else if ( *(data + m) < x)
        l = m + 1;
    else
         r = m - 1;
  } //end of while loop \\n return -1;\\n}//end of binarySearch
