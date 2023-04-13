package common.security;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.apache.directory.api.ldap.model.cursor.EntryCursor;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.exception.LdapAuthenticationException;
import org.apache.directory.api.ldap.model.message.SearchScope;
import org.apache.directory.api.ldap.model.name.Dn;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Microprofile JWT Authentication Steps
 *
 * Step 1: Generate the public/private key pair for signing and verification.
 * Generate a private key file named jwt-private.pem using the openssl command from a Terminal:
 *
 mkdir -p ~/keys
 openssl genpkey -algorithm RSA -out ~/keys/jwt-private.pem -pkeyopt rsa_keygen_bits:2048
 *
 * Export the public key that will be included in the deployment using the openssl command from a Terminal:
 *
 openssl rsa -in ~/keys/jwt-private.pem -pubout -out src/main/resources/META-INF/jwt-jwt-public.pem
 *
 * Step 2: Add the TokenUtil.java class that is used to generate JWT tokens.
 *
 * Step 3: Activate Microprofile JWT by adding the @LoginConfig annotation as follows:
 * <code>
 @ApplicationPath("restapi")
 @LoginConfig(authMethod="MP-JWT", realmName="MP JWT Realm")
 @DeclareRoles({"ROLE1","ROLE2","ROLE3"})
 public class JAXRSConfiguration extends Application { }
 * </code>
 * Step 4: Open the Microprofile Config properties file 'src/main/resources/META-INF/microprofile-config.properties'
 * and add the following key/value pairs and
 * change the value of the property jwt.privatekey.filepath with the location of your JWT private key file:
 * <code>
mp.jwt.verify.publickey.location=META-INF/jwt-jwt-public.pem
mp.jwt.verify.issuer=quickstart-jwt-issuer
jwt.privatekey.filepath=/home/user/user2015/keys/jwt-private.pem
 * </code>
 *
 * Run your application on the server and use the curl command to verify that a JWT token is returned when a valid username and password is posted to the server.
 * You can POST JSON data to the server as follows:
 *
 curl -k -i -X POST https://localhost:8443/restapi/jwt/jsonLogin \
	-d '{"username":"DLEE","password":"Password2015"}' \
	-H 'Content-Type:application/json'

 curl -k -i -X POST https://localhost:8443/restapi/jwt/jsonLogin \
	-d '{"username":"DAUSTIN","password":"Password2015"}' \
	-H 'Content-Type:application/json'

  *
 * You can also POST FORM data to the server as follows:
 curl -k -i -X POST https://localhost:8443/restapi/jwt/formLogin/ \
	-d 'j_username=DLEE&j_password=Password2015' \
	-H 'Content-Type:application/x-www-form-urlencoded'
 *
 * @author https://github.com/wildfly/quickstart/tree/master/microprofile-jwt
 * @version 2021.03.17
 *
 */

@RequestScoped
@Path("jwt")
public class JwtResource {

	private Logger _logger = Logger.getLogger(JwtResource.class.getName());

	@Inject
	private CallerUserRepository callerUserRepository;	// for accessing our database of users

	@Inject
	private Pbkdf2PasswordHash passwordHash;	// for hashing the plain text password to cipher text

	@Inject
	private JsonWebToken callerPrincipal;		// CDI managed bean must be @RequestScoped to inject JWT token

	// Read from micrprofile-config.properties the location of the private key used to generated JWT tokens
	@Inject
	@ConfigProperty(name = "jwt.privatekey.filepath")
	private String privateKeyPath;

	/**
	 * Generate and return a JWT bearer token where the username and password are posted as FORM fields named j_username and j_password, respectively.
	 *
	 * @param username The j_username form field value
	 * @param password The j_password form field value
	 * @param servletContext Injected by the container to allow use to read context parameters from web.xml
	 * @return
	 */
	@Path("formLogin")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	@POST
	public Response formLogin(
			@FormParam("j_username") String username,
			@FormParam("j_password") String password,
			@Context ServletContext servletContext) {
		JsonObject credential = Json.createObjectBuilder()
				.add("username", username)
				.add("password", password)
				.build();
		return jsonLogin(credential, servletContext);
	}

	/**
	 * Generate and return a JWT bearer token for the given username and password.
	 *
	 * @param credential A JSON object with the username and password used for authentication
	 * @param servletContext Injected by the container to allow use to read context parameters from web.xml
	 * @return A JWT beear token
	 */
	@POST
	@Path("jsonLogin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response jsonLogin(JsonObject credential, @Context ServletContext servletContext) {
		String username = credential.getString("username");
		String password = credential.getString("password");

		Optional<CallerUser> optionalCallerUser = callerUserRepository.findById(username);
		if (optionalCallerUser.isPresent()) {
			CallerUser existingCallerUser = optionalCallerUser.get();
			if (passwordHash.verify(password.toCharArray(), existingCallerUser.getPassword())) {
				String[] groups = existingCallerUser.getGroups().toArray(String[]::new);
				try {
					String token = TokenUtil.generateJWT(privateKeyPath, username, groups);
					return Response.ok(token).build();
				} catch (Exception e) {
					e.printStackTrace();
					return Response.serverError().entity(e.getMessage()).build();
				}
			}
		}

		String message = "Incorrect username and/or password.";
		return Response.status(Status.BAD_REQUEST).entity(message).build();
	}


