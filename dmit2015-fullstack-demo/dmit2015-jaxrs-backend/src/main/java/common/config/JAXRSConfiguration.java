package common.config;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
//import org.eclipse.microprofile.auth.LoginConfig;

@ApplicationPath("restapi")
//@LoginConfig(authMethod="MP-JWT", realmName="MP JWT Realm")
public class JAXRSConfiguration extends Application {

}