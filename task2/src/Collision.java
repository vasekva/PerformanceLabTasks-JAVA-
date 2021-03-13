import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Тестовые варианты конфигов на парсинг:
 *   {sphere: {center: [0, 0, 0], radius: 10.67}, line: {[1, 0.5, 15], [43, -14.6, 0.04]}}
 *   {sphere: {radius: 10.67, center: [0, 0, 0]}, line: {[1, 0.5, 15], [43, -14.6, 0.04]}}
 *   {line: {[1, 0.5, 15], [43, -14.6, 0.04], sphere: {radius: 10.67, center: [0, 0, 0]}}}
 *   {sphere: {radius: 10.67, center: [0, 0, 0]}, line: {[43, -14.6, 0.04], [1, 0.5, 15]}}
 *   {line: {[43, -14.6, 0.04], [1, 0.5, 15], sphere: {radius: 10.67, center: [0, 0, 0]}}}
 */

public class Collision {

    private static double[] ft_parse_sphere(String str) {
        double[] coordsC;

        // Вытаскиваем из строки значения отвечающее за сферу

        Pattern pattern = Pattern.compile("sphere: \\{.*?\\}");
        Matcher matcher = pattern.matcher(str);

        String sphere = "";
        while (matcher.find()) {
            sphere = str.substring(matcher.start(), matcher.end());
        }
        sphere = sphere.replaceAll("sphere: |\\{|\\}", "");

        // Вытаскиваем из строки значение отвечающее за координаты центра окружности

        pattern = Pattern.compile("\\[.*\\]");
        matcher = pattern.matcher(sphere);
        String coord = "";
        while (matcher.find()) {
            coord = sphere.substring(matcher.start(), matcher.end());
        }

        coord = coord.replaceAll("\\[|\\]|\\s", "");
        coordsC = Arrays.asList(coord.split(",")).stream().mapToDouble(Double::parseDouble).toArray();

        return coordsC;
    }

    private static double ft_parse_radius(String str) {
        double radius;

        Pattern pattern = Pattern.compile("sphere: \\{.*?\\}");
        Matcher matcher = pattern.matcher(str);

        String sphere = "";
        while (matcher.find()) {
            sphere = str.substring(matcher.start(), matcher.end());
        }
        sphere = sphere.replaceAll("sphere: |\\{|\\}", "");

        // Получаем значение радиуса из строки параметров сферы

        pattern = Pattern.compile("radius: .+?,|radius: .+?}");
        matcher = pattern.matcher(str);
        String radius_str = "";
        while (matcher.find()) {
            radius_str = str.substring(matcher.start(), matcher.end());
        }
        radius_str = radius_str.replaceAll("radius: |}|,", "");
        radius = Double.parseDouble(radius_str);
        return radius;
    }


    private static double[] ft_parse_A(String str) {
        double[] coordsA = new double[3];

        Pattern pattern = Pattern.compile("line: .+?]}");
        Matcher matcher = pattern.matcher(str);
        String line = "";
        while (matcher.find()) {
            line = str.substring(matcher.start(), matcher.end());
        }
        line = line.replaceAll("line: |}|\\{", "");

        // Вытаскиваем из строки line две последовательности координат(точек отрезка) в массив

        pattern = Pattern.compile("\\[.+?\\]");
        matcher = pattern.matcher(line);
        int i = 0;
        while (matcher.find()) {
            str = line.substring(matcher.start(), matcher.end()).replaceAll(" |\\[|\\]", "");
            if (i == 0)
                coordsA = Arrays.asList(str.split(",")).stream().mapToDouble(Double::parseDouble).toArray();
            i++;
        }
        return coordsA;
    }

    private static double[] ft_parse_B(String str) {
        double[] coordsB = new double[3];

        Pattern pattern = Pattern.compile("line: .+?]}");
        Matcher matcher = pattern.matcher(str);
        String line = "";
        while (matcher.find()) {
            line = str.substring(matcher.start(), matcher.end());
        }
        line = line.replaceAll("line: |}|\\{", "");

        // Вытаскиваем из строки line две последовательности координат(точек отрезка) в массив

        pattern = Pattern.compile("\\[.+?\\]");
        matcher = pattern.matcher(line);
        int i = 0;
        while (matcher.find()) {
            str = line.substring(matcher.start(), matcher.end()).replaceAll(" |\\[|\\]", "");
            if (i == 1)
                coordsB = Arrays.asList(str.split(",")).stream().mapToDouble(Double::parseDouble).toArray();
            i++;
        }
        return coordsB;
    }

    public static void check_collisions3D(double[] linePoint0, double[] linePoint1, double[] circleCenter, double circleRadius)
    {
        double cx = circleCenter[0];
        double cy = circleCenter[1];
        double cz = circleCenter[2];

        double px = linePoint0[0];
        double py = linePoint0[1];
        double pz = linePoint0[2];

        double vx = linePoint1[0] - px;
        double vy = linePoint1[1] - py;
        double vz = linePoint1[2] - pz;

        double A = vx * vx + vy * vy + vz * vz;
        double B = 2.0 * (px * vx + py * vy + pz * vz - vx * cx - vy * cy - vz * cz);
        double C = px * px - 2 * px * cx + cx * cx + py * py - 2 * py * cy + cy * cy +
                pz * pz - 2 * pz * cz + cz * cz - circleRadius * circleRadius;

        // discriminant
        double D = B * B - 4 * A * C;

        double t1 = (-B - Math.sqrt(D)) / (2.0 * A);

        double[] solution1 = {
                linePoint0[0] * (1 - t1) + t1 * linePoint1[0],
                linePoint0[1] * (1 - t1) + t1 * linePoint1[1],
                linePoint0[2] * (1 - t1) + t1 * linePoint1[2]
        };

        double t2 = (-B + Math.sqrt(D)) / (2.0 * A);
        double[] solution2 = {
                linePoint0[0] * (1 - t2) + t2 * linePoint1[0],
                linePoint0[1] * (1 - t2) + t2 * linePoint1[1],
                linePoint0[2] * (1 - t2) + t2 * linePoint1[2]
        };

        if (D < 0 || t1 > 1 || t2 >1) {
            System.out.println("Коллизий не найдено");
        } else if (D == 0)  {
            System.out.println(solution1);
        } else {
            for (double one : solution1) {
                System.out.print(one + " ");
            }
            System.out.println();
            for (double two : solution2) {
                System.out.print(two + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        double[]    coordsA;
        double[]    coordsB;
        double[]    coordsC;
        double      R;

        String str = "";
        if (args.length == 1) {
            try(
                    FileReader reader = new FileReader(args[0]);
                    Scanner scanner = new Scanner(reader);
                ) {
                if (scanner.hasNextLine()) {
                    str = scanner.nextLine();
                }
                coordsC = ft_parse_sphere(str);
                coordsA = ft_parse_A(str);
                coordsB = ft_parse_B(str);
                R = ft_parse_radius(str);

                check_collisions3D(coordsA, coordsB, coordsC, R);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Неверное кол-во аргументов!");
        }
    }
}
