package ru.bogachenko.sessions;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.bogachenko.models.CatModel;
import ru.bogachenko.models.OwnerModel;
import ru.bogachenko.utils.HibernateSessionFactoryUtil;

import java.util.Set;


public class DaoCatImpl implements DaoCat {
    @Override
    public void createCat(CatModel cat) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(cat);
        tx1.commit();
        session.close();
    }
    @Override
    public CatModel readCat(long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        CatModel cat = session.get(CatModel.class, id);
        session.close();
        return cat;
    }

    @Override
    public void updateCat(CatModel cat, Long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        OwnerModel owner = new DaoOwnerImpl().readOwner(id);
        cat.setOwner(owner);
        session.merge(cat);
        tx1.commit();
        session.close();
    }

    @Override
    public void updateFriendship(CatModel cat) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(cat);
        tx1.commit();
        session.close();
    }

    @Override
    public void deleteCat(long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        CatModel cat = readCat(id);
        session.delete(cat);
        tx1.commit();
        session.close();
    }
    @Override
    public Set<CatModel> getCats(Long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        OwnerModel owner = (OwnerModel) session.load(OwnerModel.class, id);
        session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Set<CatModel> cats = owner.getOwnerCats();
        session.close();
        return cats;
    }
}
