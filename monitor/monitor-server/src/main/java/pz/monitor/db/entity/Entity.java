package pz.monitor.db.entity;

import java.sql.Timestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Entity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    private int entityVersion;
    @CreationTimestamp
    private Timestamp creationTimestamp;
    @UpdateTimestamp
    private Timestamp updateTimestamp;

    // TODO czy setter do Id jest potrzebny?
    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public int getEntityVersion()
    {
        return entityVersion;
    }

    public Timestamp getCreationTimestamp()
    {
        return creationTimestamp;
    }

    public Timestamp getUpdateTimestamp()
    {
        return updateTimestamp;
    }
}
