#include <stdio.h>
#include <stdlib.h>
#include "testlib.h"

#include "str_how_many.c"

void test_str_how_many(){
    int nbtests = 3;
    char * strings[] = { "hello", "there is a fox on the hill", "yet another test"};
    int numbers_of_e[] = { 1, 3, 3};;

    int i = 0;
    for(i = 0; i < nbtests ; i++){
        TEST(strings[i], numbers_of_e[i] == str_how_many('e', strings[i]));
    }
}

int main()
{
    test_str_how_many();
    return 0;
}
