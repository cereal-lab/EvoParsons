Author=Alessio Gaspar
Title=Arrow vs. Dot notation for dereferencing pointers
Description=Removes a dot by an arrow in pointer expression
FragmentSelection= ^(.*(\w+|\)))->(\w+.*)$
Transform=$1.$2
