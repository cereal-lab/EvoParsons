#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "testlib.h"

#include "str_compare_delet.c"

int main()
{
  int nbtests = 5;

  char * in1[5]; 
  in1[0] = strdup("x");
  in1[1] = strdup("xxbb");
  in1[2] = strdup("xbbxx");
  in1[3] = strdup("xxxxbb2");
  in1[4] = strdup("xxxxxbc");
  
  char * in2[5]; 
  in2[0] = strdup("b");
  in2[1] = strdup("bb");
  in2[2] = strdup("bbb1");
  in2[3] = strdup("bbbb");
  in2[4] = strdup("bbbbxb");

  char * out1[5];
  out1[0] = strdup("x");
  out1[1] = strdup("xx");
  out1[2] = strdup("xxx");
  out1[3] = strdup("xxxx2");
  out1[4] = strdup("c");

  

  int i = 0;
  for(i = 0; i < nbtests ; i++){
    compare_and_delete(in1[i], in2[i]);
    int bool1=(strcmp(out1[i], in1[i])==0);
    TEST("",bool1);
  }
  return 0;
}
