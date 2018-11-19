Author = EvoParsons Team

Title =  method that has a non-void return type is called and its return value ignored/discarded.


Description = Altadmir, 2015- SIGCSE, “37 Million Compilations: Investigating Novice Programming Mistakes in Large-Scale Student Data”
For example: myObject.toString();


FragmentSelection=^(\s*)(?!.*?(printf|\/\/))(.*?)(=)(.*)(\(.*\)\s*;\s*)$

Transform=$5$6
