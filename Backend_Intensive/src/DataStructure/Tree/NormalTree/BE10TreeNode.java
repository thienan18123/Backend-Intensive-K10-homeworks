package DataStructure.Tree.NormalTree;

import java.util.ArrayList;

public class BE10TreeNode {
    public int value;
    public ArrayList<BE10TreeNode> children;

    public BE10TreeNode(int value) {
        this.value = value;
        this.children = new ArrayList<>();
    }

}