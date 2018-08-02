int ami_at_end(char *s,char *t, int len){
        while(*s!='\0') //Place this line before line marked as line [x]
            ++s;
        --s;   
        while(*t!='\0') // This line is marked as line [x]
            ++t;
        --t;
        while(len > 0) {
            if(*t==*s){ 
                --t; --s; --len;}
            else 
                return 0;}
        if( 0 == len) 
            return 1;
    }    
