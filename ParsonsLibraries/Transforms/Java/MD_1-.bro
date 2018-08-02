Author = EvoParsons Team

Title= Mismatching int data type (Mismatches - data types : MD)

Description=   when defining variable or parameter, change char to boolean


FragmentSelection=^(.*?)(char)(.*)$

Transform=$1boolean$3

