Author = EvoParsons Team

Title=  1 to 0   (Off by one bugs : OO)

Description=   when used inside loop and control statements

FragmentSelection=^(\s*)(if|while|for|else|do\s*\{\s*.*\}\s*while)(.*?)1(.*)$
Transform=$1$2$30$4


