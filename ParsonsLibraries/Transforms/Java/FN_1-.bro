Author = EvoParsons Team

Title= Functions/Methods (FN)

Description= Removing the first parameter from a function/method prototype

FragmentSelection=^(?!.*(for|if|else\s*if|while|=))(.*)\(\s*(double|float|int|char|boolean|short|long)\s*\W*\w+\s*\s*,*\s*(.*)$

Transform=$1$2\($4