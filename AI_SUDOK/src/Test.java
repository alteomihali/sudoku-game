import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Test {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("sudoku.txt"));

        String line = in.readLine();
        while (line != null) {
            new AC3(Node.getStructures(line));
            line = in.readLine();
        }
    }
}
