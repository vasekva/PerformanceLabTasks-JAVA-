
public class Collision {

    private static void check_collisions(double[] coordA, double[] coordB, double[] coordC, double R) {
        double Ax = coordA[0], Bx = coordB[0], Cx = coordC[0];
        double Ay = coordA[1], By = coordB[1], Cy = coordC[1];

        // Вычисляем евклидово расстояние между A & B
        double LAB = Math.sqrt(Math.pow(Bx - Ax, 2) + Math.pow(By - Ay, 2));
        System.out.println("LAB: " + LAB);
        // Вычисляем вектор направления D от A до B
        double Dx = (Bx - Ax) / LAB;
        double Dy = (By - Ay) / LAB;
        System.out.println("Dx && Dy: " + Dx + " " + Dy);
        // Вычисляем расстояние между точками A и E, где E - точка AB, ближайшая к центру окружности (Cx, Cy)
        double t = Dx * (Cx - Ax) + Dy * (Cy - Ay);

        // Вычисляем координаты точки E
        double Ex = t * Dx + Ax;
        double Ey = t * Dy + Ay;
        System.out.println("Ex && Ey: " + Ex + " " + Ey);
        // Вычисляем евклидово расстояние между E и C
        double LEC = Math.sqrt(Math.pow(Ex - Cx, 2) + Math.pow(Ey - Cy, 2));
        System.out.println("LEC: " + LEC);
        // Проверяем пересекла ли линия круг
        if (LEC < R) {
            // Вычисляем расстояние от t до точки пересечения окружности
            double dt = Math.sqrt(Math.pow(R, 2) - Math.pow(LEC, 2));

            // Вычисляем первую точку пересечения
            double Fx = (t - dt) * Dx + Ax;
            double Fy = (t - dt) * Dy + Ay;

            // Вычилсяем вторую точку пересечения
            double Gx = (t + dt) * Dx + Ax;
            double Gy = (t + dt) * Dy + Ay;
            System.out.printf("Points of intersections: (%f %f) (%f %f)", Fx, Fy, Gx, Gy);
        }
        else if (LEC == R) {
            System.out.println("Косание");
        }
        else
        {
            System.out.println("Nope");
        }

    }

    public static void main(String[] args) {
        double[] coordsA = {-2, -2};
        double[] coordsB = {8, -2};
        double[] coordsC = {4, -2};
        double R = 2.0;

        check_collisions(coordsA, coordsB, coordsC, R);
    }
}
