package workingWithIO;

import java.nio.file.Path;

public class PathApiDemo {
    public static void main(String[] args){
        Path p = Path.of(".");
        System.out.println(p.toAbsolutePath()); // Prints the path till the current folder
        System.out.println(p.toAbsolutePath().normalize()); // Prints the current folder in which we are
        System.out.println(p.toAbsolutePath().resolve("..").normalize()); // Goes back one folder and prints the path
        System.out.println(p.toAbsolutePath().toUri()); // Prints the link which can be used to open in the browser
    }
}
