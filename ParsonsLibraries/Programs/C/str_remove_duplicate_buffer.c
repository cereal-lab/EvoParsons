char* remove_duplicate_using_buffer(char* s, int size) {
   int  i = 0, tail = 1, ascii_char[256];
   for (; i <256; i++)
      ascii_char[i] = 0;
   ascii_char[(int)s[0]]= 1;
   for(i = 1; i <size; i++) {
     if(ascii_char[(int)s[i]]== 0) {
        s[tail] = s[i];  //write this line before line A
        ascii_char[(int)s[i]] = 1; // This is line A
        tail++;
     } }
    s[tail]= '\0';    
    return s;
}

