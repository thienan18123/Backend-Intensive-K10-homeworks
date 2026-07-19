package DataStructure.Tree.NormalTree;

public class BE10Main {
    public static void main(String[] args) {
        BE10TreeNode node = new BE10TreeNode(1);

        BE10Tree tree = new BE10Tree();
        tree.root = node;

        // Xây cây thử:  1 → {12, 13},  12 → {120}
        tree.root.children.add(new BE10TreeNode(12));
        tree.root.children.add(new BE10TreeNode(13));
        tree.bfs(12).children.add(new BE10TreeNode(120));

        // Test tìm kiếm
        System.out.println(tree.bfs(12).value);     // 12
        System.out.println(tree.bfs(120).value);    // 120
        System.out.println(tree.bfs(99));           // null

        // Test thêm node sau node 12
        tree.addAfter(12, 125);
        System.out.println(tree.bfs(125).value);    // 125

        // Test DFS (sau khi em viết xong)
        // System.out.println(tree.dfs(120).value); // 120
    }
    
}
