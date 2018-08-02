Author = EvoParsons Team

Title=  misspelling a variable/Keyword name(Other Syntax Errors : OS)

Description=   Removing semicolon between first two variables in a variable declaration

FragmentSelection=^(.*?)(double|int|char|boolean|short|long|float)(\s*\w*\W*),(.*)$

Transform=$1$2$3$4

