public class Main {
    public static void main(String[] args) {
        Tree tree = new Tree();

        for (int i = 1; i <= 20; i++) {
            tree.add(i);
        }
        tree.print();
    }
}
