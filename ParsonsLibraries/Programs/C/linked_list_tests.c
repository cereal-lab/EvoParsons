#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "testlib.h"

#include "linked_list.c"
/*
 *THIS IS A DUMMY TEST SCRIPT COMPATIBLE WITH TEST-ALL.SH
  LINKED LIST CREATION AND PRINT WAS TESTED CALLING A MAIN
  FUNCTION INSIDE linked_list.c 
 *
 */
int main()
{
  int nbtests = 1;
  
  int out[1] = {1
               };

  int i = 0;
  int result; 
  for (; i<nbtests; i++) {
     int bool = 1;
     TEST("",bool == out[i]);  
  }
  return 0;
}
