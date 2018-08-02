[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Setting its entire row and column to zero, if an element in a matrix is zero 

[#description#]
If an element in an MxN matrix is 0, its entire row and column is set to 0. The 
algorithm works as follows : keep track of the rows and columns with zeros.
Then make a second pass of the matrix and set a cell to zero if its row or columns
is zero. 

[#source#]
void setZero(int [][] matrix) {
   int [] row = new int [matrix.length]; int []column = new int [matrix[0].length];
   for (int i = 0; i < matrix.length; i++ )
      for (int j = 0; j < matrix[0].length; j++)
         if (matrix[i][j] == 0) { row[i] = 1; column[j] = 1;}
      for (int i = 0; i < matrix.length; i++)
         for (int j = 0; j <matrix[0].length; j++)
	    if (row[i] == 1 || column[j] == 1) matrix[i][j] = 0;
}
