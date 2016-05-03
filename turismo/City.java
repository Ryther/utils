package turismo;

import java.util.Map;
import java.util.Set;

/**
 *
 * @author Edoardo Zanoni
 */
public class City {
    
    private String id;
    private String name;
    private Set<String> poi;
    private Map<String, Link> links;
    
    private class Link {
        
        public City link;
        public int distance;
    }
    
    
}