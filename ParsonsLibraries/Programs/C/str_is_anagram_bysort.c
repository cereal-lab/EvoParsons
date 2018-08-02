char* insertion(char* A){
   int len = 0, i = 1, j = 0, key = 0;
   for(; A[len]; len++);
   for(; i < len; i++){
      key = A[i]; j = i - 1;
      while (j >= 0 && A[j] > key){
	A[j + 1] = A[j];
        j--;}
      A[j + 1] = key;}
return A;}
int is_anagram_bysort(char* s1, char* s2 ) {
   char *s11, *s21; 
   s11 = insertion(s1); s21 = insertion(s2);
   while(*s11) { 
      if(*s11 != *s21) return 0;
      else { s11++; s21++;}}
 return 1;
}
       
