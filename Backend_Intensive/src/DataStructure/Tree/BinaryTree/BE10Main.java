package DataStructure.Tree.BinaryTree;




public class BE10Main {
    public static void main(String[] args) {
        //Balanced Tree
        BE10BinaryTree bst = new BE10BinaryTree();
        bst.root = new BE10BinaryTreeNode(8);
        bst.root.left = new BE10BinaryTreeNode(3);
        bst.root.right = new BE10BinaryTreeNode(10);
        bst.root.left.left = new BE10BinaryTreeNode(1);
        bst.root.left.right = new BE10BinaryTreeNode(6);
        bst.root.left.right.left = new BE10BinaryTreeNode(4);
        bst.root.left.right.right = new BE10BinaryTreeNode(7);
        bst.root.right.right = new BE10BinaryTreeNode(14);
        bst.root.right.right.left = new BE10BinaryTreeNode(13);

        System.out.println(bst.searchBST(6).value);
    }
}