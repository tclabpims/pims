package com.pims.service.impl.othermanagement;

import com.pims.dao.othermanage.PimsSlideLoanDao;
import com.pims.model.PimsBaseModel;
import com.pims.model.PimsPathologySlide;
import com.pims.service.othermanagement.PimsSlideLoanManager;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zp on 2016/11/16.
 */
@Service("pimsSlideLoanManager")
public class PimsSlideLoanManagerImpl extends GenericManagerImpl<PimsPathologySlide, Long> implements PimsSlideLoanManager{
    private PimsSlideLoanDao pimsSlideLoanDao;

    @Autowired
    public void setPimsSlideLoanDao(PimsSlideLoanDao pimsSlideLoanDao){
        this.pimsSlideLoanDao = pimsSlideLoanDao;
        this.dao = pimsSlideLoanDao;
    }

    private List ChangeList(String[] st,List list){
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        if(list == null || list.size() == 0){
            return mapList;
        }else{

            for(Object bean:list){
                Map<String, Object> map1 = new HashMap<String, Object>();
                Object[] pd = (Object[]) bean;
                for(int i=0;i<st.length;i++){
                    Object o = pd[i];
                    map1.put(st[i],o);
                }
                mapList.add(map1);
            }
            return mapList;
        }
    }

    @Override
    public List getLoanList(PimsBaseModel map){
        List list = pimsSlideLoanDao.getLoanList(map);
        String[] st = {"slistockin","slipathologycode","slislidebarcode","slicreatetime","samsamplename","sampathologyid","sampatientname","sampatientsex","sampatientage","sampatientagetype"};
        return ChangeList(st,list);
    }

    @Override
    public List getLoanList2(PimsBaseModel map){
        List list = pimsSlideLoanDao.getLoanList2(map);
        String[] st = {"slislidebarcode","sliouttime","slicustomername","slicustomerid"};
        return ChangeList(st,list);
    }

    @Override
    public int getReqListNum(PimsBaseModel map) {
        return pimsSlideLoanDao.getReqListNum(map);
    }

    @Override
    public PimsPathologySlide getByLoanNo(Long id) {
        return pimsSlideLoanDao.getByLoanNo(id);
    }

    @Override
    public boolean canChange(Long id, String sts) {
        return pimsSlideLoanDao.canChange(id, sts);
    }

    @Override
    public boolean loan(Map map) { return pimsSlideLoanDao.loan(map);
    }


//    @Override
//    public boolean returnSlide4(PimsBaseModel map){return pimsSlideLoanDao.returnSlide4(map);}
//
//    @Override
//    public boolean returnSlide(PimsBaseModel map){return pimsSlideLoanDao.returnSlide(map);}
//
//    @Override
//    public boolean returnSlide2(PimsBaseModel map){return pimsSlideLoanDao.returnSlide2(map);}
//
    @Override
    public boolean returnSlide3(PimsBaseModel map){return pimsSlideLoanDao.returnSlide3(map);}
}
