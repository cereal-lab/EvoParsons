struct linked_item {
   int data; struct node* next;};
typedef struct linked_item node;
node *head = NULL;
int is_there_duplicate() {
  node *tmp = head, *curr;
  if (head == NULL) return 0;
  else if (head->next == NULL) return 0;
  else {
     curr = (node*)head->next;
     while(curr) {
        while(tmp != curr) {
          if (tmp->data == curr->data) return 1;
          tmp = (node*)tmp->next;}
        tmp = head;
        curr = (node*) curr->next;}}
return 0;
}



