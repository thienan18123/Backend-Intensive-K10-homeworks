package DataStructure.Tree.BinaryTree;

import java.util.LinkedList;
import java.util.Queue;

public class BE10BinaryTree {
    public BE10BinaryTreeNode root;
    public BE10BinaryTreeNode bfs(int value) {
        if (root == null) {
            return null;
        }
        Queue<BE10BinaryTreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            BE10BinaryTreeNode node = queue.poll();

            if (node.value == value) {
                return node;
            }

            if (node.left != null) {
                queue.add(node.left);
            }

            if (node.right != null) {
                queue.add(node.right);
            }

        }
        return null;
    }

    public BE10BinaryTreeNode dfs(int val) {
        return dfsHelper(root,val);
    }

    private BE10BinaryTreeNode dfsHelper(BE10BinaryTreeNode node, int val) {
        if (node == null) {
            return null;
        }
        if (node.value == val ) {
            return node;
        }

        BE10BinaryTreeNode found = dfsHelper(node.left, val);
        if (found != null) {
            return found;
        } 
        return dfsHelper(node.right, val);
    }

    public BE10BinaryTreeNode searchBST(int val) {
        return searchBSTHelper(root, val);
    }


    private BE10BinaryTreeNode searchBSTHelper(BE10BinaryTreeNode node, int val) {
        if (node == null) {
            return null;
        }
        if (node.value == val) {
            return node;    
        }
        if (val < node.value) {
            return searchBSTHelper(node.left, val);
        } else {
            return searchBSTHelper(node.right,val);
        }
    }

    
}
