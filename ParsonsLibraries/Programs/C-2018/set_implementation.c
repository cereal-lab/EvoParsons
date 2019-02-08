/*[#author#]
EvoParsons Team

[#language#]
C

[#title#]
Set Implementation

[#description#]
This program checks if an element is already available in a set of elements
using hash index. The index of an element is determined by taking the
remainder when that element is divided by SIZE of the set (x % SIZE). This
produces hashed_index of the element. Function create_set checks if there is
any element already in that hashed_index. If so, it prints a message "Collision".
otherwise, the element is placed in that position. Please implement main
first then create_set.


[#source#]
*/
#include<stdio.h>\\n#define SIZE 10\\nvoid create_set(int *);
int main() {\\nint set[SIZE] = {0};\\ncreate_set(set);\\nreturn 0;\\n}//end of main
void create_set(int * data) {
    int i = 0, x, hashed_index;
    while (i++ < SIZE) {
      scanf("%d", &x);
      hashed_index = x % SIZE;
      if (data[hashed_index] == 0)
         data[hashed_index] = x;
      else
         printf("Collision\n");
    }//end of while inside create_set
  }//end of create_set
