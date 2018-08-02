[#author#]
Alessio Gaspar

[#language#]
java 

[#title#]
Fibonnachi Numbers: Memoization-based Version

[#description#]
This program consists of two public methods.
We will assume that we have already at our disposal a function <em>fib</em> which takes a positive integer value n and return the nth Fibonnachi number. 
The code below is for the method <em>Fibonnachi</em> which will use a memoization technique to cache results in a global object <em>cache</em> and only
use <em>fib</em> when the result has not yet been cached.

[#source#]
public long fibonnachi(int n){
        Integer f = cache.get(n);
        if(f != null){
            return f;
        }
        f = fib(n);
        cache.put(n, f); 
        return f;
}
