#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "testlib.h"

#include "selection_sort.c"

int main()
{
  int nbtests = 4 ;
  char *input_string1[4];
  char *input_string2[4];
 //returns 1 for non-duplicate
  input_string1[0] = strdup("AB");       // empty string
  input_string1[1] = strdup("siobhan donaghy");      // smallest odd length 
  input_string1[2] = strdup("1542");     // smallest even length
  input_string1[3] = strdup("AXBDY");  // another odd
  
  input_string2[0] = strdup("BA"); // another even
  input_string2[1] = strdup("shanghai nobody"); 
  input_string2[2] = strdup("1245"); 
  input_string2[3] = strdup("ABDXY"); 
  

  int out[4] = {
                1, 1, 0, 0
               };

  int i = 0;
  int result; 
  for (; i<nbtests; i++) {
    
    char* t  = selection_sort(input_string1[i]);
   // printf("%s %d \n", t, i);
    int bool = strcmp(t, input_string2[i]);
   // printf("%d %s\n", bool, s);
    TEST("",bool == out[i]);  
  }
  return 0;
}
