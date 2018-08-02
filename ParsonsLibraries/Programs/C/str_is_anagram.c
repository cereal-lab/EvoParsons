int is_anagram(char *first, char *second) {
  int c = 0, counter_first[26] = {0}, counter_second[26] = {0};
  for(c=0 ; first[c] ; c++)   // Do this before line marked [x]
    counter_first[first[c] - 'a']++; 
  for(c=0 ; second[c] ; c++) // this line is the one marked [x]
    counter_second[second[c] - 'a']++;
  for (c = 0; c < 26; c++){
    if (counter_first[c] != counter_second[c])
      return 0;}
  return 1;   
}
