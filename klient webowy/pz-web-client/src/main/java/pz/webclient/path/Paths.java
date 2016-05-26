/**
 * 
 */
package pz.webclient.path;

/**
 * @author niemar
 * Common paths used by controllers and services to fetch data from rest api.
 */
public class Paths {
	public static final String METRICS = "metrics";
	public static final String METRIC_ID = "metricId";
	public static final String METRIC = METRICS + "/{" + METRIC_ID + "}";
	
	public static final String RESOURCES = "resources";
	public static final String RESOURCE_ID = "resourceId";
	public static final String RESOURCE = RESOURCES + "/{" + RESOURCE_ID + "}";

}
