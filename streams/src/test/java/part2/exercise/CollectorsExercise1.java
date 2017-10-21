package part2.exercise;

import data.Employee;
import data.JobHistoryEntry;
import data.Person;
import javafx.util.Pair;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class CollectorsExercise1 {

    private static class PersonPositionDuration {
        private final Person person;
        private final String position;
        private final int duration;

        public PersonPositionDuration(Person person, String position, int duration) {
            this.person = person;
            this.position = position;
            this.duration = duration;
        }

        public Person getPerson() {
            return person;
        }

        public String getPosition() {
            return position;
        }

        public int getDuration() {
            return duration;
        }
    }


    // "epam" -> "Alex Ivanov 23, Semen Popugaev 25, Ivan Ivanov 33"
    @Test
    public void getEmployeesByEmployer() {
        List<Employee> employees =getEmployees();
        Map<String, String> result = employees.stream()
                .flatMap(e -> e.getJobHistory().stream().map(h -> new Pair<>(e,h)))
                .collect(Collectors
                        .groupingBy(p -> p.getValue().getEmployer(), Collectors.mapping(p -> p.getKey().getPerson().toString(),Collectors.joining(","))));
    }

    @Test
    public void getTheCoolestOne() {
        Map<String, Person> coolestByPosition = getCoolestByPosition(getEmployees());

        coolestByPosition.forEach((position, person) -> System.out.println(position + " -> " + person));
    }

    private Map<String, Person> getCoolestByPosition(List<Employee> employees) {
        Comparator<JobHistoryEntry> comparator = (e1,e2) -> Integer.compare(e1.getDuration(),e2.getDuration());
        Map<String,Person> result = employees.stream()
                .flatMap(e -> e.getJobHistory().stream().map(h -> new Pair<>(e,h)))
                .collect(Collectors.groupingBy(p -> p.getValue().getPosition(),Collectors.collectingAndThen(
                        Collectors.maxBy((p1,p2) -> comparator.compare(p1.getValue(),p2.getValue())),p -> p.isPresent()? p.get().getKey().getPerson() : null)));
        // First option
        // Collectors.maxBy
        // Collectors.collectingAndThen
        // Collectors.groupingBy

        // Second option
        // Collectors.toMap
        // iterate twice: stream...collect(...).stream()...
        // TODO
        return result;
    }

    private List<Employee> getEmployees() {
        return Arrays.asList(
                new Employee(
                        new Person("John", "Galt", 20),
                        Arrays.asList(
                                new JobHistoryEntry(3, "dev", "epam"),
                                new JobHistoryEntry(2, "dev", "google")
                        )),
                new Employee(
                        new Person("John", "Doe", 21),
                        Arrays.asList(
                                new JobHistoryEntry(4, "BA", "yandex"),
                                new JobHistoryEntry(2, "QA", "epam"),
                                new JobHistoryEntry(2, "dev", "abc")
                        )),
                new Employee(
                        new Person("John", "White", 22),
                        Collections.singletonList(
                                new JobHistoryEntry(6, "QA", "epam")
                        )),
                new Employee(
                        new Person("John", "Galt", 23),
                        Arrays.asList(
                                new JobHistoryEntry(3, "dev", "epam"),
                                new JobHistoryEntry(2, "dev", "google")
                        )),
                new Employee(
                        new Person("John", "Doe", 24),
                        Arrays.asList(
                                new JobHistoryEntry(4, "QA", "yandex"),
                                new JobHistoryEntry(2, "BA", "epam"),
                                new JobHistoryEntry(2, "dev", "abc")
                        )),
                new Employee(
                        new Person("John", "White", 25),
                        Collections.singletonList(
                                new JobHistoryEntry(6, "QA", "epam")
                        )),
                new Employee(
                        new Person("John", "Galt", 26),
                        Arrays.asList(
                                new JobHistoryEntry(3, "dev", "epam"),
                                new JobHistoryEntry(1, "dev", "google")
                        )),
                new Employee(
                        new Person("Bob", "Doe", 27),
                        Arrays.asList(
                                new JobHistoryEntry(4, "QA", "yandex"),
                                new JobHistoryEntry(2, "QA", "epam"),
                                new JobHistoryEntry(2, "dev", "abc")
                        )),
                new Employee(
                        new Person("John", "White", 28),
                        Collections.singletonList(
                                new JobHistoryEntry(6, "BA", "epam")
                        )),
                new Employee(
                        new Person("John", "Galt", 29),
                        Arrays.asList(
                                new JobHistoryEntry(3, "dev", "epam"),
                                new JobHistoryEntry(1, "dev", "google")
                        )),
                new Employee(
                        new Person("John", "Doe", 30),
                        Arrays.asList(
                                new JobHistoryEntry(4, "QA", "yandex"),
                                new JobHistoryEntry(2, "QA", "epam"),
                                new JobHistoryEntry(5, "dev", "abc")
                        )),
                new Employee(
                        new Person("Bob", "White", 31),
                        Collections.singletonList(
                                new JobHistoryEntry(6, "QA", "epam")
                        ))
        );
    }

}
