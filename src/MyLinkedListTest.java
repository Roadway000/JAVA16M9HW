public class MyLinkedListTest {
    public static void main(String[] args) {
        MyLinkedList<String> ll = new MyLinkedList<>();
        ll.add("first element");
        ll.add("second element");
        ll.add("third element");
        ll.remove(2);
        //System.out.println("ll.get(2) = " + ll.get(2));
        System.out.println(ll.getSize());
        ll.clear();
        System.out.println(ll.toString());
        //LinkedList ll2 = new LinkedList<>();
        //ll2.clear();
    }
}
class MyLinkedList<T> {
    private Node<T> firstNode;
    private Node<T> lastNode;
    private T value;
    private int size = 0;
    public void setValue(T value) {
        this.value = value;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void add(T val) {
        if (firstNode == null) {
            firstNode = new Node<>(null, null, val);
        } else if (lastNode == null) {
            lastNode = new Node<>(firstNode, null, val);
            firstNode.setNextNode(lastNode);
        } else {
            Node<T> newLastNode = new Node<>(lastNode, null, val);
            lastNode.setNextNode(newLastNode);
            lastNode = newLastNode;
        }
        size++;
    }
    public T get(int index) {
        Node<T> currentNode = firstNode;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.getNextNode();
        }
        if (currentNode != null)
            return currentNode.getValue();
        else return null;
    }
    public void remove(int index) {
        Node<T> currentNode = firstNode;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.getNextNode();
        }
        if (currentNode != null) {
            Node<T> prevNode = currentNode.getPrevNode();
            Node<T> nextNode = currentNode.getNextNode();
            if (prevNode != null)
                prevNode.setNextNode(nextNode);
            if (nextNode != null)
                nextNode.setPrevNode(prevNode);
            currentNode = null;
            size--;
        }
    }
    public void clear() {
        Node<T> currentNode = firstNode;
        firstNode = null;
        for (int i = 0; i < size; i++) {
            currentNode.setValue(null);
            currentNode.setPrevNode(null);
            currentNode = currentNode.getNextNode();
        }
        lastNode = null;
        size = 0;
    }
    @Override
    public String toString() {
        if (firstNode == null)
            return "";
        Node<T> currentNode = firstNode;
        StringBuilder nodeStr = new StringBuilder();
        for (int i = 0; i < size; i++) {
            nodeStr.append((String) currentNode.getValue()+", ");
            currentNode = currentNode.getNextNode();
        }
        return nodeStr.toString();
    }
}
class Node <T>{
    private Node<T> prevNode;
    private Node<T> nextNode;
    private T value;
    public Node(Node<T> prevNode, Node<T> nextNode, T value) {
        this.prevNode = prevNode;
        this.nextNode = nextNode;
        this.value = value;
    }

    public Node<T> getPrevNode() {
        return prevNode;
    }
    public Node<T> getNextNode() {
        return nextNode;
    }
    public T getValue() {
        return value;
    }
    public void setPrevNode(Node<T> prevNode) {
        this.prevNode = prevNode;
    }
    public void setNextNode(Node<T> nextNode) {
        this.nextNode = nextNode;
    }
    public void setValue(T value) {
        this.value = value;
    }
}
