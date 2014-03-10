package de.professional_webworkx.blog.sp;

import de.professional_webworkx.blog.sp.connector.JDBCConnector;

public class App 
{
    public static void main( String[] args )
    {
        JDBCConnector connector = JDBCConnector.getInstance();
        connector.addStoredProcedure();
    }
}
