package website.automate.teamcity.agent.support;

import static java.util.Collections.singleton;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ContextParameterResolverTest {

    private static final String 
        ENV_PARAM_NAME = "automate.website.context.x",
        CONTEXT_PARAM_NAME = "x",
        CONTEXT_PARAM_VALUE = "y",
        NON_ENV_PARAM_NAME = "x",
        NON_CONTEXT_PARAM_VALUE = "z";
    
    private ContextParameterResolver resolver = ContextParameterResolver.getInstance();
    
    @Mock
    private Map<String, String> runnerParameters;
    
    @Test
    public void contextParameterIsExtracted(){
        when(runnerParameters.keySet()).thenReturn(singleton(ENV_PARAM_NAME));
        when(runnerParameters.get(ENV_PARAM_NAME)).thenReturn(CONTEXT_PARAM_VALUE);
        
        Map<String, String> context = resolver.resolve(runnerParameters);
        
        assertThat(context.get(CONTEXT_PARAM_NAME), is(CONTEXT_PARAM_VALUE));
    }
    
    @Test
    public void nonContextParamIsNotExtracted(){
        when(runnerParameters.keySet()).thenReturn(singleton(NON_ENV_PARAM_NAME));
        when(runnerParameters.get(NON_ENV_PARAM_NAME)).thenReturn(NON_CONTEXT_PARAM_VALUE);
        
        Map<String, String> context = resolver.resolve(runnerParameters);
        
        assertTrue(context.isEmpty());
    }
}
