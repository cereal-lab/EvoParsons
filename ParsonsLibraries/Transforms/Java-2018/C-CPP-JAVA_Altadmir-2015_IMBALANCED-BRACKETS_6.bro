Author = EvoParsons Team

Title =  Unbalanced parentheses, curly or square brackets and quotation marks, or using these di
erent symbols interchangeably.


Description = Altadmir, 2015- SIGCSE, “37 Million Compilations: Investigating Novice Programming Mistakes in Large-Scale Student Data”
Forgetting parentheses after a method call.
For example: myObject.toString;

FragmentSelection=^(\s*)(?!.*?(printf|\/\/))(.*)(\()(\s*)(\))(.*)$



Transform=$1$2$3$5$7
