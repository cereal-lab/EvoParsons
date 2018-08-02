struct linked_item {
   int data; struct node* next;};
typedef struct linked_item node;
node *head = NULL;
void delete(int data) {
   node *prev, *tmp;
   tmp = prev = head;
   while(tmp) { 
      if (tmp->data == data) {
         if((tmp == head)){ 
            head = (node*)tmp->next;
            free(tmp);}
         else if ((tmp->next == NULL) && (tmp != head)) {
           prev->next = NULL;
           free(tmp);}
         else {
            prev->next = tmp->next; 
            free(tmp);}}
      prev = tmp;
      tmp = (node*)tmp->next;}
}

