#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "testlib.h"

#include "str_remove_duplicate.c"

int main()
{
  int nbtests = 8;
  char *input_string[8];
  
    //returns 1 for non-duplicate
  input_string[0] = "";       // empty string
  input_string[1] = "1";      // smallest odd length 
  input_string[2] = "12";     // smallest even length
  input_string[3] = "12345";  // another odd
  input_string[4] = "123456"; // another even

  //returns 0 for duplicate
  input_string[5] = "11"; 
  input_string[6] = "121"; 
  input_string[7] = "1223"; 
  
  char* output_string[8];
 
  output_string[0] = "";       // empty string
  output_string[1] = "1";      // smallest odd length 
  output_string[2] = "12";     // smallest even length
  output_string[3] = "12345";  // another odd
  output_string[4] = "123456"; // another even

  //returns 0 for duplicate
  output_string[5] = "1"; 
  output_string[6] = "12"; 
  output_string[7] = "123"; 
 

 
  int out[8] = {
                1, 1, 1, 1,
                1, 0, 0, 0
               };

  int i = 0;
  int result; 

  for(i = 0; i < nbtests ; i++){
    output_string[i] = remove_duplicate(input_string[i]); 
    TEST(input_string[i],strcmp(input_string[i],output_string[i]));
  }
  return 0;
}
