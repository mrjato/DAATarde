package es.uvigo.esei.daa.tarde.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Response;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.After;
import org.junit.Before;

public abstract class BaseResourceTest {

    protected static final int OK_CODE = Response.Status.OK.getStatusCode();

    protected JerseyTest  jerseyTest;
    private   Set<Object> resources = new HashSet<>();

    protected final void registerResourceUnderTest(final Object obj) {
        resources.add(obj);
    }

    @Before
    public final void setUpJerseyTest( ) throws Exception {
        jerseyTest = new JerseyTestBuilder().addResources(resources).build();
        jerseyTest.setUp();
    }

    @After
    public final void tearDownJerseyTest( ) throws Exception {
        jerseyTest.tearDown();
    }

}
