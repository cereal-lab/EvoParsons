Author = EvoParsons Team

Title=  misspelling a variable/Keyword name(Other Syntax Errors : OS)

Description=   capitalize first letter of the keyword boolean only for variable declaration

FragmentSelection=^(\s*)(boolean)(\s*\w+\s*\W*\w*\s*;\s*)$

Transform=$1Boolean$3

