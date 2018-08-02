Author = Paul Burton

Title=For loop swap less than to greater than

Description=Modify a for loop initialization part so that it counts greater than rather than less than
FragmentSelection=^(\s*for\s*\(.*;.*)<(.*;.*\).*)$

Transform=$1>$2
