import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
public class MyQueueTest {
    public static void main(String[] args) {
        IQueue<String> quequeStr = new MyQueque<>();
        quequeStr.add("first element");
        quequeStr.add("second element");
        quequeStr.add("third element");
        System.out.println(quequeStr + "; size = " + quequeStr.size());
        System.out.println("peek() => " + quequeStr.peek());
        System.out.println("poll() => "+ quequeStr.poll());
        System.out.println(quequeStr + "; size = " + quequeStr.size());

        quequeStr.add("first element");
        quequeStr.add("second element");
        System.out.println(quequeStr + "; size = " + quequeStr.size());
        quequeStr.clear();
        System.out.println(quequeStr + "; size = " + quequeStr.size());
    }
}

class MyQueque<E> implements IQueue<E> {
    private static final int MAX_SIZE = 10;
    private Node<E> first;
    private Node<E> last;
    private int size;

    @Override
    public int size() {
        return this.size;
    }
    @Override
    public E getFirst() {
        Node<E> element = this.first;
        if (element == null) {
            throw new NoSuchElementException();
        }
        return element.value;
    }
    @Override
    public E getLast() {
        Node<E> element = this.last;
        if (element == null) {
            element = this.first;
            if (element == null) {
                throw new NoSuchElementException();
            }
        }
        return element.value;
    }
    @Override
    public boolean offerFirst(E value) {
        boolean result;
        if (result = value != null && this.size < MAX_SIZE) {
            Node<E> newNode = new Node<>(null, null, value);
            if (this.first != null) {
                newNode.next = this.first;
                this.first = this.first.prev = newNode;
                if (this.last == null) {
                    this.last = first.next;
                }
            } else {
                this.first = newNode;
            }
            this.size++;
        }
        return result;
    }
    @Override
    public boolean add(E value) {
        boolean result;
        if (result = value != null && this.size < MAX_SIZE) {
            Node<E> newNode = new Node<>(null, null, value);
            if (this.last != null) {
                newNode.prev = this.last;
                this.last = last.next = newNode;
                this.size++;
            } else {
                if (this.first == null) {
                    result = offerFirst(value);
                } else {
                    this.last = this.first.next = newNode;
                    this.last.prev = this.first;
                    this.size++;
                }
            }
        }
        return result;
    }
    @Override
    public E peek() {
        Node<E> element = this.first;
        return element != null ? element.value : null;
    }
    @Override
    public E poll() {
        E value = null;
        if (this.first != null) {
            value = first.value;
            this.first = this.first.next;
            if (this.first != null) {
                first.prev = null;
            }
            this.size--;
        }
        return value;
    }
    @Override
    public void clear() {
        E value = null;
        do { value = poll(); }
        while (value != null);
    }
    @Override
    public String toString() {
        return Arrays.toString(toArray());
    }
    @Override
    public Object[] toArray() {
        Object[] array = new Object[this.size];
        int index = 0;
        for (Node<E> x = this.first; x != null; x = x.next) {
            array[index++] = x.value;
        }
        return array;
    }
    @Override
    public Iterator<E> iterator() {
        return new IteratorMyQueque<>(this.first);
    }
    private class Node<E> {
        private Node<E> prev;
        private Node<E> next;
        E value;
        public Node(Node<E> prev, Node<E> next, E value) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }

    private class IteratorMyQueque<E> implements Iterator<E> {
        private Node<E> first;
        public IteratorMyQueque(Node<E> first) {
            this.first = first;
        }
        @Override
        public boolean hasNext() {
            return this.first != null;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E value = this.first.value;
            this.first = first.next;
            return value;
        }
    }

}
interface IQueue<E> extends Iterable<E> {
    int size(); // кол-во елементов
    E getFirst();
    E getLast();

    boolean offerFirst(E value); // Добавляет значение в начало
    boolean add(E value); // Добавляет значение в конец
    E peek(); // Получаем значение из начала
    E poll(); // Получаем значение из начала с удаленнием.
    void clear();
    Object[] toArray(); // элементы в массив
}

