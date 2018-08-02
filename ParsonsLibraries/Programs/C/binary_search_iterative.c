int binary_search_iterative(int* A, int item){
   int low = 0, mid , high = 0;
   for(; A[high]; high++);
   while(low < high) {
      mid = (low + high)/ 2;
      if (A[mid] == item) return 1;
      else if (item > A[mid]) low = mid + 1;
      else high = mid - 1; }
return 0;
}
