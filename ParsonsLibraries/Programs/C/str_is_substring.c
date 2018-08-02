int is_substring(char *main_str, char *ptrn_str){
        int i,j,flag, count_main = 0, count_ptrn = 0;
        while(main_str[count_main] != '\0') //Write this line before line A
           count_main++; 
        while(ptrn_str[count_ptrn] != '\0') //This line is marked as line A
           count_ptrn++;   
        for (i = 0; i <= count_main - count_ptrn; i++){
           for(j = i; j < i + count_ptrn; j++) {
              flag = 1;
              if (main_str[j] != ptrn_str[j-i]) {
                 flag = 0;
                 break;}} 
          if (flag == 1) break;}
         return flag;
}

      
