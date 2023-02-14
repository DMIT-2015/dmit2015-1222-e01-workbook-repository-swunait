package common.config;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.annotation.sql.DataSourceDefinitions;
import jakarta.enterprise.context.ApplicationScoped;

@DataSourceDefinitions({

        @DataSourceDefinition(
                name = "java:app/datasources/h2databaseDS",
                className = "org.h2.jdbcx.JdbcDataSource",
                // url="jdbc:h2:file:~/jdk/databases/h2/DMIT2015_1222_CourseDB;MODE=LEGACY;",
                url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;MODE=LEGACY;",
                user = "user2015",
                password = "Password2015"),

//	@DataSourceDefinition(
//		name="java:app/datasources/LocalMssqlDMIT2015DS",
//		className="com.microsoft.sqlserver.jdbc.SQLServerDataSource",
//		url="jdbc:sqlserver://localhost;databaseName=DMIT2015_1222_CourseDB;TrustServerCertificate=true",
//		user="user2015",
//		password="Password2015"),
//
//	@DataSourceDefinition(
//		name="java:app/datasources/RemoteMssqlDMIT2015DS",
//		className="com.microsoft.sqlserver.jdbc.SQLServerDataSource",
//		url="jdbc:sqlserver://CAPSTONE1.dmit.sast.ca;databaseName=DMIT2015_1222_A01_yourNaitUsername;TrustServerCertificate=true", // replace yourNaitUsername
//		user="yourNaitUsername",	// replace yourNaitUsername
//		password="RemotePassword.200012345"), // replace 200012345 with your NAIT StudentID


//	@DataSourceDefinition(
//		name="java:app/datasources/oracleUser2015DS",
//		className="oracle.jdbc.xa.client.OracleXADataSource",
//		url="jdbc:oracle:thin:@localhost:1521/xepdb1",
//		user="user2015",
//		password="Password2015"),
//	@DataSourceDefinition(
//		name="java:app/datasources/oracleHrDS",
//		className="oracle.jdbc.xa.client.OracleXADataSource",
//		url="jdbc:oracle:thin:@localhost:1521/xepdb1",
//		user="HR",
//		password="Password2015"),
//
//	@DataSourceDefinition(
//		name="java:app/datasources/oracleOeDS",
//		className="oracle.jdbc.xa.client.OracleXADataSource",
//		url="jdbc:oracle:thin:@localhost:1521/xepdb1",
//		user="OE",
//		password="Password2015"),
//
//	@DataSourceDefinition(
//		name="java:app/datasources/oracleCoDS",
//		className="oracle.jdbc.xa.client.OracleXADataSource",
//		url="jdbc:oracle:thin:@localhost:1521/xepdb1",
//		user="CO",
//		password="Password2015"),

//	@DataSourceDefinition(
//		name="java:app/datasources/mysqlDS",
//		className="com.mysql.cj.jdbc.MysqlXADataSource",
//		url="jdbc:mysql://localhost/DMIT2015_1222_CourseDB",
//		user="user2015",
//		password="Password2015"),

//	@DataSourceDefinition(
//		name="java:app/datasources/mariadbDS",
//		className="org.mariadb.jdbc.MySQLDataSource",
//		url="jdbc:mariadb://localhost/DMIT2015_1222_CourseDB",
//		user="user2015",
//		password="Password2015"),

//	@DataSourceDefinition(
//		name="java:app/datasources/postgresqlDS",
//		className="org.postgresql.xa.PGXADataSource",
//		url="jdbc:postgresql://localhost/DMIT2015_1222_CourseDB",
//		user="user2015",
//		password="Password2015"),

//	@DataSourceDefinition(
//		name="java:app/datasources/hsqldatabaseDS",
//		className="org.hsqldb.jdbc.JDBCDataSource",
////		url="jdbc:hsqldb:mem:dmit2015hsqldb",
//		url="jdbc:hsqldb:file:~/jdk/databases/hsqldb/DMIT2015_1222_CourseDB",
//		user="user2015",
//		password="Password2015"),

})

@ApplicationScoped
public class ApplicationConfig {

}