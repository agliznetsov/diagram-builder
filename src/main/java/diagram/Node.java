package diagram;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
public class Node {
    String id;
    String label;
    Integer level;
    List<Node> links = new LinkedList<>();

    public Node(String label) {
        this.label = label;
    }

    public void link(Node node) {
        this.links.add(node);
    }
}
