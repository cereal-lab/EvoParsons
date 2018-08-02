#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "testlib.h"

#include "matrix_is_sorted.c"

int main()
{
  int nbtests = 6;

  int input[6][3][5] = { 
    { {1, 2, 3, 4, 5}, {21, 22, 23, 24, 25}, {51, 52, 53, 54, 55} }, 
    { {1, 2, 3, 4, 21}, {5, 22, 23, 24, 25}, {51, 52, 53, 54, 55} }, 
    { {1, 2, 3, 4, 5}, {21, 22, 23, 24, 51}, {25, 52, 53, 54, 55} }, 
    { {1, 3, 2, 4, 5}, {21, 22, 23, 24, 25}, {51, 52, 53, 54, 55} }, 
    { {1, 2, 3, 4, 5}, {21, 23, 22, 24, 25}, {51, 52, 53, 54, 55} }, 
    { {1, 2, 3, 4, 5}, {21, 22, 23, 24, 25}, {51, 53, 52, 54, 55} }, 
  };

  int out[] = {1,0,0,0,0,0};

  int i = 0;
  int result; 

  for(i = 0; i < nbtests ; i++){
    result = matrix_is_sorted(3, 5, input[i]); 
    TEST("",result == out[i]);
  }
  return 0;
}
