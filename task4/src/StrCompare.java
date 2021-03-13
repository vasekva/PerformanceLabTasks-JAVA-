
public class StrCompare {

    static boolean match(String first, String second)
    {

        // - // Если мы дойдем до конца обеих строк,
        // - // мы закончим
        if (first.length() == 0 && second.length() == 0)
            return true;

        // Убедитесь, что символы после '*'
        // присутствуют во второй строке.
        // Эта функция предполагает, что первая
        // строка не будет содержать двух подряд идущих '*'
        if (second.length() > 1 && second.charAt(0) == '*' &&
                first.length() == 0)
            return false;

        //-// если текущие символы обеих строк совпадают
        if ((first.length() != 0 && second.length() != 0 &&
                first.charAt(0) == second.charAt(0)))
            return match(first.substring(1),
                    second.substring(1));

        // Если есть *, то есть две возможности
        // a) Мы рассматриваем текущий символ второй строки
        // b) Игнорируем текущий символ второй строки.
        if (second.length() > 0 && second.charAt(0) == '*')
            return match(first, second.substring(1)) ||
                    match(first.substring(1), second);
        return false;
    }


    static void test(String first, String second)
    {
        if (match(first, second))
            System.out.println("Yes");
        else
            System.out.println("No");
    }

    public static void main(String[] args) {
//        if (args.length != 2) {
//            System.out.println("Неверное кол-во входных аргументов!");
//        } else {
//            ft_compare(args[0], args[1]);
        test("bicycle", "bic*le"); // Yes
        test("iwantasummer", "i*ta*u*mer"); // Yes
            test("bee", "b*k"); // No because 'k' is not in second
            test("sqrt", "*sqr"); // No because 't' is not in first
        test("abcdhghgbcd", "abc*bcd"); // Yes
        test("abcb cd", "abc****************cd");
        test("a", "*****************"); // Yes
        test("abcd", "*c*d"); // Yes
        test("abcd", "**c*d"); // Yes
//        }
    }
}
