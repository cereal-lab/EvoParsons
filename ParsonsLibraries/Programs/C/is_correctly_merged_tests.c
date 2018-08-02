#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "testlib.h"
#include "is_correctly_merged.c"

int main()
{
  int nbtests = 1;
  int  left[2] = {5, 6}; //5,6->tested // 20,1,80 ->tested
  int  right[3] = {1, 2, 4};   // 1, 2, 4 ->tested // 1, 2, 4 ->tested
  int  sorted[5];
 
   int out[1] = {
                1
               };

  int i = 0;
  int result; 

  for(i = 0; i < nbtests ; i++){
    result = merge_and_check(left, right, 2, 3, sorted); 
    TEST("",result == out[i]);
  }
  return 0;
}
