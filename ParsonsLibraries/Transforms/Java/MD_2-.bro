Author = EvoParsons Team

Title= Mismatching float or double data type (Mismatches - data types : MD)

Description=   when defining variable or parameter, change double/float to int


FragmentSelection=^(.*?)(double|float)(.*)$

Transform=$1int$3

