import java.util.Scanner;
public class FrontEnd {
    final int SCREENWIDTH = 15;
    final int SCREENHEIGHT = 15;
    public static void main (String []args) {

        Scanner io = new Scanner(System.in);
        String input;

        Student st = new Student ("Tupac", 1);
        CourseCatalogue catalogue = new CourseCatalogue ();
        Course engg = catalogue.searchCat("ENGG", 233);
        catalogue.createCourseOffering(engg, 1, 100);
        catalogue.createCourseOffering(engg, 2, 200);
        System.out.println(catalogue);

        while (true) {
            System.out.println("/n/nCourse Registratin System");
            System.out.println("1. Search catalogue courses");
            System.out.println("2. Add course to student courses");
            System.out.println("3. Remove course from student courses");
            System.out.println("4. View All courses in catalogue");
            System.out.println("5. View all courses taken by student");
            System.out.println("6. Quit");

            input = io.nextLine();
            if (input.equals( "6")) {
                break;
            }
            else if (input.equals("1")) {
                System.out.println("Enter Course Name");
                String name = io.nextLine();
                int num = Integer.valueOf(io.nextLine());
                Course myCourse = catalogue.searchCat(name, num);
                if (myCourse == null) {
                    System.out.println("Course does not exist");
                    continue;
                }

                System.out.println(myCourse);
            }
            else if (input.equals( "2")) {
                System.out.println("Enter Course Name");
                String name = io.nextLine();
                int num = Integer.valueOf(io.nextLine());
                Course myCourse = catalogue.searchCat(name, num);
                if (myCourse == null) {
                    System.out.println("Course does not exist");
                    continue;
                }

                System.out.println("Enter course offering");
                int offering = Integer.valueOf(io.nextLine());
                if (myCourse.getCourseOfferingAt(offering) == null) {
                    System.out.println("Invalid Course Offering");
                    continue;
                }

                Registration reg = new Registration ();
                if (reg.completeRegistration(st, myCourse.getCourseOfferingAt(offering)) == true) {
                    System.out.println(reg);
                    continue;
                }

                System.out.println("Invalid Offering");
            }
            else {
                System.out.println("Invalid input: " + input);
            }
          //  else if (input.equals( "3") {
          //      System.out.println("Enter name of student");
          //      catalogue.removeReistration(student, offering);

          //  }
          //  else if (input.equals( "4"){

          //  }
            else if (input.equals("4")) {

            }



        }
    }

    public void drawMenu() {


    }
}
