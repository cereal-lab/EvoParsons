#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "testlib.h"

#include "str_is_anagram_bysort.c"

int main()
{
  int nbtests = 4 ;
  char *input_string1[4];
  char *input_string2[4];
  
    //returns 1 for non-duplicate
  input_string1[0] = strdup("AB");       // empty string
  input_string1[1] = strdup("siobhan donaghy");      // smallest odd length 
  input_string1[2] = strdup("12");     // smallest even length
  input_string1[3] = strdup("12345");  // another odd
  
  input_string2[0] = strdup("BA"); // another even
  input_string2[1] = strdup("shanghai nobody"); 
  input_string2[2] = strdup("121"); 
  input_string2[3] = strdup("1223"); 
  

  int out[4] = {
                1, 1, 0, 0
               };

  int i = 0;
  int result; 
  for (; i<nbtests; i++) {
    int bool  = is_anagram_bysort(input_string1[i],input_string2[i]);
   // printf("Test Result : %d \n", bool);
    TEST("",bool == out[i]);  
  }
  return 0;
}
