package Database;

import Models.Course;

import java.sql.*;

interface CourseTableConstants {
    public static String sqlTableName = "CourseTable";
}

public class CourseTable extends Table implements CourseTableConstants{
    public ArrayList<Course> getCatalogue() {

    }

    public Course getCourse(int courseId)  {
        String statementStr = "SELECT * FROM " + CourseTableConstants.sqlTableName +
                              "WHERE ID=" + courseId;
        try {
            statement = getConnection().prepareStatement(statementStr);

        }
        catch (Exception e) {
            System.out.println("Exception: CourseTable::getCourse");
            e.printStackTrace();
        }
    }


}