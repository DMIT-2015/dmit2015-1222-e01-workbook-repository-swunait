package common.config;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.annotation.sql.DataSourceDefinitions;
import jakarta.enterprise.context.ApplicationScoped;

@DataSourceDefinitions({

	@DataSourceDefinition(
		name="java:app/datasources/mssqlDS",
		className="com.microsoft.sqlserver.jdbc.SQLServerDataSource",
		url="jdbc:sqlserver://localhost;databaseName=DMIT2015_1222_CourseDB;TrustServerCertificate=true",
		user="user2015",
		password="Password2015"),

//	@DataSourceDefinition(
//		name="java:app/datasources/oracleUser2015DS",
//		className="oracle.jdbc.xa.client.OracleXADataSource",
//		url="jdbc:oracle:thin:@localhost:1521/XEPDB1",
//		user="user2015",
//		password="Password2015"),

})

@ApplicationScoped
public class ApplicationConfig {

}