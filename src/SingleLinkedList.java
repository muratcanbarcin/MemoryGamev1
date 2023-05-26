public class SingleLinkedList {
    public Node head;

    public void unsortedAdd(Object dataToAdd){
        if(head == null){
            Node newnode = new Node(dataToAdd);
            head = newnode;
        }
        else {
            Node temp = head;
            while (temp.getLink() != null)
                temp=temp.getLink();
            Node newnode = new Node(dataToAdd);
            temp.setLink(newnode);
        }
    }


    public void delete(Object dataToDelete) {
        if (head == null) {
            return;
        }
        Node current = head;
        Node previous = null;
        while (current != null) {
            if (current.getData().equals(dataToDelete)) {
                if (previous == null) {
                    head = current.getLink();
                } else {
                    previous.setLink(current.getLink());
                }
                return;
            }
            previous = current;
            current = current.getLink();
        }
    }



    public int size(){
        if(head == null){
            return 0;
        }
        else {
            int count = 0;

            Node temp = head;

            while (temp != null){
                temp = temp.getLink();
                if (temp == null) {
                    break;
                }
                count++;
            }
            return count;
        }
    }

    public void display(){
        if (head ==null){
            System.out.println(" ");
        }
        else {
            Node temp = head;

            while (temp != null){
                if (temp.getData() == ""){
                    System.out.print(" ");
                    break;
                }
                System.out.print(temp.getData()+ " ");
                temp = temp.getLink();
            }
        }
    }

    public Object get(int index) {
        if (head == null) {
            return "Linked list is empty";
        }

        Node current = head;
        int count = 0;

        while (current != null) {
            if (count == index) {
                return current.getData();
            }
            count++;
            current = current.getLink();
        }

        return "SLL Overflow";
    }


    public boolean contains(Object dataToFind) {
        if (head == null) {
            return false;
        }
        Node temp = head;
        while (temp != null) {
            if (temp.getData().equals(dataToFind)) {
                return true;
            }
            temp = temp.getLink();
        }
        return false;
    }

    public SingleLinkedList copy() {
        SingleLinkedList newList = new SingleLinkedList();
        Node temp = head;

        while (temp != null) {
            newList.unsortedAdd(temp.getData());
            temp = temp.getLink();
        }

        return newList;
    }

    public SingleLinkedList sort() {
        SingleLinkedList sortedList = new SingleLinkedList();
        Node current = head;
        while (current != null) {
            Object data = current.getData();
            Node temp = sortedList.head;
            Node prev = null;
            while (temp != null && Integer.parseInt((String) temp.getData()) > Integer.parseInt((String) data)) {
                prev = temp;
                temp = temp.getLink();
            }
            Node newNode = new Node(data);
            if (prev == null) {
                newNode.setLink(sortedList.head);
                sortedList.head = newNode;
            } else {
                prev.setLink(newNode);
                newNode.setLink(temp);
            }
            current = current.getLink();
        }
        return sortedList;
    }

}


