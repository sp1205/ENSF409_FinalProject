package backend.server;

/**
 * Used by clients to create server messages
 * 
 * @author Devon Vipond, Robyn Scholz, Janam Marthak
 * 
 */
public class MessageBuilder implements StudentQueries {
	public MessageBuilder() {
		
	}

    /**
     *
     * Returns a query message, which is used by the client.
     *
     * Server Response:
     * if course exists in database:
     *      1. successMessage()
     *      2. Course
     * else:
     *      1. error message (the client should display this on the GUI)
     *
     * @param courseName name of course
     * @return a properly structured query message
     *
     */
    public String searchCourseMessage(String courseName) {
    	return searchCourse + messageDelimiter + courseName + messageDelimiter;
    }

    /**
     *
     * Returns a query message, which is used by the client.
     *
     * Server Response:
     *      1. successMessage()
     *      2. ArrayList<Course> of courses taken by student
     * else:
     *      1. custom error message (client should display to GUI)
     * @param courseName name of course
     * @return a properly structured query message
     *
     */
    public String addCourseToStudentMessage( String courseName) {
    	return addCourseToStudent + messageDelimiter +  courseName + messageDelimiter;
    }

    /**
     * Returns a query message, which is used by the client.
     *
     * Server Response:
     * if successful:
     *      1. successMessage()
     *      2. ArrayList<Course> of courses taken by student
     * else:
     *      1. custom error message (client should display to GUI)
     * @param courseName name of course
     * @return a properly structured query message
     *
     */
    public String removeCourseFromStudentMessage( String courseName) {
    	return removeCourseFromStudent + messageDelimiter +  courseName + messageDelimiter;
    }

    /**
     * Returns a query message, which is used by the client.
     *
     * Server Response:
     * if successful:
     *      1. successMessage()
     *      2. ArrayList<Course> of courses taken by student
     * else:
     *      1. custom error message (client should display to GUI)
     * @return a properly structured query message
     *
     */
    public String listCoursesMessage() {
    	return listCourses + messageDelimiter;
    }

    /**
     * Returns a query message, which is used by the client.
     *
     * Server Response:
     * if successful:
     *      1. successMessage()
     *      2. ArrayList<Course> of courses taken by student
     * else:
     *      1. custom error message (client should display to GUI)
     * @return a properly structured query message
     *
     */
    public String allCoursesTakenByStudentMessage() {
    	return allCoursesTakenByStudent + messageDelimiter ;
    }

    /**
     * Terminates connection from server to client
     * @return a properly structured query message
     */
    public String quitMessage() {
    	return quit + messageDelimiter;
    }

    /**
     * After the server handles a query successfully, this message will be returned to the client
     * @return a properly structured query message
     */
    public String successMessage() {
        return success + messageDelimiter;
    }

    /**
     * Returns a query message, which is used by the client.
     *
     * Server Response:
     * if successful:
     *      1. successMessage()
     * else:
     *      1. custom error message (client should display to GUI)
     * @param studentName name of student
     * @return a properly structured query message
     *
     */
    public String loginMessage(String studentName) {
	    return studentName + messageDelimiter;
    }
}
