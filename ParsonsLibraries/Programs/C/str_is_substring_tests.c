#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "testlib.h"
#include "str_is_substring.c"

int main()
{
  int nbtests = 8;
  char *first_string[8];
  char *second_string[8];
  
  first_string[0] = "!!!,";
  first_string[1] = "this is a , test";
  first_string[2] = "an apple";
  first_string[3] = "The Great Oaks";
  first_string[4] = "Netizen World"; 
  first_string[5] = "Time to Go"; 
  first_string[6] = "May I come In Sir"; 
  first_string[7] = "Sure, most welcome"; 
  
  second_string[0] = "!!,";
  second_string[1] = "a , test";
  second_string[2] = "apple";
  second_string[3] = "Great";
  second_string[4] = "Netzen World";
  second_string[5] = "Timeto Go"; 
  second_string[6] = "com in Sir"; 
  second_string[7] = "Sure, mos";   

  int out[8] = {
                1, 1, 1, 1, 
                0, 0, 0, 1
               };

  int i = 0;
  int result; 

  for(i = 0; i < nbtests ; i++){
    result = is_substring(first_string[i], second_string[i]); 
    TEST("",result == out[i]);
  }
  return 0;
}
