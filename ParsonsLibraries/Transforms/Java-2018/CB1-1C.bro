Author = EvoParsons Team

Title= Caceffo Misconceptions adaptation (Misconception ID - B)

Description=   Local	variables	accessed	outside	their	scope (B1)

FragmentSelection=^(?!.*?(System))(\s*\w+\s*=)(\s*\d*\w*\W*\s*)(;\s*)$



Transform=$1$2$3 \+ c $4

