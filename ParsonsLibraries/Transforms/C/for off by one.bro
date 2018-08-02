Author = Paul Burton

Title=Loop starting at 1 instead of 0

Description=Modify a for loop initialization part so that it starts at 1 instead of 0. This will result in a off by one bug.

FragmentSelection=^(\s*for\s*\(\s*\w*\s*\w*\s*\s*=\s*)0(\s*;.*;.*\).*)$

Transform=$11$2
