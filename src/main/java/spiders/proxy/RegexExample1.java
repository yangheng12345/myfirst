package spiders.proxy;

import java.util.regex.Pattern;

public class RegexExample1 {
    public static void main(String args[]){
        String content = "I am noob " +
                "from runoob.com.";

        String pattern = ".+runoob.*";

        boolean isMatch = Pattern.matches(pattern, content);
        System.out.println("字符串中是否包含了 'runoob' 子字符串? " + isMatch);
    }
}
