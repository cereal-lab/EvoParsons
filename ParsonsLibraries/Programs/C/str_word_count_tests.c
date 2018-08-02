#include <stdio.h>
#include <stdlib.h>
#include "testlib.h"

#include "str_word_count.c"

int main()
{
    int nbtests = 4;
    char * strings[] = { "hello", "hello	\n there\n how are you?", "hello	there","abra	   ca dab\n\n\t	ra?"};
    int numbers_of_e[] = { 6, 34, 13, 27};

    int i = 0;
    for(i = 0; i < nbtests ; i++){
        TEST("", numbers_of_e[i] == word_bs_counter(strings[i]));
    }
    return 0;
}
