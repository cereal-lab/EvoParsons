int unique(const char  *input_string) {
  int i=0, j=0, length=0, result=1;
  if (input_string == NULL) 
    return result; 
  while(input_string[i]) i++;
  length = i - 1;
  for(i = length; i > 0; i--)
    for (j = i-1; j >= 0; j--)
      if (input_string[i] == input_string[j]) 
	return !result;
  return result;  
}






