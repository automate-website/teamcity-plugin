package website.automate.plugins.teamcity.server.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class Mapper<S, T> {

    public abstract T map(S source);
    
    public T safeMap(S source){
        if(source == null){
            return null;
        }
        return map(source);
    }
    
    public List<T> safeMapCollection(Collection<S> sources){
        List<T> targets = new ArrayList<T>();
        if(sources == null){
            return Collections.emptyList();
        }
        for(S source : sources){
            targets.add(map(source));
        }
        return targets;
    }
}
