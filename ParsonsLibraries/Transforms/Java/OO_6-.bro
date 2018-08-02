Author = EvoParsons Team

Title= plus one to minus one  (Off by one bugs : OO)

Description=    Convert the sign of one from plus to minus


FragmentSelection=^(.*[^+])\+(\s*1\D.*)$

Transform=$1-$2

