package com.smart.dao.hibernate;

import com.pims.webapp.util.VerificaDate;
import com.smart.Constants;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.smart.dao.GenericDao;
import com.smart.dao.SearchException;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.ObjectRetrievalFailureException;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.InstantiationException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.annotation.Resource;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.util.Version;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;

/**
 * This class serves as the Base class for all other DAOs - namely to hold
 * common CRUD methods that they might all use. You should only need to extend
 * this class when your require custom CRUD logic.
 * <p/>
 * <p>To register this class in your Spring context file, use the following XML.
 * <pre>
 *      &lt;bean id="fooDao" class="com.smart.dao.hibernate.GenericDaoHibernate"&gt;
 *          &lt;constructor-arg value="com.smart.model.Foo"/&gt;
 *      &lt;/bean&gt;
 * </pre>
 *
 * @param <T>  a type variable
 * @param <PK> the primary key for that type
 * @author <a href="mailto:bwnoll@gmail.com">Bryan Noll</a>
 *         Updated by jgarcia: update hibernate3 to hibernate4
 * @author jgarcia (update: added full text search + reindexing)
 */
public class GenericDaoHibernate<T, PK extends Serializable> implements GenericDao<T, PK> {
    /**
     * Log variable for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
     */
    protected final Log log = LogFactory.getLog(getClass());
    private Class<T> persistentClass;
    @Resource
    private SessionFactory sessionFactory;
    private Analyzer defaultAnalyzer;

    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing.
     *
     * @param persistentClass the class type you'd like to persist
     */
    public GenericDaoHibernate(final Class<T> persistentClass) {
        this.persistentClass = persistentClass;
        defaultAnalyzer = new StandardAnalyzer(Version.LUCENE_35);
    }

    /**
     * Constructor that takes in a class and sessionFactory for easy creation of DAO.
     *
     * @param persistentClass the class type you'd like to persist
     * @param sessionFactory  the pre-configured Hibernate SessionFactory
     */
    public GenericDaoHibernate(final Class<T> persistentClass, SessionFactory sessionFactory) {
        this.persistentClass = persistentClass;
        this.sessionFactory = sessionFactory;
        defaultAnalyzer = new StandardAnalyzer(Version.LUCENE_35);
    }

    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    public Session getSession() {
        Session sess = null;
        try {
            sess = getSessionFactory().getCurrentSession();
        } catch (Exception e) {
            sess = getSessionFactory().openSession();
        }
        return sess;
    }

    @Autowired
    @Required
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        Session sess = getSession();
        return sess.createCriteria(persistentClass).list();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> getAllDistinct() {
        Collection<T> result = new LinkedHashSet<T>(getAll());
        return new ArrayList<T>(result);
    }

