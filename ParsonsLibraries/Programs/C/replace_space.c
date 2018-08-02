char* replace_space_by_percent20(char *s) {
   int i = 0, no_space = 0, len = 0, new_len = 0;
   for (; s[len]; len++)
      if (s[len]== ' ') 
          no_space++;
   new_len = len + no_space * 2;
   s[new_len] = '\0';
   for ( i = len - 1; i >= 0; i--) {
     if (s[i] == ' '){
          s[new_len - 1] = '0';  s[new_len - 2] = '2';   s[new_len - 3] = '%';
          new_len -= 3;}
     else {
         s[new_len - 1] = s[i];
         new_len--;}}
return s;
}
