package student;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Consumer;

/**
 * Display reminders of students having a birthday soon.
 * @author you
 */
public class StudentApp {

	/**
	 * Print the names (and birthdays) of students having a birtday in the
	 * specified month.
	 * @param students list of students
	 * @param month the month to use in selecting bithdays
	 */
	public void filterAndPrint( List<Student> students, Predicate<Student> filter, Consumer<Student> action, Comparator<Student> stdSort) {
		students.sort(stdSort);
		for(Student s : students ) {
			if (filter.test(s))
	                  action.accept(s);
		}
	}
	
	public static void main(String[] args) {
		LocalDate localDate = LocalDate.now();
		List<Student> students = Registrar.getInstance().getStudents();
		Predicate<Student> studentFilter = (s) -> s.getBirthdate().getMonthValue() == localDate.getMonthValue();
		Predicate<Student> week2Filter = (s) -> s.getBirthdate().getDayOfYear() >= localDate.getDayOfYear() &&
				s.getBirthdate().getDayOfYear() <= localDate.getDayOfYear()+14;
		Consumer<Student> studentAction = (s) -> System.out.printf("%s %s will have birthday on %d %s. \n"
				,s.getFirstname(),s.getLastname(),s.getBirthdate().getDayOfMonth(),localDate.getMonth());
		StudentApp app = new StudentApp();
		Comparator<Student> nameSort = (s1,s2) -> s1.getFirstname().charAt(0) - s2.getFirstname().charAt(0);
		Comparator<Student> birthdaySort = (s1,s2) -> s1.getBirthdate().getDayOfMonth() - s2.getBirthdate().getDayOfMonth();
		System.out.println("Sort by name: ");
		app.filterAndPrint(students, studentFilter, studentAction, nameSort);
		System.out.println("\nSort by birthday: ");
		app.filterAndPrint(students, studentFilter, studentAction, birthdaySort);
		System.out.println("\nList of people who will have birthday in two weeks: ");
		app.filterAndPrint(students, week2Filter, studentAction, birthdaySort);
	
	}
}
