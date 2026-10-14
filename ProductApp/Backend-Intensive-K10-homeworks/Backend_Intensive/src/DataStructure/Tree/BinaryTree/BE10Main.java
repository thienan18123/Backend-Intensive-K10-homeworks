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

        //binary tree thường
        BE10BinaryTree tree =  new BE10BinaryTree();
         tree.root = new BE10BinaryTreeNode(1);
        tree.root.left = new BE10BinaryTreeNode(9);
        tree.root.right = new BE10BinaryTreeNode(4);
        tree.root.left.left = new BE10BinaryTreeNode(2);
        tree.root.left.right = new BE10BinaryTreeNode(7);
        tree.root.right.left = new BE10BinaryTreeNode(5);

        System.out.println("dfs(5)  : " + (tree.dfs(5).value));    // 5
        System.out.println("bfs(7)  : " + (tree.bfs(7).value));    // 7
    }
}