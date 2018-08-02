int str_cmp(const char* s1, const char* s2){
  if(strlen(s1) != strlen(s2))
    return 0;
  else{ 
    while( *s1 && *s2){
      if(*s1++ != *s2++)
	return 0;}
    return 1;}
}
