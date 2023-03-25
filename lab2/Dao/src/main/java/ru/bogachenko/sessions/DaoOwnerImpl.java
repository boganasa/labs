package ru.bogachenko.sessions;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.bogachenko.models.CatModel;
import ru.bogachenko.models.OwnerModel;
import ru.bogachenko.utils.HibernateSessionFactoryUtil;

import java.util.List;
import java.util.Set;

public class DaoOwnerImpl implements DaoOwner {
    @Override
    public void createOwner(OwnerModel owner) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(owner);
        tx1.commit();
        session.close();
    }

    @Override
    public OwnerModel readOwner(long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        OwnerModel owner = session.get(OwnerModel.class, id);
        session.close();
        return owner;
    }

    @Override
    public void updateOwner(OwnerModel owner, Set<CatModel> newCats) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        owner.setCats(newCats);
        session.merge(owner);
        tx1.commit();
        session.close();
    }

    @Override
    public void deleteOwner(long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        OwnerModel owner = readOwner(id);
        session.delete(owner);
        tx1.commit();
        session.close();
    }
}
