package daos;

import com.google.inject.Inject;
import com.jrerdangjia.base.orm.AbstractDao;
import org.hibernate.Session;
import play.db.jpa.JPAApi;

/**
 * Created by wxji on 2016-12-23.
 */
public abstract class BaseDao<T> extends AbstractDao<T> {
    @Inject
    private JPAApi jpa;

    public BaseDao(Class<T> entityClass) {
        super(entityClass);
    }

    public void insert(T t) {
        this.getSession().save(t);
    }

    public void update(T t) {
        this.getSession().update(t);
    }

    public void delete(T t) {
        this.getSession().delete(t);
    }

    public JPAApi getJpa() {
        return jpa;
    }

    @Override
    public Session getSession() {
        return jpa.em().unwrap(Session.class);
    }
}
