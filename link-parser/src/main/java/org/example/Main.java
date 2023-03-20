package org.example;

public class Main { // тут непосредственно реализуется запуск парсера
    public static void main(String[] args) {
        String url1 = "https://github.com/melmelnikoff/Tink_Project/blob/master/.gitignore";
        Link link1 = LinkParser.parse(url1);
        System.out.println(link1.parse()); // Выводит "user + repo"

        String url2 = "https://stackoverflow.com/questions/75791735/cannot-execute-mvn-spring-boot-aplication-error-with-entity-manager-and-interfa";
        Link link2 = LinkParser.parse(url2);
        System.out.println(link2.parse()); // Выводит "номер вопроса"

        String url3 = "https://example.com";
        Link link3 = LinkParser.parse(url3);
        System.out.println(link3.parse()); // Выводит "null"
    }
}
