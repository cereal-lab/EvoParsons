Author = EvoParsons Team

Title=  ARRY 

Description= Miscellaneous (Replacing {} by [] on right hand)

FragmentSelection=^(.+?\=)(.*)(\{)(.*)(\})(.*)$

Transform=$1$2\($4\)$6