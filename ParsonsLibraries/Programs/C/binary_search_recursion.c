int binary_search_recursion(int* A, int low, int high, int val) {
   int mid = (low + high)/2;
   if (A[mid] == val) return 1;
   if(low >  high)     return 0; 
   if (val > A[mid])  return  binary_search_recursion(A, mid+1, high, val);
   else               return  binary_search_recursion(A, low, mid-1, val);
 }
