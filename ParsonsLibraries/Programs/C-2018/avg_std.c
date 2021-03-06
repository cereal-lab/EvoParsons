/*[#author#]
EvoParsons Team

[#language#]
C

[#title#]
Average and Standard Deviation

[#description#]
This program calculates average and standard deviation of some given numbers.
To calculate average, we sum all the given numbers and divide that sum by the
total number given. We need average to measure standard deviation. Lets assume
we have total N numbers and their average is xbar. Standard deviation can be formulated
as SQRT(SUM_i(x_i - xbar)^2/(N-1)), where i iterates for all the given numbers.
Function main() calls populate_array then display the array, then it prints average and standard deviations.
Please implement the functions in the following order; main, populate_array, get_standard_deviation, get_average, display.

[#source#]
*/

#include<stdio.h> \\n #include<math.h> \\n #define SIZE 10 \\n double get_average(int *); \\n double get_standard_deviation(int *);
void display(int *); \\n void populate_array(int *);
int main(){ \\n int data[SIZE] = {0}; \\n populate_array(data); \\n display(data); \\n printf("average: %f, standard deviation: %f \n", get_average(data), get_standard_deviation(data));  \\n return 0;  \\n }//end of main
void populate_array(int * data) { \\n int i = 0; \\n for (; i < SIZE;) { \\n scanf("%d", (data + i++)); \\n} \\n }//end of populate_array
double get_standard_deviation(int * data) { \\n int k = 0; \\n double x_minus_xbar = 0, xbar = get_average(data);
  while(k < SIZE) { \\n x_minus_xbar = pow(fabs(*(data + k++) - xbar), 2.0); \\n }
  return sqrt(x_minus_xbar/(SIZE-1)); \\n } //end of get_standard_deviation
double get_average (int * data) { \\n double sum = 0; \\n int j = 0;
   for (; j < SIZE; j++) { \\n sum += data[j]; \\n } \\n return sum/SIZE; \\n }//end of get_average
void display(int * values) { \\n int j = 0; \\n for (; j < SIZE; j++) { \\n printf("arr[%d]: [%d]\n", j, values[j]); \\n } \\n }//end of display
