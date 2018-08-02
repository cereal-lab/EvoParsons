[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Add two integers

[#description#]

This function takes two integers and output their arithmetic summation in reverse order.
For example, if two integers are 2359 and 8604 then function will print 36901 

[#source#]

public static void Summation (int [] A, int [] B) {
	int  carry = 0, len = A.length;	
	while (--len >= 0) {
		int sum = (A[len] + B[len]) % 10 + carry;
		carry = (A[len] + B[len]) / 10;
		System.out.print(sum);
		if (len == 0 && carry > 0)
			System.out.println(carry);				
	}
}