    /**
     * {@inheritDoc}
     */
    public List<T> search(String searchTerm) throws SearchException {
        Session sess = getSession();
        FullTextSession txtSession = Search.getFullTextSession(sess);

        org.apache.lucene.search.Query qry;
        try {
            qry = HibernateSearchTools.generateQuery(searchTerm, this.persistentClass, sess, defaultAnalyzer);
        } catch (ParseException ex) {
            throw new SearchException(ex);
        }
        org.hibernate.search.FullTextQuery hibQuery = txtSession.createFullTextQuery(qry,
                this.persistentClass);
        return hibQuery.list();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public T get(PK id) {
        Session sess = getSession();
        IdentifierLoadAccess byId = sess.byId(persistentClass);
        T entity = (T) byId.load(id);

        if (entity == null) {
            log.warn("Uh oh, '" + this.persistentClass + "' object with id '" + id + "' not found...");
            throw new ObjectRetrievalFailureException(this.persistentClass, id);
        }

        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public boolean exists(PK id) {
        Session sess = getSession();
        IdentifierLoadAccess byId = sess.byId(persistentClass);
        T entity = (T) byId.load(id);
        return entity != null;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public T save(T object) {
        Session sess = getSession();
        return (T) sess.merge(object);
    }

    /**
     * {@inheritDoc}
     */
    public void remove(T object) {
        Session sess = getSession();
        sess.delete(object);
    }

    /**
     * {@inheritDoc}
     */
    public void remove(PK id) {
        Session sess = getSession();
        IdentifierLoadAccess byId = sess.byId(persistentClass);
        T entity = (T) byId.load(id);
        sess.delete(entity);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams) {
        Session sess = getSession();
        Query namedQuery = sess.getNamedQuery(queryName);

        for (String s : queryParams.keySet()) {
            namedQuery.setParameter(s, queryParams.get(s));
        }

        return namedQuery.list();
    }

    /**
     * {@inheritDoc}
     */
    public void reindex() {
        HibernateSearchTools.reindex(persistentClass, getSessionFactory().getCurrentSession());
    }


    /**
     * {@inheritDoc}
     */
    public void reindexAll(boolean async) {
        HibernateSearchTools.reindexAll(async, getSessionFactory().getCurrentSession());
    }

    /**
     *
     * @param s hql
     * @param start
     * @param end
     * @return
     */
    public List pagingList(String s, int start, int end) {
        Session session = getSession();
        Query query = session.createQuery(s);
        query.setFirstResult(start);
        query.setMaxResults(end);
        return query.list();
    }

    /**
     * 按日期查询格式化wang 2016/10/17
     * @param s
     * @param start
     * @param end
     * @param req_bf_time
     * @param req_af_time
     * @return
     */
    public List pagingList(String s, int start, int end, Date req_bf_time,Date req_af_time) {
        Session session = getSession();
        Query query = session.createQuery(s);
        if(req_bf_time != null){
           query.setDate("req_bf_time",req_bf_time);
        }
        if(req_af_time != null){
            query.setDate("req_af_time",req_af_time);
        }
        query.setFirstResult(start);
        query.setMaxResults(end);
        System.out.println(query.toString());
        return query.list();
    }

    /**
     *
     * @param s hql
     * @return
     */
    public Integer countTotal(String s) {
        Query query = getSession().createSQLQuery(s);
        Object total = query.uniqueResult();
        if(total == null) return 0;
        return ((BigDecimal)total).intValue();
    }

    public Integer countTotal(String s,Date req_bf_time,Date req_af_time) {
        Query query = getSession().createSQLQuery(s);
        if(req_bf_time != null){
            query.setDate("req_bf_time",req_bf_time);
        }
        if(req_af_time != null){
            query.setDate("req_af_time",req_af_time);
        }
        Object total = query.uniqueResult();
        if(total == null) return 0;
        return ((BigDecimal)total).intValue();
    }

    @Override
    public List<Object[]> sqlPagingQuery(String s, int start, int end) {
        SQLQuery query = getSession().createSQLQuery(s);
        query.setFirstResult(start);
        query.setMaxResults(end);
        return query.list();
    }


    public Object setBeanProperty(Map map, Class clazz) {
        Object ins = null;
        try {
            ins = clazz.newInstance();
            try {
                if (map.size() > 0) {
                    Set<String> paramsNames = map.keySet();
                    for (String name : paramsNames) {
                        PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(ins, name);
                        if(pd == null){
                            continue;
                        }
                        String propertyTypeName = pd.getPropertyType().getName();
                        String value = (String) map.get(name);
                        if(value != null && !value.trim().equals("")){
                            if (propertyTypeName.equals(int.class.getName())
                                    || propertyTypeName.equals(Integer.class.getName())) {
                                pd.getWriteMethod().invoke(ins, Integer.valueOf(value));
                            } else if (propertyTypeName.equals(long.class.getName())
                                    || propertyTypeName.equals(Long.class.getName())) {
                                pd.getWriteMethod().invoke(ins, Long.valueOf(value));
                            } else if (propertyTypeName.equals(double.class.getName())
                                    || propertyTypeName.equals(Double.class.getName())) {
                                pd.getWriteMethod().invoke(ins, Double.valueOf(value));
                            } else if (propertyTypeName.equals(float.class.getName())
                                    || propertyTypeName.equals(Float.class.getName())) {
                                pd.getWriteMethod().invoke(ins, Float.valueOf(value));
                            } else if (propertyTypeName.equals(byte.class.getName())
                                    || propertyTypeName.equals(Byte.class.getName())) {
                                pd.getWriteMethod().invoke(ins, Byte.valueOf(value));
                            } else if (propertyTypeName.equals(short.class.getName())
                                    || propertyTypeName.equals(Short.class.getName())) {
                                pd.getWriteMethod().invoke(ins, Short.valueOf(value));
                            } else if (propertyTypeName.equals(char[].class.getName())
                                    || propertyTypeName.equals(Character[].class.getName())) {
                                pd.getWriteMethod().invoke(ins, (Object) value.toCharArray());
                            } else if (propertyTypeName.equals(boolean.class.getName())
                                    || propertyTypeName.equals(Boolean.class.getName())) {
                                pd.getWriteMethod().invoke(ins, Boolean.valueOf(value));
                            } else if (propertyTypeName.equals(String.class.getName())) {
                                pd.getWriteMethod().invoke(ins, value);
                            } else if (propertyTypeName.equals(java.util.Date.class.getName())) {
                                if(!VerificaDate.verificationOfDateIsCorrect(value)) continue;
                                Set<String> patternSet = Constants.patternSet;
                                for(String p : patternSet) {
                                    if(value.length()==p.length()) {
                                        SimpleDateFormat sdf = new SimpleDateFormat(p);
                                        try {
                                            pd.getWriteMethod().invoke(ins, sdf.parse(value));
                                        } catch (java.text.ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            } catch (InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return ins;
    }
}
