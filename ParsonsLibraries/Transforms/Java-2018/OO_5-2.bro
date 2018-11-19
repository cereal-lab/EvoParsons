Author = EvoParsons Team

Title= zero to one  (Off by one bugs : OO)

Description=    when not inside loop and control statements

FragmentSelection=^(?!.*?(if|else\s*if|while|for))(.*?=\s*)0(.*)$

Transform=$1$21$3

