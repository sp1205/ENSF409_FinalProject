package Server;

public class MessageBuilder implements StudentQueries {
	public MessageBuilder() {
		
	}

    /**
     *
     * @param courseId
     * @return
     *
     */
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

    // Currently not used. Ignore for now
    public String errorMessage() {
    	return error + messageDelimiter;
    }

    public String failMessage() {
	    return failed + messageDelimiter;
    }

    public String successMessage() {
        return success + messageDelimiter;
    }

    public String loginMessage(String studentName, String studentId) {
	    return studentName + messageDelimiter + studentId + messageDelimiter;
    }
}
