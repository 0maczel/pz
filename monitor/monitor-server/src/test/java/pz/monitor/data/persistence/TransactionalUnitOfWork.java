package pz.monitor.data.persistence;

import java.util.function.Consumer;
import java.util.function.Function;

import org.hibernate.Session;
import org.hibernate.Transaction;

import pz.monitor.data.infrastructure.SessionProvider;
import pz.monitor.db.Repository;

public class TransactionalUnitOfWork implements UnitOfWork
{
    SessionProvider sessionProvider;

    public TransactionalUnitOfWork(SessionProvider sessionProvider)
    {
        this.sessionProvider = sessionProvider;
    }

    @Override
    public void doWork(Consumer<Repository> work)
    {
        doWork(repository -> {
            work.accept(repository);
            return null;
        });
    }

    @Override
    public <T> T doWork(Function<Repository, T> work)
    {
        T returnValue;
        Session session = sessionProvider.openSession();
        Transaction tx = null;
        try
        {
            tx = session.beginTransaction();
            returnValue = work.apply(new TestCRUDRepository(session));
            tx.commit();
        }
        catch (Exception e)
        {
            if (tx != null)
                tx.rollback();
            throw e;
        }
        finally
        {
            session.close();
        }
        return returnValue;
    }
}
