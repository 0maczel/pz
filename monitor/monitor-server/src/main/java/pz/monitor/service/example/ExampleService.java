package pz.monitor.service.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pz.monitor.db.entity.Example;

// TODO remove
/**
 * Testowy serwis
 * 
 * @author Invader
 *
 */
@RestController
@Transactional
public class ExampleService
{
    @Autowired
    private ExampleDao dao;

    @RequestMapping(path = "/storeRandom", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public Example store()
    {
        return dao.store();
    }

    @RequestMapping(path = "/example/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public Example get(@PathVariable Long id)
    {
        return dao.get(id);
    }
}
