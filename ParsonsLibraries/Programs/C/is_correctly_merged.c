int merge_and_check(int *left, int *right, int len_left, int len_right, int sorted[]){
   int i = 0, j = 0, k = 0; 
   for (i = 0; i < len_left+len_right;) {
     if ( j < len_left && k < len_right) {
     	if (left[j] < right[k]) {
          sorted[i] = left[j];
          j++;}        
      else {
         sorted[i] = right[k];
         k++;}
     i++;}   
   else if (j == len_left) {
      for (; i < len_left + len_right;) {
         sorted[i] = right[k]; 
          k++;  i++;}}
    else {
       for(; i < len_left + len_right;) {
         sorted[i] = left[j];
         j++;  i++;}}} 
  for (i = 0; i < len_left+len_right - 1 ; i++)
      for (j = i; j < len_left + len_right; j++)
        if (sorted[i] > sorted[j])
         return 0;
 return 1;
}

