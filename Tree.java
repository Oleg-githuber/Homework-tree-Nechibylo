public class Tree {
    Node root;
    class Node {
        int value;
        Node leftChild;
        Node rightChild;
        Color color;
    }

    // Цвета узлов
    enum Color {
        BLACK, RED
    }

    // Метод добавления узла в дерево
    public void add(int value) {
        if (root != null) {     // Если корень существует, добавляем элемент и проводим балансировку
            add(root, value);
            root = balance(root);
        }
        else {                  // Если корень не существует, присваиваем ему значение присваимого элемента
            root = new Node();
            root.value = value;
        }
        root.color = Color.BLACK;   // Корень всегда чёрный
    }

    // Метод добавления узла в дерево
    private void add(Node node, int value) {
        if (node.value != value) {
            if (node.value < value) {       // Если значение ноды меньше нового элемента помещаем элемент в правую ветку
                if (node.rightChild == null) {  // Если правая ветка пуста, просто помещаем элемент в правую ветку
                    node.rightChild = new Node();
                    node.rightChild.value = value;
                    node.rightChild.color = Color.RED;  // Каждая новая нода должна имень красный цвет
                } else {
                    add(node.rightChild, value);    // Если правая ветка не пустая, производим рекурсивное добавление
                    node.rightChild = balance(node.rightChild);
                }
                
            } else {                                // Если значение ноды больше, чем значение нового элемента, производим те же операции, но уже для левой ветки
                if (node.leftChild == null) {
                    node.leftChild = new Node();
                    node.leftChild.value = value;
                    node.leftChild.color = Color.RED;
                } else {
                    add(node.leftChild, value);
                    node.leftChild = balance(node.leftChild);
                }
            }
        }
    }


    // Метод поиска
    public Node find(int value) {
        return find(root, value);   // Поиск начинается с корня
    }

    // Метод поиска
    private Node find(Node node, int value) {
        if (node == null) {     // Если нода отсутствует, выходим из функции
            return null;
        }
        if (node.value == value) {
            return node;        // Если нашли нужный элемент
        }
        if (node.value < value) {       // В зависимости от неравенства вызываем рекурсию в правой либо левой ветке
            return find(node.rightChild, value);
        } else {
            return find(node.leftChild, value);
        }
    }
    // Правый переворот
    private Node rightRotation(Node node) {
        Node current = node.rightChild;
        node.rightChild = current.leftChild;
        current.leftChild = node;
        current.color = node.color;
        node.color = Color.RED;
        return current;
    }

    // Левый переворот
    private Node leftRotation(Node node) {
        Node current = node.leftChild;
        node.leftChild = current.rightChild;
        current.rightChild = node;
        current.color = node.color;
        node.color = Color.RED;
        return current;
    }

    // Смена цвета
    private void swapColors(Node node) {
        node.color = (node.color == Color.RED) ? Color.BLACK: Color.RED;
        node.leftChild.color = Color.BLACK;
        node.rightChild.color = Color.BLACK;
    }


    // Балансировка
    private Node balance(Node node) {
        Node current = node;
        boolean flag = true;
        while (flag) {
            flag = false;
            // Если справа находится красная нода, вызываем правый переворот
            if ((current.rightChild != null) && (current.rightChild.color == Color.RED) && 
            ((current.leftChild == null) || (current.leftChild.color == Color.BLACK))) {
                current = rightRotation(current);
                flag = true;
            }
            // Если две левые ветки подряд красного цвета вызываем леывй переворот
            if ((current.leftChild != null) && (current.leftChild.color == Color.RED) && 
            (current.leftChild.leftChild != null) && (current.leftChild.leftChild.color == Color.RED)) {
                current = leftRotation(current);
                flag = true;
            }
            // Если обе ветки красного цвета, вызываем смену цвета
            if ((current.leftChild != null) && (current.leftChild.color == Color.RED) && 
            (current.rightChild != null) && (current.rightChild.color == Color.RED)) {
                swapColors(current);
                flag = true;
            }
        }
        return current;
    }


    // Печать дерева
    public void print() {
        print(root, 0);
    }

    private void print(Node node, int deep) {
        if (node == null) {
            return;
        }
        print(node.rightChild, deep + 10);
        //System.out.println();

        for (int i = 0; i < deep; i++) {
            System.out.print(" ");
        }

        System.out.println("value: " + node.value + "{color: " + node.color + "}");
        //System.out.println();

        print(node.leftChild, deep + 10);
    }

}
