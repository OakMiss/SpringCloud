package com.oak.acron.service.impl;

import com.oak.acron.dao.TestDao;
import com.oak.acron.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Oak on 2018/6/28.
 * Description:
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestDao dao;
    @Override
    public String test() {
        Map map = dao.test();
        return map.toString();
    }
}
