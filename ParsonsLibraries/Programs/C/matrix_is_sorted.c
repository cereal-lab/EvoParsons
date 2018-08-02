int matrix_is_sorted(int nrows, int ncolumns, int matrix[nrows][ncolumns]){
  int i,j; 
  for(i=0 ; i < nrows ; i++){ 
    if(i > 0){ //Write this line before line A
      if(matrix[i-1][ncolumns-1] > matrix[i][0]){
	return 0;}}
    for(j=0; j < ncolumns -1 ; j++){ //This is line A
      if(matrix[i][j] > matrix[i][j+1])
	return 0;}}
  return 1;
}
