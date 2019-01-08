Author = EvoParsons Team

Title=  ARRY 

Description= Index out of bound - step down index to -1 when defining it

FragmentSelection=^(.+?\=)(.+?\[)(\s*\w*\d*\s*)(\s*\].*)$
Transform=$1$2-1$4
