Author = EvoParsons Team

Title= pre-increment to post-increment

Description= when we assign value to a variable using a single term in right hand side.

FragmentSelection=^(\s*\w*\s*\w+\s*=\s*)\+\+(\s*\w+\s*)(.*)$

Transform=$1$2\+\+$3
