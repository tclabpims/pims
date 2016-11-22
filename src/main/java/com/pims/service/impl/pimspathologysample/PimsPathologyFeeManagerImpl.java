package com.pims.service.impl.pimspathologysample;

import com.pims.dao.pimspathologysample.PimsPathologyFeeDao;
import com.pims.model.PimsPathologyFee;
import com.pims.model.PimsPathologyOrder;
import com.pims.service.pimspathologysample.PimsPathologyFeeManager;
import com.pims.webapp.controller.WebControllerUtil;
import com.smart.model.user.User;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by king on 2016/10/10.
 */
@Service("pimsPathologyFeeManager")
public class PimsPathologyFeeManagerImpl extends GenericManagerImpl<PimsPathologyFee, Long> implements PimsPathologyFeeManager {
    private PimsPathologyFeeDao pimsPathologyFeeDao;

    @Autowired
    public void setPimsPathologyFeeDao(PimsPathologyFeeDao pimsPathologyFeeDao) {
        this.pimsPathologyFeeDao = pimsPathologyFeeDao;
        this.dao = pimsPathologyFeeDao;
    }
    /**
     * 根据费用来源查询病理的收费列表
     * @param map
     * @return
     */
    @Override
    public List<PimsPathologyFee> getSampleList(PimsPathologyFee map) {
        return pimsPathologyFeeDao.getSampleList(map);
    }

    @Override
    @Transactional
    public void saveFee(PimsPathologyOrder pathologyOrder, long pathologyId, Set<Long> testItem) {
        if(testItem.size() > 0) {
            List result = pimsPathologyFeeDao.getChargeItems(pathologyOrder.getOrdcustomercode(), testItem);
            if(result.size() > 0) {
                User user = WebControllerUtil.getAuthUser();
                for(Object obj : result) {
                    PimsPathologyFee fee = new PimsPathologyFee();
                    Date dt = new Date();
                    Object[] item = (Object[])obj;
                    fee.setFeepathologyid(pathologyId);
                    fee.setFeecustomerid(pathologyOrder.getOrdcustomercode());
                    fee.setFeetime(dt);
                    fee.setFeeuserid(String.valueOf(user.getId()));
                    fee.setFeeusername(user.getName());
                    fee.setFeeinputuser(user.getName());
                    fee.setFeeinputtime(dt);
                    fee.setFeepathologycode(pathologyOrder.getOrdpathologycode());
                    fee.setFeesource(1L);
                    fee.setFeestate(1L);
                    fee.setFeeamount(1L);
                    fee.setFeesampleid(pathologyOrder.getOrdsampleid());
                    fee.setFeeid(((BigDecimal)item[0]).longValue());
                    fee.setFeenamech((String)item[1]);
                    fee.setFeenameen((String)item[2]);
                    fee.setFeeprince(((BigDecimal)item[3]).doubleValue());
                    fee.setFeehisitemid((String)item[5]);
                    fee.setFeehisname((String)item[6]);
                    fee.setFeehisprice(((BigDecimal)item[7]).doubleValue());
                    fee.setFeecost(((BigDecimal)item[7]).doubleValue());
                    fee.setFeecategory(" ");
                    save(fee);
                }
            }
        }
    }
}
