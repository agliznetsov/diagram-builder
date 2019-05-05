package diagram;

import org.junit.Test;

import java.util.Arrays;

public class DiagramBuilderTest {
    @Test
    public void test() {
        Node n1 = new Node("root");
        Node n2 = new Node("bbb");
        Node n3 = new Node("ccc");
        Node n4 = new Node("aaa");
        n1.link(n2);
        n1.link(n3);
        n2.link(n4);
        String xml = new DiagramBuilder().build(Arrays.asList(n1, n2, n3, n4), n1);
        System.out.println(xml);
    }
}
