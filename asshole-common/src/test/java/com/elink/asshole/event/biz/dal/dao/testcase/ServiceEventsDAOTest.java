package com.elink.asshole.event.biz.dal.dao.testcase;


import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ServiceEventsDAOTest  {

    @Resource
    private ServiceEventsDAO serviceEventsDAO;

    private static Long id = null;

    @Before
    public void insertServiceEventsDOTest() {
        ServiceEventsDO serviceEventsDO = new ServiceEventsDO();
        //FIXME AutoDAO�Զ���ɵĴ��룬���ڴ˴�����������
        id = serviceEventsDAO.insertServiceEventsDO( serviceEventsDO );
    }

    @Test
    public void countServiceEventsDOByExampleTest() {
        ServiceEventsDO serviceEventsDO = new ServiceEventsDO();
        //FIXME AutoDAO�Զ���ɵĴ��룬���ڴ˴�����������
        Assert.assertTrue( serviceEventsDAO.countServiceEventsDOByExample( serviceEventsDO ) > 0 );
    }

    @Test
    public void countServiceEventsQueryByExampleTest() {
        ServiceEventsQuery serviceEventsQuery = new ServiceEventsQuery();
        //FIXME AutoDAO�Զ���ɵĴ��룬���ڴ˴�����������
        Assert.assertTrue( serviceEventsDAO.countServiceEventsQueryByExample( serviceEventsQuery ) > 0 );
    }

    @Test
    public void updateServiceEventsDOTest() {
        ServiceEventsDO serviceEventsDO = new ServiceEventsDO();
        //FIXME AutoDAO�Զ���ɵĴ��룬���ڴ˴�����������
        Assert.assertTrue( serviceEventsDAO.updateServiceEventsDO( serviceEventsDO ) > 0 );
    }

    @Test
    public void findListServiceEventsDOByExampleTest() {
        ServiceEventsDO serviceEventsDO = new ServiceEventsDO();
        //FIXME AutoDAO�Զ���ɵĴ��룬���ڴ˴�����������
        List<ServiceEventsDO> resultList = serviceEventsDAO.findListByExample( serviceEventsDO );
        Assert.assertNotNull( resultList );
        Assert.assertTrue( resultList.size() > 0 );
    }

    @Test
    public void findListServiceEventsQueryByExampleTest() {
        ServiceEventsQuery serviceEventsQuery = new ServiceEventsQuery();
        //FIXME AutoDAO�Զ���ɵĴ��룬���ڴ˴�����������
        List<ServiceEventsQuery> resultList = serviceEventsDAO.findListByExample( serviceEventsQuery );
        Assert.assertNotNull( resultList );
        Assert.assertTrue( resultList.size() > 0 );
    }

    @Test
    public void findServiceEventsDOByPrimaryKeyTest() {
        Assert.assertNotNull( serviceEventsDAO.findServiceEventsDOByPrimaryKey( id ) );
    }

    @Test
    public void deleteServiceEventsDOByPrimaryKeyTest() {
        Assert.assertTrue( serviceEventsDAO.deleteServiceEventsDOByPrimaryKey( id ) > 0 );
    }

}