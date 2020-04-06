package Database;

import Models.Course;

import java.io.*;

import java.sql.*;

interface CourseTableConstants {
    public static String CourseTableName = "CourseTable";
    public static String CourseNum = "Id";
    public static String CourseName = "Name";
    public static String SQLSearchCourse = "SELECT * FROM " + CourseTableConstants.CourseTableName +
                              "WHERE ID=?";
    public static String SQLAddCourse = "INSERT INTO " + CourseTableConstants.CourseTableName + 
    						  " VALUES (? ,?);";
    public static String SQLCatalogue = "SELECT * FROM " + CourseTableConstants.CourseTableName;
}

public class CourseTable extends Table implements CourseTableConstants{
    public ArrayList<Course> getCatalogue() {
    	try {
    		ArrayList<Course> list = new ArrayList<Course>;
    		statement = getConnection().prepareStatement(CourseTableConstants.SQLCatalogue);
    		ResultSet result = statement.executeQuery();
    		
    		
    		boolean flag = false;
    		while (result.next() ) {
    			flag = true;
    			list.add(new Course(result.getString(CourseTableConstants.CourseName),
							  result.getInt(CourseTableConstants.CourseNum));
    		}

    		if (flag == false) {
    			System.out.println("CourseTable: No courses in CourseTable");
    			return null;
    		}
    		
    		result.close();
    		statement.close();
    		return list;
    	}
    	catch (Exception e) {
    		System.out.println("CourseTable: Unable to retreive course catalogue");
    		e.printStackTrace();
    	}

    	return null;
    }
    
    public void addCourse(Course course) {
    	try {
    		statement = getConnection().prepareStatement(CourseTableConstants.SQLAddCourse);
    		statement.setInt(course.getCourseNum());
    		statement.setInt(course.getCourseName());
    		
    		statement.executeUpdate();
    		statement.close();
    	}
    	catch (Exception e) {
            System.out.println("Exception: CourseTable::addCourse");
            e.printStackTrace();
    	}
    	
    	System.out.println("CourseTable: Unable to add course " + course.getCourseName());
    	return null;
    }

    public Course getCourse(int courseId)  {
        try {
        	ResultSet result;
            statement = getConnection().prepareStatement(CourseTableConstants.SQLSearchCourse );
            statement.sentInt(1, courseId);
            
            result = statement.executeQuery();

            if (!statement.next()) {
				System.out.println("CourseTable: Unable to retreive course: " + courseId);
            	return null;
            }

            
            Course c = new Course(result.getString(CourseTableConstants.CourseName),
							  result.getInt(CourseTableConstants.CourseNum);
            
            statement.close();
            result.close();
        }
        catch (Exception e) {
            System.out.println("Exception: CourseTable::getCourse");
            e.printStackTrace();
        }
        
        System.out.println("CourseTable: Unable to retreive course: " + courseId);
        return null;
    }
    

}