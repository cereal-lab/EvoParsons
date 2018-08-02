int isUniqueV4(const char *s) {
   int my_ascii_value, my_bit_position, self_checker = 0, i = 0, j = 0;
   for(; s[i]; i++);
   for(; j < i+1; j++){
      my_ascii_value = s[j] - 'a'; 
      my_bit_position = 1 << my_ascii_value;
      if (self_checker & my_bit_position > 0) return 0;
      self_checker |= my_bit_position;  }
   return 1;
}
