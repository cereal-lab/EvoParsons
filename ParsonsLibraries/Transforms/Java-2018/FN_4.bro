Author = EvoParsons Team

Title= Functions/Methods (FN)

Description= Making a returnable method (except main) to void : int to void

FragmentSelection=^(?!.*?(main|=|System))(.*?)(int|double|float|char|String)(.*\(.*)$

Transform=$1$2void$4
