Author = EvoParsons Team

Title=  ARRY 

Description= Miscellaneous (Replacing first [] by () on right hand side)

FragmentSelection=^(.+?\=)(\s*\w+\s*\w+\s*)(\[)(\s*\d+\s*)(\])(.*)$

Transform=$1$2\($4\)$6