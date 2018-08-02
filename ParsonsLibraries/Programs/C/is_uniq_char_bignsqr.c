int isUnique(char *s, int size) {
	int i = 0, j=0;	
	for(; i < size - 2 ; i++)
           for (j = i+1; j < size - 1; j++)  
              if (*(s+i)== *(s+j)) return 0;			
return 1;}
char* insertion(char* a, int size) {
	int i = 1, j = 0, key = 0;
	for (; i < size - 1; i++) {
		key = a[i]; j = i - 1;
		while(a[j] > key && j >= 0) {
			a[j + 1] = a[j];
			j--;}
	a[j+1] = key;}
 return a;}
int isUniqueV3(char* s, int size){
   char* ret = insertion(s,size);
return isUnique(ret, size);
}





