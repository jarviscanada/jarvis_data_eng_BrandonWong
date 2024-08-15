package ca.jrvs.apps.practice;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JavaGrepImpTest {

    private JavaGrepImp mockedJavaGrepImp;

    @Before
    public void setup() {
       mockedJavaGrepImp = mock(JavaGrepImp.class);
    }

    @Test
    public void evaluateGetRootPath() {
        when(mockedJavaGrepImp.getRootPath()).thenReturn("C://test");
        Assert.assertEquals("C://test", mockedJavaGrepImp.getRootPath());
    }
}
