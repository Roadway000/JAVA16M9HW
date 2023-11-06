import java.util.Arrays;
import java.util.Iterator;

class MyArrayListTest {
    public static void main(String[] args) {
        Iterablex<String> MyListStr = new MyArrayList<>();
        Iterablex<Integer> MyListInt = new MyArrayList<>();
        MyListStr.add( "first element");
        MyListStr.add( "second element");
        MyListStr.add( "third element");
        MyListStr.add( "fourth element");
        MyListStr.add( "fifth element");
        System.out.println(MyListStr.toString());
        MyListStr.add( 1,"other element");
        System.out.println(MyListStr.toString());
        System.out.println("MyListStr.second element is \""+MyListStr.get(1)+"\"");
        MyListStr.reset(1,"changed element");
        System.out.println("MyListStr.second element is \""+MyListStr.get(1)+"\"");
        MyListStr.remove(1);
        System.out.println("MyListStr.remove(1)");
        System.out.println(MyListStr.toString());
        MyListStr.clear();
        System.out.println(MyListStr.toString());
    }
}
class MyArrayList<E> implements Iterablex<E>{
    private E[] values;
    private int size = 0;
    public MyArrayList() {
        this.values = (E[]) new Object[0];
    }

    @Override
    public String toString() {
        return "MyArrayList .size = "+size()+" .values: " + Arrays.toString(values);
    }

    @Override
    public boolean add(E value) {
        try {
            if (size == values.length) {
                E[] oldValues = values;
                values = (E[]) new Object[oldValues.length * 2 + 1];
                System.arraycopy(oldValues, 0, values, 0, oldValues.length);
            }
            values[size] = value;
            size++;
            return true;
        } catch(ClassCastException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean add(int index, E value) {
        try {
            E[] oldValues = values;
            values = (E[]) new Object[oldValues.length + 1];
            System.arraycopy(oldValues, 0, values, 0, index);
            values[index] = value;
            //values = "1"
            int elementLength = oldValues.length-index;
            System.arraycopy(oldValues, index, values, index+1, elementLength);
            size++;
            return true;
        } catch(ClassCastException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public void remove(int index) {
        try {
            E[] oldValues = values;
            values = (E[]) new Object[oldValues.length - 1];
            System.arraycopy(oldValues, 0, values, 0, index);
            int elementLength = values.length-index;
            System.arraycopy(oldValues, index + 1, values, index, elementLength);
        } catch(ClassCastException e) {
            e.printStackTrace();
        }
        size--;
    }
    @Override
    public void clear() {
        for(int i=0; i<size; i++) {
            values[i] = null;
        }
        values = (E[]) new Object[0];
    }
    @Override
    public int size() {
        return values.length;
    }
    @Override
    public E get(int index) {
        return values[index];
    }
    @Override
    public void reset(int index, E e) {
        values[index] = e;
    }
    @Override
    public Iterator<E> iterator() {
        return new ArrayIterator<E>(values);
    }
}
class ArrayIterator<E> implements Iterator<E> {
    private int index = 0;
    private E[] values;
    public ArrayIterator(E[] values) {
        this.values = values;
    }

    @Override
    public boolean hasNext() {
        return index < values.length;
    }
    @Override
    public E next() {
        return values[index++];
    }
}

interface Iterablex<E> extends Iterable<E> {
    boolean add(E value);
    boolean add(int index, E value);
    void remove(int index);
    void clear();
    int size();
    E get(int index);
    void reset(int index, E e);
}