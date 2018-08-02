void str_mix(char* s1, char* s2){
  int n = 0; 
  while( *s1 && *s2){
    if(n++ % 2){ 
      char tmp = *s1; 
      *s1 = *s2; 
      *s2 = tmp;}
    s1++; s2++;}
}
