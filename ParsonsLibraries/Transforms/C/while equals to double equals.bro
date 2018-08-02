Author = Paul Burton

Title=While loop swap = for ==

Description=Modify a while loop so that it considers == rather than =
FragmentSelection=^(\s*while\s*\(.*)=(.*\).*)$

Transform=$1==$2
