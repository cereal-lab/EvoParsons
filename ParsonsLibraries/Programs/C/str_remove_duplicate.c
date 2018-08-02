char* remove_duplicate(char *s) {
   int i = 1, len = 0, j = 0, head = 1;
   for(;s[len];len++);  
   for(; i < len; i++) {
      for (j = 0; j < head; j++) 
         if (s[i] == s[j]) break;       
      if (j == head) {
		s[head] = s[i];
		head++; }}
    s[head] = '\0';  
   return s;
}


