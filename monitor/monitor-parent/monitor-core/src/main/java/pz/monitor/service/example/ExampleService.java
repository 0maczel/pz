package pz.monitor.service.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pz.monitor.db.Example;

// TODO remove
/**
 * Testowy serwis
 * 
 * @author Invader
 *
 */
@RestController
public class ExampleService
{
    @Autowired
    private ExampleDao dao;

    @RequestMapping(path = "/example", produces = MediaType.APPLICATION_JSON_VALUE)
    public Example hello()
    {
        return dao.get();
    }
}
