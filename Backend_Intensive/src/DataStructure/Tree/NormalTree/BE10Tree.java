package DataStructure.Tree.NormalTree;

import java.util.LinkedList;
import java.util.Queue;

public class BE10Tree {
    public BE10TreeNode root;

    public BE10TreeNode bfs(int value) {
        if (root == null) return null;

        Queue<BE10TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            BE10TreeNode node = queue.poll();      // lấy từ ĐẦU (FIFO)

            if (node.value == value) {
                return node;                        
            }

            for (BE10TreeNode child : node.children) {
                queue.add(child);                   // thêm vào CUỐI (FIFO)
            }
        }

        return null;                                // duyệt hết, không thấy
    }

    public BE10TreeNode dfs(int val) {
        return dfsHelper(root, val);
    }

    private BE10TreeNode dfsHelper(BE10TreeNode node, int val){
        if (node == null) {
            return null;
        }
        if (node.value == val) {
            return node;
        }

        for (BE10TreeNode child : node.children) {
            BE10TreeNode found = dfsHelper(child, val);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    // Thêm node mới làm con của node có giá trị targetValue
    public boolean addAfter(int targetValue, int newValue) {
        BE10TreeNode target = bfs(targetValue);
        if (target == null) return false;

        target.children.add(new BE10TreeNode(newValue));
        return true;
    }
}
    
    

