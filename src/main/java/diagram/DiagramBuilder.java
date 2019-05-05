package diagram;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


public class DiagramBuilder {
    private static final int WIDTH = 120;
    private static final int HEIGHT = 40;

    private int counter = 0;
    private List<Integer> rows = new LinkedList<>();
    private String rootXml;
    private String nodeXml;
    private String linkXml;

    public DiagramBuilder() {
        rootXml = readResource("/template/root.xml");
        nodeXml = readResource("/template/node.xml");
        linkXml = readResource("/template/link.xml");
    }

    @SneakyThrows
    private String readResource(String path) {
        InputStream stream = this.getClass().getResourceAsStream(path);
        return IOUtils.toString(stream);
    }

    public String build(Collection<Node> elements, Node root) {
        preProcess(elements, root);
        String elementsXml = buildElements(elements);
        return rootXml.replace("$elements", elementsXml);
    }

    private void preProcess(Collection<Node> elements, Node root) {
        setLevel(root, 0);
        for (Node n : elements) {
            n.setId(String.valueOf("node" + counter++));
            if (n.getLevel() == null) {
                n.setLevel(0);
            }
        }
    }

    private void setLevel(Node node, int level) {
        if (node.getLevel() == null) {
            node.setLevel(level);
            node.getLinks().forEach(n -> setLevel(n, level + 1));
        }
    }

    private String buildElements(Collection<Node> nodes) {
        StringBuilder sb = new StringBuilder();
        nodes.forEach(source -> source.getLinks().forEach(target -> sb.append(mapLink(source, target))));
        nodes.forEach(it -> sb.append(mapNode(it)));
        return sb.toString();
    }

    private String mapNode(Node node) {
        return nodeXml
                .replace("$id", String.valueOf(node.getId()))
                .replace("$label", String.valueOf(node.getLabel()))
                .replace("$width", String.valueOf(WIDTH))
                .replace("$height", String.valueOf(HEIGHT))
                .replace("$x", String.valueOf(10 + (getRowPosition(node.getLevel()) * (WIDTH + 20))))
                .replace("$y", String.valueOf(10 + node.getLevel() * HEIGHT * 2));
    }

    private int getRowPosition(Integer level) {
        while (rows.size() <= level) {
            rows.add(0);
        }
        int position = rows.get(level);
        rows.set(level, position + 1);
        return position;
    }

    private String mapLink(Node source, Node target) {
        return linkXml
                .replace("$id", String.valueOf("link" + counter++))
                .replace("$source", String.valueOf(source.getId()))
                .replace("$target", String.valueOf(target.getId()));
    }


}
