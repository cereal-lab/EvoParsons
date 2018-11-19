Author = EvoParsons Team

Title =  Getting greater than or equal/less than or equal


Description = Altadmir, 2015- SIGCSE, “37 Million Compilations: Investigating Novice Programming Mistakes in Large-Scale Student Data”
wrong, i.e. using => or =< instead of >= and <=.
For example: if (a =< b) ...


FragmentSelection=^(\s*)(?!.*?(printf|\/\/))(.*)(<=)(.*)$



Transform=$1$2$3=<$5
