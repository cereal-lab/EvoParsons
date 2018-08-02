#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "testlib.h"

#include "str_amI_end.c"

int main()
{
  int nbtests = 5;

  char * in1[5]; 
  in1[0] = strdup("something here goes wrong");
  in1[1] = strdup("should work hard");
  in1[2] = strdup("here you go");
  in1[3] = strdup("hi there");
  in1[4] = strdup("I am done");
  
  char * in2[5]; 
  in2[0] = strdup("wrong");
  in2[1] = strdup("workhard");
  in2[2] = strdup("go to");
  in2[3] = strdup("there");
  in2[4] = strdup("done");

  int  out[5] = {1, 0, 0, 1, 1};

  int i = 0;
  char buffer[255]; 
  int result; 
  for(i = 0; i < nbtests ; i++){
    result = ami_at_end(in1[i], in2[i],strlen(in2[i])); 
    TEST("",result == out[i]);
  }
 return 0;
}
