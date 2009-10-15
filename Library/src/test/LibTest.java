package test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class LibTest extends TestCase {
	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTestSuite(BookTest.class);
		suite.addTestSuite(CustomerTest.class);
		suite.addTestSuite(LibraryTest.class);
		suite.addTestSuite(LibraryTest.class);
		suite.addTestSuite(TitleTest.class);
		return suite;
	}
}
