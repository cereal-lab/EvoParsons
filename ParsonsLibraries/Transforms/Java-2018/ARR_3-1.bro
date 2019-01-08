Author = EvoParsons Team

Title=  ARRY 

Description= Type Mismatch when defining an array (removing first [] in left hand side)

FragmentSelection=^(\s*)(?!.*?(System))(\w+\s*\w+\s*)(\[\s*\])(.*)$

Transform=$1$2$3$5