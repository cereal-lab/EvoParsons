Author = EvoParsons Team

Title =  removing & from scanf

Description = Own experience
scanf(“%d”, &vb); into scanf(“%d”, vb);



FragmentSelection=^(\s*)(scanf)(.*)\&(.*)$

Transform=$1$2$3$4
