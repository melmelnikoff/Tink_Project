import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.tinkoff.edu.java.parser.Parser;

import java.util.List;


public class MainTestParser {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext("ru.tinkoff.edu.java.parser");
        //ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext('spring-config.xml');
        Parser parser = (Parser) ctx.getBean("parser");
        System.out.println(parser.parse("1231313"));
        System.out.println(parser.parse("https://github.com/sanyarnd/tinkoff-java-course-2022/"));
        System.out.println(parser.parse("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c"));
        System.out.println(parser.parse("https://stackoverflow.com/search?q=unsupported%20link"));
        System.out.println(parser.parse("https://github.com/TivM/Lab8/tree/main/Lab8/lib"));

    }
}
