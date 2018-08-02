Author = EvoParsons Team

Title=  misspelling a variable/Keyword name(Other Syntax Errors : OS)

Description=   capitalize first letter of the keyword char only for variable declaration

FragmentSelection=^(\s*)(char)(\s*\w+\s*\W*\w*\s*;)(.*)$

Transform=$1Char$3$4

