package org.tsybus.generator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.List;

public class VisualGenerator {

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File("output.json"));
        JsonNode results = root.get("results");

        File dir = new File("graphs_png");
        if (!dir.exists()) dir.mkdirs();

        for (JsonNode graphNode : results) {
            int id = graphNode.get("graph_id").asInt();
            int vertexCount = graphNode.get("input_stats").get("vertices").asInt();
            if (vertexCount > 30) continue;

            System.out.println("Drawing graph " + id + " (" + vertexCount + " vertices)");

            List<Point> points = generateLayout(vertexCount);
            Map<String, Point> pos = new HashMap<>();
            for (int i = 0; i < vertexCount; i++) {
                pos.put("N" + i, points.get(i));
            }

            JsonNode prim = graphNode.get("prim");
            Set<String> mstEdges = new HashSet<>();
            for (JsonNode e : prim.get("mst_edges")) {
                String a = e.get("from").asText();
                String b = e.get("to").asText();
                mstEdges.add(edgeKey(a, b));
            }

            List<String> nodes = new ArrayList<>(pos.keySet());
            int size = 600;
            BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = img.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, size, size);

            g.setStroke(new BasicStroke(2));
            g.setColor(Color.LIGHT_GRAY);
            Random rand = new Random(id);
            for (int i = 0; i < nodes.size(); i++) {
                for (int j = i + 1; j < nodes.size(); j++) {
                    if (rand.nextDouble() < 0.25) {
                        Point p1 = pos.get(nodes.get(i));
                        Point p2 = pos.get(nodes.get(j));
                        g.draw(new Line2D.Double(p1.x, p1.y, p2.x, p2.y));
                    }
                }
            }
            g.setStroke(new BasicStroke(2));
            g.setColor(Color.RED);
            for (String edgeKey : mstEdges) {
                String[] parts = edgeKey.split("-");
                Point p1 = pos.get(parts[0]);
                Point p2 = pos.get(parts[1]);
                if (p1 != null && p2 != null)
                    g.draw(new Line2D.Double(p1.x, p1.y, p2.x, p2.y));
            }
            g.setColor(Color.BLACK);
            for (Map.Entry<String, Point> e : pos.entrySet()) {
                Point p = e.getValue();
                g.fillOval(p.x - 4, p.y - 4, 8, 8);
                g.drawString(e.getKey(), p.x + 6, p.y - 6);
            }

            g.dispose();

            File outFile = new File(dir, "graph_" + id + ".png");
            ImageIO.write(img, "png", outFile);
        }

        System.out.println("Successfully draw graphs");
    }

    private static List<Point> generateLayout(int n) {
        List<Point> points = new ArrayList<>();
        int centerX = 300, centerY = 300, radius = 220;
        for (int i = 0; i < n; i++) {
            double angle = 2 * Math.PI * i / n;
            int x = (int) (centerX + radius * Math.cos(angle));
            int y = (int) (centerY + radius * Math.sin(angle));
            points.add(new Point(x, y));
        }
        return points;
    }

    private static String edgeKey(String a, String b) {
        return a.compareTo(b) < 0 ? a + "-" + b : b + "-" + a;
    }
}
