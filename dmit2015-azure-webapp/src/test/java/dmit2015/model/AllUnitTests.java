package dmit2015.model;

import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;
@Suite
@SuiteDisplayName("JUnit 5 Platform Suite")
@SelectPackages("dmit2015.model")
@IncludeClassNamePatterns(".*Test")
//@SelectClasses({CircleTest.class, RectangleTest.class})
public class AllUnitTests
{
// the class remains completely empty,
// being used only as a holder for the above annotations
}