	// Read from microprofile-config.properties the LDAP server
	@Inject
	@ConfigProperty(name = "ldap.server")
	private String ldapServer;

	// Read from microprofile-config.properties the LDAP account username with permissions to search for users in the LDAP directory.
	@Inject
	@ConfigProperty(name = "ldap.bind.name")
	private String ldapBindName;

	// Read from microprofile-config.properties the LDAP account password with permissions to search for users in the LDAP directory.
	@Inject
	@ConfigProperty(name = "ldap.bind.password")
	private String ldapBindPassword;

	// Read from microprofile-config.properties the distinguished name location where to search for accounts
	@Inject
	@ConfigProperty(name = "ldap.search.dn")
	private String ldapSearchDn;


	/**
	 * Validate the username and password with Windows Active Directory.
	 *
	 * This method assumes that you have added the Apache Directory LDAP API maven dependency to pom.xml
	 *
		<dependency>
		  <groupId>org.apache.directory.api</groupId>
		  <artifactId>api-all</artifactId>
		  <version>2.1.2</version>
		</dependency>
	 *
	 * This method assumes that you have define the following properties in src/main/resources/META-INF/microprofile.config
	 *
ldap.server=192.168.101.159
ldap.bind.name=cn=DAUSTIN,ou=IT,ou=Departments,dc=dmit2015,dc=ca
ldap.bind.password=Password2015
ldap.search.dn=ou=Departments,dc=dmit2015,dc=ca
	 *
	 * @param credential a JSON object with a username and a password property
	 * @param servletContext the ServletContext currently being accessed
	 * @return the generated JWT token for the account username and password
	 */
	@POST
	@Path("ldapJsonLogin")
	public Response ldapJsonLogin(JsonObject credential, @Context ServletContext servletContext) {
		// Get from the JsonObject value for the username and password property.
		String username = credential.getString("username");
		String password = credential.getString("password");

		// Connect to the LDAP server
		try (LdapConnection connection = new LdapNetworkConnection(ldapServer)) {
			// Bind to the LDAP server using the lookup account
			connection.bind(ldapBindName,ldapBindPassword);

			// Search for the following attributes of the user:
			// 	"memberOf",				// Security groups that the user is a member of
			// 	"cn",					// Display Name
			// 	"givenName",			// First Name
			// 	"sn",					// Last Name
			// 	"sAMAccountName",		// username
			// 	"userPrincipalName"		// username@domain
			Dn searchDn = new Dn(ldapSearchDn);
			String[] attributes = {"memberOf","cn","givenName","sn","sAMAccountName","userPrincipalName"};
			// Search for the user using their sAMAccountName
			EntryCursor cursor = connection.search( searchDn, "(sAMAccountName=" + username + ")", SearchScope.SUBTREE, attributes);
			// Create a boolean flag to track if any search results were returned
			boolean foundAccount = false;
			for (Entry entry : cursor) {
				foundAccount = true;

				try {
					// Validate password by binding with with LDAP
					connection.bind(entry.getDn(), password);
					_logger.info("Password is valid");

					// Find all the security group names that the account is a member of
					// Create a new set of String to store all the security group name the account is a member of
					Set<String> memberOfSet = new HashSet<>();
					// The memberOf attribute contains the security group names
					String memberOf = entry.get("memberOf").toString();
					// Remove the memberOf value and convert the single line value into an array of values where each line is a different security group
					String[] memberOfArray = memberOf.replaceAll("memberOf: ", "").split("\n");
					for (String singleMemberOf : memberOfArray) {
						// Extract just the security group name
						String memberOfName = singleMemberOf.substring(3, singleMemberOf.indexOf(",OU="));
						memberOfSet.add(memberOfName);
						_logger.info("Added role group: " + memberOfName);
					}

					String[] groups = memberOfSet.toArray(String[]::new);
					String token = TokenUtil.generateJWT(privateKeyPath, username, groups);

					String[] parts = token.split("\\.");
					_logger.info(String.format("\nJWT Header - %s", new String(Base64.getDecoder().decode(parts[0]), StandardCharsets.UTF_8)));
					_logger.info(String.format("\nJWT Claims - %s", new String(Base64.getDecoder().decode(parts[1]), StandardCharsets.UTF_8)));
					_logger.info(String.format("\nGenerated JWT Token \n%s\n", token));

					return Response.ok(token).build();
				} catch (LdapAuthenticationException e) {
					_logger.info("Invalid password");
					return Response.status(Status.BAD_REQUEST).entity("Invalid password").build();
				}
			}
			if (!foundAccount) {
				return Response.status(Status.BAD_REQUEST).entity("Invalid username").build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage()).build();
		}
		return Response.status(Status.BAD_REQUEST).entity("Invalid username and/or password.").build();
	}


}