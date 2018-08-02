char* selection_sort(char* s) {
   int  len = 0, i = 0, j = 0, index = 0; char tmp, min;
   for (; s[len]; len++);   
   for (; i < len - 1; i++) {
      min = s[i]; index = i;
      for (j = i + 1; j < len ; j++) { 
         if (min > s[j]) { min = s[j]; index = j; }}
      tmp = s[i];
      s[i] = s[index];
      s[index] = tmp;}
 return s;
}
