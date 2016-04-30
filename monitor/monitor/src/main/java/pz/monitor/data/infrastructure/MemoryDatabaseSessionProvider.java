package pz.monitor.data.infrastructure;

import org.hibernate.cfg.Configuration;

public class MemoryDatabaseSessionProvider extends CommonConfigurationSessionProvider {
	protected void configure(Configuration configuration) {
		super.configure(configuration);
		
		configuration.setProperty("hibernate.connection.url", "jdbc:hsqldb:mem:database/monitor;shutdown=true");
	}
}
