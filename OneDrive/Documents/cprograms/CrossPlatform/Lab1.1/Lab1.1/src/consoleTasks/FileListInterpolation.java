package consoleTasks;
import java.io.*;
import java.util.*;

public class FileListInterpolation extends ListInterpolation {
    public void readFromFile(String fileName) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(fileName));
        String s = in.readLine(); // заголовок
        clear();
        while ((s = in.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(s);
            addPoint(new Point2D(Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken())));
        }
        in.close();
    }

    public void writeToFile(String fileName) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(fileName));
        out.printf("%9s%25s\n", "x", "y");
        for (int i = 0; i < numPoints(); i++) {
            out.println(getPoint(i).getX() + "\t" + getPoint(i).getY());
        }
        out.close();
    }
}
