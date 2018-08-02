#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "testlib.h"

#include "replace_space.c"

int main()
{
  int nbtests = 4 ;
  char *input_string1[4];
  char *input_string2[4];
 //returns 1 for non-duplicate
  input_string1[0] = strdup("A B");       // empty string
  input_string1[1] = strdup("siobhan donaghy");      // smallest odd length 
  input_string1[2] = strdup(" 15 42 ");     // smallest even length
  input_string1[3] = strdup("AXBDY");  // another odd
  
  input_string2[0] = strdup("A%20B"); // another even
  input_string2[1] = strdup("siobhan%20donaghy"); 
  input_string2[2] = strdup("%2015%2042%20"); 
  input_string2[3] = strdup("ABDXYi"); 
  

  int out[4] = {
                0, 0, 0, 1
               };

  int i = 0;
  int result; 
  for (; i<nbtests; i++) {
    
    char* t  = replace_space_by_percent20(input_string1[i]);
   // printf("%s %d \n", t, i);
    int bool = strcmp(t, input_string2[i]);
   // printf("%d %s\n", bool, s);
    TEST("",bool == out[i]);  
  }
  return 0;
}
