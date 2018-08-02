#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "testlib.h"

#include "str_remove_duplicate_buffer.c"

int main()
{
  int nbtests = 8;
  char *input_string[8];
  
    //returns 1 for non-duplicate
  input_string[0] = strdup("");       // empty string
  input_string[1] = strdup("1");      // smallest odd length 
  input_string[2] = strdup("12");     // smallest even length
  input_string[3] = strdup("12345");  // another odd
  input_string[4] = strdup("123456"); // another even

  //returns 0 for duplicate
  input_string[5] = strdup("11"); 
  input_string[6] = strdup("121"); 
  input_string[7] = strdup("1223"); 
  
  char* output_string[8];
 
  output_string[0] = strdup("");       // empty string
  output_string[1] = strdup("1");      // smallest odd length 
  output_string[2] = strdup("12");     // smallest even length
  output_string[3] = strdup("12345");  // another odd
  output_string[4] = strdup("123456"); // another even

  //returns 0 for duplicate
  output_string[5] = strdup("1"); 
  output_string[6] = strdup("12"); 
  output_string[7] = strdup("123"); 
 
  int size[8] = { 1, 1, 2, 5,
                  6, 2, 3, 4  
               };
 
  int out[8] = {
                0, 0, 0, 0,
                0, 1, 1, 1
               };

  int i = 0;
  int result; 
  for (; i<nbtests; i++) {
    char* output  = remove_duplicate_using_buffer(input_string[i],size[i]);
    int bool = strcmp(input_string[i],output);
    TEST(input_string[i],bool == out[i]);  
  }
  return 0;
}
