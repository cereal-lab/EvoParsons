#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "testlib.h"

#include "insertion_sort.c"

int main()
{
  //int* in1[1];
  int  nbtests = 1, size = 7; 
  
  int in1[7] = {1,-1,0,1,-20, 200, -100};
 /* in1[1] = strdup("should work hard");
  in1[2] = strdup("here you go");
  in1[3] = strdup("hi there");
  in1[4] = strdup("I am done");
  
  char * in2[5]; 
  in2[0] = strdup("wrong");
  in2[1] = strdup("workhard");
  in2[2] = strdup("go to");
  in2[3] = strdup("there"); */
  //int in2[1];
  int in2[7] = {-100, -20,-1, 0, 1, 1, 200};

  int  out[1] = {1};

  int i = 0, k = 0;
  char buffer[255]; 
  int result = 1; 
  
  for(i = 0; i < nbtests ; i++){
    int *p  = insertion(in1, size);
    for (k = 0; k < size; k++) {
	//printf("%d ", *(p+k));
    	if (*(p+k)!= in2[k]) 
          result = 0;       
    }   
    TEST("",result == out[i]);
  }
 return 0;
}
