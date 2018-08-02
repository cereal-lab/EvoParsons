int str_how_many(const char letter, const char * string){
    int n = 0; //Write this line before line A
    if(string){ // This is line A
        while(*string){
            if (*string == letter){
                n++;}
            string++;}}
    return n;
}
