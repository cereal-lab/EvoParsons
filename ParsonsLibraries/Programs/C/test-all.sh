#!/bin/bash

for file in str_how_many  str_reverse str_mix str_cmp matrix_is_sorted str_palindrome str_all_unique_char str_is_anagram str_is_substring is_correctly_merged str_concat str_compare_delet str_word_count str_amI_end  is_uniq_char_bigohn insertion_sort is_uniq_char_bignsqr is_uniq_char_bigohn_less_space str_remove_duplicate str_remove_duplicate_buffer str_is_anagram_bysort selection_sort replace_space binary_search_iterative binary_search_recursion linked_list linked_list_insert linked_list_delete linked_list_minimum linked_list_check_duplicate
do
    gcc -o ${file}.exe testlib.c ${file}_tests.c 
    nbFails=`./${file}.exe | egrep -v "^\[Test\]\s+\#\s+[0-9]+\s+-->\s+OK.*$"|wc -l`
    if [ ${nbFails} -eq 0 ]
    then 
	echo "${file} --> Shiny"
	if [ -e ${file}.bro ]
	then
	    rm ${file}.bro
	fi
	cp ${file}.hdr ${file}.bro
	cat ${file}.c >> ${file}.bro
    else
	echo "========= ${file} =========="
	./${file}.exe
	echo "========= end =========="
    fi
done

rm *.exe
