struct linked_item {
   int data; struct node* next;
 };
typedef struct linked_item node;
node* head = NULL;
void insert(int found, int new_item) {
   node *new_node, *tmp = head;
   while(tmp) {
      if (tmp->data == found) {
         new_node = (node*) malloc(sizeof(node*));
         new_node->data = new_item;  new_node->next = tmp->next;
         tmp->next = (struct node*)new_node; 
         break;}
      tmp = (node*)tmp->next;}
}

