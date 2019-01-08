Author = EvoParsons Team

Title=  ARRY 

Description= Miscellaneous (Replacing first [] by () on left hand side)

FragmentSelection=^(\s*)(?!.*?(System))(\w+\s*\w+\s*)(\[\s*\])(.*)$

Transform=$1$2$3\(\)$5