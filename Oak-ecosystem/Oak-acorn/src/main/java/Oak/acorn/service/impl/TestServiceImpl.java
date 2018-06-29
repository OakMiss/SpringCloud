package Oak.acorn.service.impl;

import Oak.acorn.dao.TestDao;
import Oak.acorn.service.TestService;
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
