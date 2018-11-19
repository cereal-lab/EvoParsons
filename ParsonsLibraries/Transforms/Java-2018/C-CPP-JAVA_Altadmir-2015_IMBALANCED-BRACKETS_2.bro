Author = EvoParsons Team

Title =  nUnbalanced parentheses, curly or square brackets and quotation marks, or using these di
erent symbols interchangeably.


Description = Altadmir, 2015- SIGCSE, “37 Million Compilations: Investigating Novice Programming Mistakes in Large-Scale Student Data”



FragmentSelection=^(\s*)(?!.*?(printf|\/\/))(.*)(\])(.*)$




Transform=$1$2$3)$5
