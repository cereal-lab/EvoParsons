#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "testlib.h"
#include "str_is_anagram.c"

int main()
{
  int nbtests = 8;
  char *first_string[8];
  char *second_string[8];
  
  first_string[0] = "catalogue";
  first_string[1] = "decimate";
  first_string[2] = "ocean";
  first_string[3] = "englander";
  first_string[4] = "bedroom "; 
  first_string[5] = "elbow"; 
  first_string[6] = "thistestconsiders"; 
  first_string[7] = "book"; 
  
  second_string[0] = "coagulate";
  second_string[1] = "medicate";
  second_string[2] = "canoe";
  second_string[3] = "greenland";
  second_string[4] = "boredomst"; 
  second_string[5] = "belowes"; 
  second_string[6] = "onlysmallletter"; 
  second_string[7] = "koobte";   

  int out[8] = {
                1, 1, 1, 1, 
                0, 0, 0, 0
               };

  int i = 0;
  int result; 

  for(i = 0; i < nbtests ; i++){
    result = is_anagram(first_string[i], second_string[i]); 
    TEST("",result == out[i]);
  }
  return 0;
}
