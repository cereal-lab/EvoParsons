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
A Stack is full when 'top' points to the last index of stack array.
Pop returns the top element from stack and decreases top value by one.
Any attempt to pop an element from an empty stack causes "Stack underflow error."
A Stack is empty when its 'top' points to -1.
The program implements the functions in the following order: push, pop.

[#source#]

*/

#include <stdio.h>\\n#include<stdlib.h>\\n#define SIZE 10\\nint stack[SIZE];\\nint top = -1;
void push(int item) {
	if(top == SIZE - 1) {\\n printf("ERROR: Stack Overflow\n"); \\n exit(0); \\n } //end of if inside push
	else stack[++top] = item; \\n } //end of push
int pop() {
	if(-1 == top) { \\n printf("ERROR: Stack Underflow\n"); \\n exit(0); \\n } //end of if inside pop
	else return stack[top--]; \\n } //end of pop
