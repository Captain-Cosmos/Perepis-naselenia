import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");

        Collection<Person> persons = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(random.nextInt(names.size())),
                    families.get(random.nextInt(families.size())),
                    random.nextInt(100),
                    Sex.values()[random.nextInt(Sex.values().length)],
                    Education.values()[random.nextInt(Education.values().length)])
            );
        }

        // Несовершеннолетние
        long underageCount = persons.stream()
                .filter(person -> person.getAge() < 18)
                .count();
        System.out.println("Количество несовершеннолетних: " + underageCount);

        // Призывники
        List<String> draftEligibleFamilies = persons.stream()
                .filter(person -> person.getSex() == Sex.MAN && person.getAge() >= 18 && person.getAge() <= 27)
                .map(Person::getFamily)
                .collect(Collectors.toList());
        System.out.println("Количество призывников: " + draftEligibleFamilies.size());

        // Работоспособные люди с высшим образованием
        List<Person> workingAgePeople = persons.stream()
                .filter(person -> person.getEducation() == Education.HIGHER)
                .filter(person -> {
                    int retirementAge = (person.getSex() == Sex.WOMAN) ? 60 : 65;
                    return person.getAge() >= 18 && person.getAge() <= retirementAge;
                })
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());
        System.out.println("Количество трудоспособных людей с высшим образованием: " + workingAgePeople.size());
    }
}