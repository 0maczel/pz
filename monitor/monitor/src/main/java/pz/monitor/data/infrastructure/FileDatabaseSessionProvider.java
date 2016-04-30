package pz.monitor.data.infrastructure;

import org.hibernate.cfg.Configuration;

public class FileDatabaseSessionProvider extends CommonConfigurationSessionProvider {
	@Override
	protected void configure(Configuration configuration) {
		super.configure(configuration);
				
		configuration.setProperty("hibernate.connection.url", "jdbc:hsqldb:file:database/monitor;shutdown=true;hsqldb.default_table_type=cached");
			//.setProperty("hibernate.show_sql", "true")
	}
}
