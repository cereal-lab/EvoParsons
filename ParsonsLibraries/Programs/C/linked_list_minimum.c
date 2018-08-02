struct linked_item {
   int data; struct node* next;};
typedef struct linked_item node;
node *head = NULL;
int minimum() {
   node *tmp = head; int min = head->data;
   while(tmp) {
      if (min > tmp->data) 
        min = tmp->data;
      tmp = (node*) tmp->next;}
return min;
}

