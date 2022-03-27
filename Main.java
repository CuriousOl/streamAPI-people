package com.learningJava;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
    /*
    Из коллеции объектов Person необходимо:
    1) Найти количество несовершеннолетних (т.е. людей младше 18 лет).
    2) Получить список фамилий призывников (т.е. мужчин от 18 и до 27 лет).
    3) Получить отсортированный по фамилии список потенциально работоспособных людей с высшим образованием в выборке
    (т.е. людей с высшим образованием от 18 до 60 лет для женщин и до 65 лет для мужчин).
     */
        Collection<Person> people = createPeople(10_000_000);
        //1)
        long countPeopleLess18 = people.stream()
                .filter(p -> p.getAge() < 18)
                .count();

//        System.out.println("Количество несовершеннолетних: " + countPeopleLess18);

        //2)
        List<String> conscripts = people.stream()
                .filter(p -> (p.getAge() >= 18 && p.getAge() <= 27))
                .map(p -> p.getFamily())
                .collect(Collectors.toList());

//        System.out.println("Призывники: ");
//        System.out.println(conscripts);

        //3)
        List<Person> workable = people.stream()
                .filter(p -> p.getEducation() == Education.HIGHER && p.getAge() >= 18)
                .filter(p -> (p.getSex() == Sex.MAN && p.getAge() <= 60) || (p.getSex() == Sex.WOMAN && p.getAge() <= 65))
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());

//        System.out.println("Работоспособные: ");
//        System.out.println(workable);

    }

    public static Collection<Person> createPeople(int count) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> people = new ArrayList<>();
        int sizeNames = names.size();
        int familiesSize = families.size();
        int sexes = Sex.values().length;
        int educations = Education.values().length;

        for (int i = 0; i < count; i++) {
            people.add(new Person(
                    names.get(new Random().nextInt(sizeNames)),
                    families.get(new Random().nextInt(familiesSize)),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(sexes)],
                    Education.values()[new Random().nextInt(educations)])
            );
        }
        return people;
    }
}
