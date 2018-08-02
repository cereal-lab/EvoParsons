#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "testlib.h"

#include "str_palindrome.c"

int main()
{
  int nbtests = 17;

  char * input[] = {
    "a", 
    "aa",
    "aaa", 
    "aaaa", 
    "aaaaa", 
    
    "ab",
    "abb", 
    "abbb", 
    "abbbb",
    "not a palindrom",

    "radar", 
    "Rats live on no evil star", // ignores cases 
    "Step on no pets", 
    "A nut for a jar of tuna", // ignores spaces
    "Dammit, I'm mad!", // ignores spaces / punctuation

    "Live on time, emit no evil",
    "A Toyota's a Toyota"
  };
  
  int output[] = { 
    1,1,1,1,1,
    0,0,0,0,0,
    1,1,1,1,1,
    1,1
  };

  int i = 0;
  for(i = 0; i < nbtests ; i++){
    TEST(input[i],output[i] == str_palindrome(input[i]));
  }
  return 0;
}
