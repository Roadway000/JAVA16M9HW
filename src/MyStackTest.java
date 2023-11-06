import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyStackTest {
    public static void main(String[] args) {
        IStack<Integer> myStack = new MyStack<>();
        myStack.push(1);
        myStack.push(2);
        myStack.push(3);
        System.out.println(myStack + "; size = " + myStack.size());

        System.out.println(myStack.peek());
        System.out.println(myStack.peekLast());
        System.out.println(myStack + "; size = " + myStack.size());
        myStack.remove(3); // <- указываем не существующий индекс
//        myStack.remove(1);
//        myStack.remove(0);
//        myStack.remove(0);
        myStack.clear();
        //System.out.println("size = " + myStack.size());
        System.out.println(myStack + "; size = " + myStack.size());
    }
}

interface IStack<E> extends Iterable<E> {
    int size();
    void push(E value); // Добавляет значение в самое начало очереди
        /*
        --push(Object value) додає елемент в кінець
        remove(int index) видаляє елемент за індексом
        clear() очищає колекцію
        size() повертає розмір колекції
        peek() повертає перший елемент стеку
        pop() повертає перший елемент стеку та видаляє його з колекції
        */
    void remove(int index); // видаляє елемент за індексом
    void clear(); // очищає колекцію
    E getFirst(); // Возвращаем без удаления значение из начала очереди.
    E getLast(); // Возвращаем без удаления последний значение очереди.
    boolean offerFirst(E value); // Добавляет значение в самое начало очереди.
    boolean offerLast(E value); // Добавляет значение в конец очереди.
    E peek(); // повертає перший елемент стеку
    E peekLast(); // Возвращает без удаления последнее значение очереди.
    E pollFirst(); // Возвращает с удалением значение из начала очереди.
//    E pollLast(); // Возвращает с удалением последнее значение очереди.
    E pop(); // Возвращает с удалением значение из начала очереди.
    E removeFirst(); // Возвращает с удалением значение из начала очереди.
    Object[] toArray(); // Преобразует элементы очереди в массив Object.
}

class MyStack<E> implements IStack<E> {
    private static final int MAX_LENGTH = 10;
    private Node<E> first;
    private Node<E> last;
    private int size;

    @Override
    public int size() {
        return this.size;
    }
    @Override
    public void push(E value) {
        offerLast(value);
    }
    @Override
    public void remove(int index) {
        if (index > size-1 || index < 0) return;
        int counter = 0;
        Node<E> removeNode = this.first;
        while (counter < index) {
            removeNode = removeNode.next;
            counter++;
        }
        if (this.first == removeNode) {
            this.first = removeNode.next;
        } else
        if (this.last == removeNode) {
            this.last = removeNode.prev;
        }
        if (removeNode == null) { return; }
        if (removeNode.prev != null && removeNode.prev.next != null) {
            removeNode.prev.next = removeNode.next;
        }
        this.size--;
    }

    @Override
    public void clear() {
        while (this.size>0) {
            if (this.first.next != null) {
                this.first = this.first.next;
                this.first.prev = null;
            }
            this.size--;
        }
        if (this.first != null)
            this.first = null;
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
        if (result = value != null && this.size < MAX_LENGTH) {
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
    /* добавляем элемент в конец очереди */
    public boolean offerLast(E value) {
        boolean result;
        if (result = value != null && this.size < MAX_LENGTH) {
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
    public E peekLast() {
        Node<E> element = this.last;
        return element != null ? element.value : null;
    }

    @Override
    public E pollFirst() {
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
    public E pop() {
        E value = pollFirst();
        if (value == null) {
            throw new NoSuchElementException();
        }
        return value;
    }

    @Override
    public E removeFirst() {
        E value = pollFirst();
        if (value == null) {
            throw new NoSuchElementException();
        }
        return value;
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
        return new IteratorDeque<>(this.first);
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

    private class IteratorDeque<E> implements Iterator<E> {
        private Node<E> first;

        public IteratorDeque(Node<E> first) {
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
