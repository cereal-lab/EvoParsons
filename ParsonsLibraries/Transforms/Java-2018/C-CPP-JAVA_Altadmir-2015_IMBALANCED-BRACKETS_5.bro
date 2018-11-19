Author = EvoParsons Team

Title =  Inserting the condition of an if statement within curly brackets instead of parentheses.

Description = Altadmir, 2015- SIGCSE, “37 Million Compilations: Investigating Novice Programming Mistakes in Large-Scale Student Data”
For example: if {a == b} ...

FragmentSelection=^(\s*)(?!.*?(printf|\/\/))(.*)(\()(.*)(\))(.*)$



Transform=$1$2$3{$5}$7
