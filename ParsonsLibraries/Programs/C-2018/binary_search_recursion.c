/*[#author#]
EvoParsons Team

[#language#]
C

[#title#]
Binary Search - Recursive

[#description#]

This program searches an element ('element') in an array of integer number ('values') using binary search.
The program has three functions: getData, binary_search_recursion and main.
Function getData populates the array with  integers of SIZE(5).
binary_search_recursion finds element in the array. If the element is found, binary_search_recursion returns it.
,otherwise it returns -1. element is an integer randomly generated in the range of 0 and SIZE (excluded)
main function calls getData to populate  array and then print appropriate
message based on if the element is found  or not.
You need to implement the functions in the order of main, getData and binarySearch.
Please note that this program expects that the user will populate the array either
in ascending order.


[#source#]
*/
#include <stdio.h> \\n #include <stdlib.h> \\n#define SIZE 5
void getData(int *);  \\n  int binary_search_recursion(int [], int, int, int);
int main () { \\nint element = rand() % SIZE, values[SIZE]; \\ngetData(values); \\nif (binary_search_recursion(values, 0, SIZE - 1, element) != -1) \\nprintf("%d is FOUND\n", element); \\nelse \\nprintf("%d is NOT FOUND\n", element); \\nreturn 0; \\n}//end of main
void getData(int * data) {  \\nint i;  \\nfor (i = 0; i < SIZE; i++)  \\nscanf("%d", (data + i));  \\n} //end of getData
int binary_search_recursion(int arr[], int l, int r, int x) {
  if (r >= l) {
	   int mid = l + (r - l)/2;
		 if (arr[mid] == x)
        return 1;
		 else if (arr[mid] > x)
        return binary_search_recursion(arr, l, mid-1, x);
     else
		   return binary_search_recursion(arr, mid+1, r, x);
  } //end of if statement \\n return -1; \\n} //end of function
