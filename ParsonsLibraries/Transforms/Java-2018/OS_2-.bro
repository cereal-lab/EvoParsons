Author = EvoParsons Team

Title= Adding semicolons(Other Syntax Errors : OS)

Description=  after control (except else) and loop (without do while) but before the closing parenthesis  

FragmentSelection=^(\s*)(if|while|for|else if)(.*?\(.*)(\)\s*\W*)$

Transform=$1$2$3;$4

