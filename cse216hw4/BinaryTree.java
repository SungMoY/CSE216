import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class BinaryTree<T> implements Iterable<T> {

    T data;
    BinaryTree<T> leftChild;
    BinaryTree<T> rightChild;

    public BinaryTree() {
        this.data = null;
    }

    public BinaryTree(T data) {
        this.data = data;
    }

    public void addLeftChild(BinaryTree childTree) {
        this.leftChild = childTree;
    }

    public void addRightChild(BinaryTree childTree) {
        this.rightChild = childTree;
    }

    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator(this);
    }

    private class BinaryTreeIterator implements Iterator<T> {

        private Queue<BinaryTree<T>> queue = new LinkedList<>();

        public BinaryTreeIterator(BinaryTree<T> tree) {
            if (tree.data != null)
                this.queue.add(tree);
        }
        
        @Override
        public boolean hasNext() {
            return !this.queue.isEmpty();
        }

        @Override
        public T next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            BinaryTree<T> tree = queue.remove();
            if (tree.leftChild != null) {
                queue.add(tree.leftChild);
            }
            if (tree.rightChild != null) {
                queue.add(tree.rightChild);
            }
            return tree.data;
        }
    }
}
