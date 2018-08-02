int str_palindrome(const char const * string){
  const char *end, *start;
  start = end = string;
  while(*end && *(end+1)) end++; 
  if(!*end) return 0;
  do{
    while(!isalpha(*start)) start++;  // Do this before line marked [x]
    while(!isalpha(*end)) end--;      // this line is the one marked [x]
    if(start > end) break; 
    if(tolower(*start) != tolower(*end)) return 0;
    start++; end--; 
  }while(start <= end); 
  return 1;
}
