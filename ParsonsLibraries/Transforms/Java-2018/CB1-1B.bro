Author = EvoParsons Team

Title= Caceffo Misconceptions adaptation (Misconception ID - B)

Description=   Local	variables	accessed	outside	their	scope (B1)

FragmentSelection=^(\s*)(\w+\W*\w*\s*=)(\s*\w+\W*\w*\s*) (\+|\-|\*|\%|\\) (\s*\d*\w*\W*\w*\s*)(;\s*)$



Transform=$1$2$3$4$5 \+ c $6

