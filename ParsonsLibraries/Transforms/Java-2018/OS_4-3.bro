Author = EvoParsons Team

Title=  misspelling a variable/Keyword name(Other Syntax Errors : OS)

Description=   capitalize first letter of the keyword int only for variable declaration

FragmentSelection=^(\s*)(int)(\s*\w+\s*\W*\w*\s*;\s*)$

Transform=$1Int$3

