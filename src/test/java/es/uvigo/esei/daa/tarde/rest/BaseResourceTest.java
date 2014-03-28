package es.uvigo.esei.daa.tarde.rest;

import javax.ws.rs.core.Response;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.After;
import org.junit.Before;

public abstract class BaseResourceTest {

    protected static final int OK_CODE = Response.Status.OK.getStatusCode();

    protected JerseyTest jerseyTest;

    @Before
    public void setUpJerseyTest( ) throws Exception {
        jerseyTest = createJerseyTest();
        jerseyTest.setUp();
    }

    @After
    public void tearDownJerseyTest( ) throws Exception {
        jerseyTest.tearDown();
    }

    protected abstract JerseyTest createJerseyTest( );

}
