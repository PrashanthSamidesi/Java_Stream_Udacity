package functionalInterface;

public class Main {
    public static void main(String[] args){
        BinaryOperation add = (a, b) -> a + b;

        System.out.println(add.apply(25, 30));
    }
}
