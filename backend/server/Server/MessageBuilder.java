package Server;

public class MessageBuilder implements StudentQueries {
	public MessageBuilder() {
		
	}

    public String searchCourseMessage(String courseId) {
    	return searchCourse + messageDelimiter + courseId + messageDelimiter;
    }

    public String addCourseToStudentMessage( String courseId) {
    	return addCourseToStudent + messageDelimiter +  courseId + messageDelimiter;
    }

    public String removeCourseFromStudentMessage( String courseId) {
    	return removeCourseFromStudent + messageDelimiter +  courseId + messageDelimiter;
    }

    public String listCoursesMessage() {
    	return listCourses + messageDelimiter;
    }

    public String allCoursesTakenByStudentMessage() {
    	return allCoursesTakenByStudent + messageDelimiter ;
    }

    public String quitMessage() {
    	return quit + messageDelimiter;
    }
    
    public String errorMessage() {
    	return error + messageDelimiter;
    }
}
