#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "testlib.h"

#include "int_to_str.c"

int main()
{
  int nbtests = 5;

  int  in1[5] = {1012, -10198, 200, 120, 500};

  char*  in2[5];
  in2[0]  = strdup("1012");
  in2[1] = strdup("-10198");
  in2[2] = strdup("200");
  in2[3] = strdup("121");
  in2[4] = strdup("448");

  int out[5] = {1, 1, 1, 0, 0};  
    
  int i = 0;
  int result; 

  for(i = 0; i < nbtests - 2 ; i++){
    
    itoa(in1[i], in2[i]); 
    int bool1 = (strcmp(in2[i], in2[i]) == 0);
    TEST("",bool1 == out[i]);
  }
  for(i = 3; i < nbtests  ; i++){
    
    itoa(in1[i], in2[i]); 
    int bool1 = (strcmp(in2[i], in2[i]) != 0);
    TEST("",bool1 == out[i]);
  }
   
  return 0;
}
