

public class FirstTask {

    private static void     ft_check_value(long num) {
        if (num < 0) {
            throw new NumberFormatException("Unsigned int numbers might be only positive values!");
        }
        if (num > 4294967295L) {
            throw new NumberFormatException("You are using too large number! Max unsigned int number = 4294967295");
        }
    }

    private static String   ft_parse_binary(long num) {
        String result = "";
        long a;

        while (num > 0) {
            a = num % 2;
            result = result + "" + a;
            num = num / 2;
        }
        result =  new StringBuilder(result).reverse().toString();
        return result;
    }

    private static String   ft_parse_ternary(long num) {
        String result = "";
        long a;

        while (num > 0) {
            a = num % 3;
            result = result + "" + a;
            num = num / 3;
        }
        result =  new StringBuilder(result).reverse().toString();
        return result;
    }

    private static String   ft_parse_octal(long num) {
        String result = "";
        long a;

        while (num > 0) {
            a = num % 8;
            result = result + "" + a;
            num = num / 8;
        }
        result =  new StringBuilder(result).reverse().toString();
        return (result);
    }

    private static String	ft_parse_hexadecimal(long num) {

        String result = "";
        char	c;

        while (num > 0) {
            if ((num % 16 >= 10))
                c = (char)((num % 16) - 10 + 'a');
            else
                c = (char)((num % 16) + '0');
            result += c;
            num /= 16;
        }
        return result;
    }

    private static String   itoBase(long nb, String base) {
        String result = "";
        ft_check_value(nb);

        if (nb == 0 || nb == 1)
            return (nb + "");

        switch (base) {
            case ("01") : result = ft_parse_binary(nb); break;
            case ("012") : result = ft_parse_ternary(nb); break;
            case ("01234567") : result = ft_parse_octal(nb); break;
            case ("0123456789abcdef") : result = ft_parse_hexadecimal(nb); break;
            default: System.out.println("usage");
        }
        return result;
    }

    public static void      main(String[] args) {

        long number = 4294967295L;

        System.out.println(itoBase(number, "01"));
        System.out.println(itoBase(number, "012"));
        System.out.println(itoBase(number, "01234567"));
        System.out.println(itoBase(number, "0123456789abcdef"));
        System.out.println(itoBase(number, "0123456789abcdedff"));

    }
}
