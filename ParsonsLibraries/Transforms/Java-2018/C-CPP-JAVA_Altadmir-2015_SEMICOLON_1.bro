Author = EvoParsons Team

Title =  Incorrect semicolon at the end of a method header.

Description = Altadmir, 2015- SIGCSE, “37 Million Compilations: Investigating Novice Programming Mistakes in Large-Scale Student Data”
public void foo();
{
...
}


FragmentSelection=^(\s*)(?!.*?(for|while|if|else|System|\/\/|return|=))(.*)(\(\s*.*\))(\s*\\\\n\s*\{)$



Transform=$1$2$3$4;$5
