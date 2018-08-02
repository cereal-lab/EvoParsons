void compare_and_delete(char *s1, char *s2){
	int i, j, k = 0;
 	for(i = 0; s1[i]; ++i){
			for(j=0; (s1[i] != s2[j]) && s2[j]; ++j);
			if(s2[j]) s1[k++] = s1[i];}
	s1[k]='\0';
}


