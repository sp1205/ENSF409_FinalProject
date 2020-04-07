package Server;

public class MessageBuilder implements StudentQueries {
	MessageBuilder() {
		
	}

    public String searchCourseMessage(String courseId) {
    	return searchCourse + messageDelimiter + courseId + messageDelimiter;
    }

    public String addCourseToStudentMessage(String studentId, String courseName, String courseId) {
    	return addCourseToStudent + messageDelimiter + studentId + messageDelimiter + courseName + messageDelimiter + courseId + messageDelimiter;
    }

    public String removeCourseFromStudentMessage(String studentId, String courseId) {
    	return removeCourseFromStudent + messageDelimiter + studentId + messageDelimiter + courseId + messageDelimiter;
    }

    public String listCoursesMessage() {
    	return listCourses + messageDelimiter;
    }

    public String allCoursesTakenByStudentMessage(String studentId) {
    	return allCoursesTakenByStudent + messageDelimiter + studentId + messageDelimiter;
    }

    public String quitMessage() {
    	return quit + messageDelimiter;
    }
}
