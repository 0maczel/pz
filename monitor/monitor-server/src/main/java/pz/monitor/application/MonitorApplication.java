package pz.monitor.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * Główna klasa startowa aplikacji. Póki co (i być może na stałe) startuje
 * jako aplikacja konsolowa.
 * <p>
 * Generalnie preferowana jest automatyczna konfiguracja (autowiring etc).<br>
 * Wszystko co chciałem podać jawnie, znajduje się na classpath w plikach
 * <ul>
 *  <li>application-context.xml - minimalna konfiguracja</li>
 *  <li>monitor.properties - póki co, zaszyta tutaj konfiguracja bazy</li>
 * </ul>
 * </p>
 * 
 * @author Invader
 *
 */
@SpringBootApplication
@ImportResource("application-context.xml")
public class MonitorApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(MonitorApplication.class, args);
    }
}
