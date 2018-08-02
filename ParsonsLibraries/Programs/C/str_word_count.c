int word_bs_counter(char* s) {
    int nl = 0, nw = 0, nc = 0, i = 0, state = 0;
    while (s[i] !='\0') {
        if (s[i] == '\n') //Write this line before line A 
            ++nl;
        if (s[i] == ' ' || s[i] == '\n' || s[i] == '\t') //This is line A
            state = 0;
        else if (state == 0) {
            state = 1;  ++nw;}
        i++; ++nc;}
 return nl + nw + nc;
}


