/*[#author#]
EvoParsons Team

[#language#]
C

[#title#]
Stack - Array Implementation

[#description#]
This is an array based implementation of the stack.
The  code snippet implements two stack operations; push and pop.
Push function inserts an item at the top of the stack. It prints
"Stack overflow error", if an attempt is taken to push an element into a full stack.
Pop returns the top element from stack and decreases top value by one.
Any attempt to pop an element from an empty stack causes "Stack underflow error."
The program implements the functions in the following order: push, pop.

[#source#]

*/

#include <stdio.h>\\n#include<stdlib.h>\\n#define SIZE 10\\nint stack[SIZE];\\nint top = -1;
void push(int item) {
	if(top == SIZE - 1) {
		printf("ERROR: Stack Overflow\n");
        exit(0);
    } //end of if inside push
	else stack[++top] = item;
} //end of push
int pop() {
	if(-1 == top) {
		printf("ERROR: Stack Underflow\n");
        exit(0);
    } //end of if inside pop
	else return stack[top--];
} //end of pop
