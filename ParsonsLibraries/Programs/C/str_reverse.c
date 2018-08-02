char* str_reverse(char * string){
  char* start, *end; 
  start = end = string;//Place this line before line A 
  if(string){  // This is line A
    while(*end++);
    end--; end--;
    while(start < end){
      char tmp = *start;
      *start = *end;
      *end = tmp;
      start++; end--;}}
  return string;
}
