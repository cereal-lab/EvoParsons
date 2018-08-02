#include <stdio.h>
#include <stdlib.h>
#include "testlib.h"
#include <string.h>
#include "str_reverse.c"

int main()
{
  int nbtests = 5;
  char * inputs[5]; 
  inputs[0] = strdup("a");
  inputs[1] = strdup("ab");
  inputs[2] = strdup("two");
  inputs[3] = strdup("four");
  inputs[4] = strdup("fives");
  char * outputs[5];
  outputs[0] = strdup("a");
  outputs[1] = strdup("ba");
  outputs[2] = strdup("owt");
  outputs[3] = strdup("ruof");
  outputs[4] = strdup("sevif");

  int i = 0;
  for(i = 0; i < nbtests ; i++)
    TEST(inputs[i], strcmp(outputs[i], str_reverse(inputs[i])) == 0);
  
  return 0;
}
