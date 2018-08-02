#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "testlib.h"

#include "binary_search_iterative.c"

int main()
{
  int nbtests = 1;
  
    //returns 1 for non-duplicate
  int input_string[5] = {-1, 3, 4, 9, 10};       // empty string
/*  input_string[1] = strdup("1");      // smallest odd length 
  input_string[2] = strdup("12");     // smallest even length
  input_string[3] = strdup("12345");  // another odd
  input_string[4] = strdup("123456"); // another even

  //returns 0 for duplicate
  input_string[5] = strdup("11"); 
  input_string[6] = strdup("121"); 
  input_string[7] = strdup("1223");*/ 

 
  int item[1] = {-10   
               };
 
  int out[1] = {0
               };

  int i = 0;
  int result; 
  for (; i<nbtests; i++) {
    int bool = binary_search_iterative(input_string,item[i]);
    TEST("",bool == out[i]);  
  }
  return 0;
}
