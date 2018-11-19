Author = EvoParsons Team

Title= negative one to positive one (Off by one bugs : OO)

Description=   Convert the sign of one from negative to positive

FragmentSelection=^(.*[^-])\-(\s*1\D.*)$

Transform=$1+$2


