#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "testlib.h"

#include "str_cmp.c"

int main()
{
  int nbtests = 5;

  char * in1[5]; 
  in1[0] = strdup("x");
  in1[1] = strdup("xx");
  in1[2] = strdup("xxx");
  in1[3] = strdup("xxxx");
  in1[4] = strdup("xxxxx");
  
  char * in2[5]; 
  in2[0] = strdup("b");
  in2[1] = strdup("bb");
  in2[2] = strdup("bbb");
  in2[3] = strdup("bbbb");
  in2[4] = strdup("bbbbb");

  int * out[5] = {};

  int i = 0;
  char buffer[255]; 
  int result; 

  for(i = 0; i < nbtests ; i++){
    sprintf(buffer, "[%s , %s]", in1[i], in1[i]);
    result = str_cmp(in1[i], in1[i]); 
    TEST(buffer,result == 1);
  }
  for(i = 0; i < nbtests ; i++){
    sprintf(buffer, "[%s , %s]", in1[i], in2[i]);
    result = str_cmp(in1[i], in2[i]); 
    TEST(buffer,result == 0);
  }  
  return 0;
}
