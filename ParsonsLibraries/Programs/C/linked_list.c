struct linked_item {
   int data; struct node* next;};
typedef struct linked_item node;
node *head = NULL;
node* new_element(int data) {
   node* tmp = (node*)  malloc(sizeof(node));
   tmp->data = data; tmp->next = NULL;
return tmp;
}
node* create_list(int data) {
   node* new_node = new_element(data);
   if (head == NULL) head = new_node;
   else {
      new_node->next = (struct node*) head;
      head = new_node;
   } 
return head; 
}
void print() {
   node *tmp = head;
   while(tmp){
      printf("%d %p \n", tmp->data, tmp->next);
      tmp = (node*)tmp->next;

   }
}



