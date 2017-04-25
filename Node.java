import java.util.ArrayList;
import java.util.List;

/**
 * Created by dama on 15.03.2017.
 */
public class Node {
    Object data;
    List<Node> children = new ArrayList<>();
    Node parent;


    public Node(Node parent) {
        this.parent = parent;

    }

    public static Node addChild(Node parent, Object data) {
        Node node = new Node(parent);
        node.setData(data);
        parent.getChildren().add(node);
        return node;
    }

    public static Node addChild(Node parent) {
        Node node = new Node(parent);
        parent.children.add(node);
        return node;
    }

    public static void printTree(Node node, String appender) {
        if (node.getRoot() == null) ;
        else System.out.println(appender + node.getData() + "  ,root node is: " + node.getRoot().getData());
        for (Node each : node.getChildren()) {
            printTree(each, appender + appender);
        }
    }

    public static void preorder(Node root) {
        System.out.println(root.getData());
        for (Node child : root.getChildren()) preorder(child);

    }

    public static void postorder(Node root) {
        for (Node child : root.getChildren()) {
            postorder(child);
        }
        System.out.println(root.getData());
    }

    public Node getRoot() {
        if (parent == null) {
            return null;
        }
        return this.parent;
    }

    public Node getParent() {
        return parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


}
