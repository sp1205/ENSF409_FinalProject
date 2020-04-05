
public class RegistrationApp {
	
	public static void main (String [] args) {
		CourseCatalogue cat = new CourseCatalogue ();
		System.out.println(cat);
		Student st = new Student ("Sara", 1);
		Student st2 = new Student ("Sam", 2);
		Course myCourse = cat.searchCat("ENGG", 233);
		if (myCourse != null) {
			cat.createCourseOffering(myCourse, 1, 100);
			cat.createCourseOffering(myCourse, 2, 200);
		}
		System.out.println(myCourse.getCourseOfferingAt(0));
		
		Registration reg = new Registration ();
		reg.completeRegistration(st, myCourse.getCourseOfferingAt(0));
		
		System.out.println(reg);

		
	}

}
