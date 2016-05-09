package pz.webclient.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MonitorsService {

	@Value("#{'${monitorsPrefixes}'.split(',')}") 
	private List<String> monitorsPrefixes;

	public List<String> getMonitorsPrefixes() {
		return monitorsPrefixes;
	}

	public void setMonitorsPrefixes(List<String> monitorsPrefixes) {
		this.monitorsPrefixes = monitorsPrefixes;
	}
}
