Author = EvoParsons Team

Title =  Watch those semi colones

Description = C-Pitfalls article
if (xx) int if(xx); (replicating this depends on the next lines. If the next line is any assignment then it works, Otherwise it just adds a no op. In our case, for loop is a good place to use this bug.
ex: for (xxx;xxx;xxx) into for (xxx; xxx; xxx);


FragmentSelection=^(\s*)(for)(.*)(\))$

Transform=$1$2$3$4;
