package common.config;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.annotation.sql.DataSourceDefinitions;
import jakarta.enterprise.context.ApplicationScoped;

@DataSourceDefinitions({

	@DataSourceDefinition(
		name="java:app/datasources/h2databaseDS",
		className="org.h2.jdbcx.JdbcDataSource",
//		url="jdbc:h2:file:~/jdk/databases/h2/DMIT2015_1221_CourseDB;MODE=LEGACY;",
		url="jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;MODE=LEGACY;",
		user="user2015",
		password="Password2015"),

//	@DataSourceDefinition(
//		name="java:app/datasources/hsqldatabaseDS",
//		className="org.hsqldb.jdbc.JDBCDataSource",
//		url="jdbc:hsqldb:mem:dmit2015hsqldb",
//		user="user2015",
//		password="Password2015"),


//	@DataSourceDefinition(
//		name="java:app/datasources/localMssqlDS",
//		className="com.microsoft.sqlserver.jdbc.SQLServerDataSource",
//		url="jdbc:sqlserver://localhost;databaseName=DMIT2015_1221_CourseDB;TrustServerCertificate=true",
//		user="user2015",
//		password="Password2015"),

//	@DataSourceDefinition(
//		name="java:app/datasources/remoteMssqlDS",
//		className="com.microsoft.sqlserver.jdbc.SQLServerDataSource",
//		url="jdbc:sqlserver://DMIT-Capstone1.ad.sast.ca;databaseName=DMIT2015_1221_E01_yourNaitUseranme;TrustServerCertificate=true",
//		user="yourNaitUsername",
//		password="RemotePassword.YourNaitStudentId"),

})

@ApplicationScoped
public class ApplicationConfig {

}