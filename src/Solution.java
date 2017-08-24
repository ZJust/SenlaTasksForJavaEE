import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Solution {
    public static void main(String[] args) throws IOException {
        findPrimeNumbers();
        findFibonacciNumbers();
        intersectionOfTwoLineSegments();
        findNODandNOK();
        isPalindrome();
        delNumbers();
    }

    //1
    static void findPrimeNumbers() throws IOException {
        ArrayList<Integer> primeNumbers = new ArrayList<>();
        int n;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Задание №1. Введите число N: ");
            n = Integer.parseInt(bufferedReader.readLine());
            if (n < 0) throw new NumberFormatException();
            for (int i = 1; i <= n; i++) {
                boolean isPrime = true;
                for (int j = 2; j < i; j++) {
                    if (i % j == 0) {
                        isPrime = false;
                        break;
                    }
                }
                if (isPrime)
                    primeNumbers.add(i);
            }
            if (primeNumbers.size() == 0)
                System.out.println("В данном диапазоне нет простых чисел");
            else {
                System.out.print("Простые числа: ");
                for (int pn : primeNumbers)
                    System.out.print(pn + " ");
            }
        }
        catch (NumberFormatException e) {
            System.out.println("Вы ввели какую-то аброкадабру вместо натурального числа.");
        }
        System.out.println();
    }

    //2
    static void findFibonacciNumbers() throws IOException {
        ArrayList<Integer> fib = new ArrayList<>();
        int n, sum, a1 = 0, a2 = 1;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Задание №2. Введите число N: ");
            n = Integer.parseInt(bufferedReader.readLine());
            for (int i = 0; i < n; i++) {
                sum = a1 + a2;
                fib.add(sum);
                a1 = a2;
                a2 = sum;
            }
        }
        catch (NumberFormatException e) {
            System.out.println("Вы ввели какую-то аброкадабру вместо числа.");
        }
        for (int f : fib)
            System.out.print(f + " ");
        System.out.println();
    }

    //3
    static void intersectionOfTwoLineSegments() throws IOException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Введите координаты точки Р1: ");
            Point p1 = new Point(Double.parseDouble(bufferedReader.readLine()), Double.parseDouble(bufferedReader.readLine()));
            System.out.println("Введите координаты точки Р2: ");
            Point p2 = new Point(Double.parseDouble(bufferedReader.readLine()), Double.parseDouble(bufferedReader.readLine()));
            System.out.println("Введите координаты точки Р3: ");
            Point p3 = new Point(Double.parseDouble(bufferedReader.readLine()), Double.parseDouble(bufferedReader.readLine()));
            System.out.println("Введите координаты точки Р4: ");
            Point p4 = new Point(Double.parseDouble(bufferedReader.readLine()), Double.parseDouble(bufferedReader.readLine()));
            if (isIntersection(p1, p2, p3, p4))
                System.out.println("Отрезки пересекаются");
            else
                System.out.println("Отрезки не пересекаются");
        }
        catch (NumberFormatException e) {
            System.out.println("Введено не число.");
        }
    }
    public static class Point {

        double x, y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
    private static boolean isIntersection(Point p1, Point p2, Point p3, Point p4) {

//если нужно, переставляем точки, чтобы было p1.x <= p2.x и p3.x <= p4.x
        if (p2.x < p1.x) {
            Point tmp = p1;
            p1 = p2;
            p2 = tmp;
        }
        if (p4.x < p3.x) {
            Point tmp = p3;
            p3 = p4;
            p4 = tmp;
        }


        if (p2.x < p3.x) {
            return false; //нет взаимной абсциссы
        }

//если оба отрезка вертикальные и лежат на одном X
        if((p1.x - p2.x == 0) && (p3.x - p4.x == 0)) {
            if(p1.x == p3.x) {
                if (!((Math.max(p1.y, p2.y) < Math.min(p3.y, p4.y)) || (Math.min(p1.y, p2.y) > Math.max(p3.y, p4.y)))) {
                    return true;
                }
            }
            return false;
        }

//если первый отрезок вертикальный, найдём Xa, Ya - точки пересечения двух прямых
        if (p1.x - p2.x == 0) {
            double Xa = p1.x;
            double A2 = (p3.y - p4.y) / (p3.x - p4.x);
            double b2 = p3.y - A2 * p3.x;
            double Ya = A2 * Xa + b2;
            if (p3.x <= Xa && p4.x >= Xa && Math.min(p1.y, p2.y) <= Ya && Math.max(p1.y, p2.y) >= Ya) {
                return true;
            }
            return false;
        }

//если второй отрезок вертикальный, найдём Xa, Ya - точки пересечения двух прямых
        if (p3.x - p4.x == 0) {
            double Xa = p3.x;
            double A1 = (p1.y - p2.y) / (p1.x - p2.x);
            double b1 = p1.y - A1 * p1.x;
            double Ya = A1 * Xa + b1;
            if (p1.x <= Xa && p2.x >= Xa && Math.min(p3.y, p4.y) <= Ya && Math.max(p3.y, p4.y) >= Ya) {
                return true;
            }
            return false;
        }

//оба отрезка невертикальные
        double A1 = (p1.y - p2.y) / (p1.x - p2.x);
        double A2 = (p3.y - p4.y) / (p3.x - p4.x);
        double b1 = p1.y - A1 * p1.x;
        double b2 = p3.y - A2 * p3.x;
        if (A1 == A2) {
            return false; //отрезки параллельны
        }

//Xa - абсцисса точки пересечения двух прямых
        double Xa = (b2 - b1) / (A1 - A2);
        if ((Xa < Math.max(p1.x, p3.x)) || (Xa > Math.min( p2.x, p4.x))) {
            return false; //точка Xa находится вне пересечения проекций отрезков на ось X
        }
        else {
            return true;
        }

    }


    // 4
    static void findNODandNOK() throws IOException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Задание №4. Введите два числа: ");
            int a = Integer.parseInt(bufferedReader.readLine());
            int b = Integer.parseInt(bufferedReader.readLine());
            if (a < 0 || b < 0) throw new NumberFormatException();
            System.out.println("Их НОД равен " + nod(a, b));
            System.out.println("Их НОК равен " + nok(a, b));
        }
        catch (NumberFormatException e) {
            System.out.println("Вы ввели не целые положительные числа.");
        }
    }
    static int nod(int a, int b) {
        int min = Math.min(a, b);
        if (a%min == 0 && b%min == 0)
            return min;
        else
            return nod(a-1, b-1);
    }
    static int nok(int a, int b) {
        int max;
        max = a > b ? a : b;
        for (int count = 2; ; count++) {
            if (max%a == 0 && max%b == 0)
                return max;
            else if (max == a)
                return nok(a*count, b);
            else
                return nok(a, b*count);
        }
    }

    //5
    static void isPalindrome () throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Задание №5. Введите слово для проверки на палиндромность. ");
        String str = bufferedReader.readLine();
        String rev_str = new StringBuilder(str).reverse().toString();
        if (str.toLowerCase().equals(rev_str.toLowerCase()))
            System.out.println("Слово \"" + str + "\" является палиндромом.");
        else System.out.println("Слово \"" + str + "\" не является палиндромом.");

    }

    //6
    static void delNumbers() throws IOException {
        System.out.println("Задание №6. Введите строку.");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String str = bufferedReader.readLine() ;
        System.out.println(str.replaceAll("[0-9]", ""));
    }

}