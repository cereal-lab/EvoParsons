#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "testlib.h"

#include "str_mix.c"

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

  char * out1[5];
  out1[0] = strdup("x");
  out1[1] = strdup("xb");
  out1[2] = strdup("xbx");
  out1[3] = strdup("xbxb");
  out1[4] = strdup("xbxbx");

  char * out2[5];
  out2[0] = strdup("b");
  out2[1] = strdup("bx");
  out2[2] = strdup("bxb");
  out2[3] = strdup("bxbx");
  out2[4] = strdup("bxbxb");

  int i = 0;
  for(i = 0; i < nbtests ; i++){
    char buffer[255]; 
    sprintf(buffer, "[%s , %s]", in1[i], in2[i]);
    //printf("[%s , %s]\t-->\t",in1[i], in2[i]);
    str_mix(in1[i], in2[i]);
    //printf("[%s , %s]\n", in1[i], in2[i]);
    int bool1=(strcmp(out1[i], in1[i])==0);
    int bool2=(strcmp(out2[i], in2[i])==0);
    TEST(buffer,bool1 && bool2);
  }
  return 0;
}
