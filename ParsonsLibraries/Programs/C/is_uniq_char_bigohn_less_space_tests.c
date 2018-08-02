#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "testlib.h"

#include "is_uniq_char_bigohn_less_space.c"

int main()
{
  int nbtests = 8;
  const char *input_string[8];
  
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
    
  int out[8] = {
                1, 1, 1, 1,
                1, 0, 0, 0
               };

  int i = 0;
  int result; 

  for(i = 0; i < nbtests ; i++){
    result = isUniqueV4(input_string[i]); 
    TEST(input_string[i],result == out[i]);
  }
  return 0;
}
