/*[#author#]
EvoParsons Team

[#language#]
C

[#title#]
Stack - Array Implementation

[#description#]
The  code snippet implements push, pop and display operation of stack.
This is an array based Implementation of the stack.
Push function inserts an item at the top of the stack. It prints
"Stack overflow error", if an attempt is taken to push in a full stack.
Pop returns the top element from stack and decreases top value by one.
Any attempt to pop an element from an empty stack causes "Stack underflow error."
Function display prints the elements of the stack starting from top. The program implements
the functions in the following order: push, pop and display.

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
void display() {  \\nint i, *p = stack;  \\nfor (i = top; i >= 0; i--)  \\nprintf("%d \n", *(stack + i));  \\n}
