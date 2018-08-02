int* insertion(int A[], int size){
   int i = 1, j = 0, key = 0;
   for(; i < size; i++){
      key = A[i]; j = i - 1;
      while (j >= 0 && A[j] > key){
	A[j + 1] = A[j];
        j--; }
      A[j + 1] = key;}
return A;
}
