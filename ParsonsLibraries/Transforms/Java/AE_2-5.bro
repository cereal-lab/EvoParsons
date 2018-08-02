Author = EvoParsons Team

Title= post-increment to pre-increment

Description= when we assign value to a variable in right hand side.

FragmentSelection=^(\s*\w*\s*\w+\s*=\s*)(\w+\s*)\+\+(.*)$
                

Transform=$1\+\+$2$3
