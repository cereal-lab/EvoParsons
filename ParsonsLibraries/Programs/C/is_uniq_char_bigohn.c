int isUnique(const char *s) {
   int ascii_char_set[256], i = 0, j = 0;
   for(; i<256; i++) ascii_char_set[i] = 0;
   i = 0;
   for(; s[i]; i++);
   for(; j < i+1; j++){
     if(ascii_char_set[(int)s[j]] == 1) 
       return 0;
     ascii_char_set[(int)s[j]] = 1;}
   return 1;
}
