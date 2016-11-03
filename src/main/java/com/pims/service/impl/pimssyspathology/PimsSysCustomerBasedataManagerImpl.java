package com.pims.service.impl.pimssyspathology;

import com.pims.dao.pimssyspathology.PimsSysCustomerBasedataDao;
import com.pims.model.PimsSysCustomerBasedata;
import com.pims.service.pimssyspathology.PimsSysCustomerBasedataManager;
import com.pims.webapp.controller.GridQuery;
import com.smart.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/12.
 * Description:
 */
@Service("pimsSysCustomerBasedataManager")
public class PimsSysCustomerBasedataManagerImpl extends GenericManagerImpl<PimsSysCustomerBasedata, Long> implements PimsSysCustomerBasedataManager {

    private PimsSysCustomerBasedataDao pimsSysCustomerBasedataDao;

    @Autowired
    public void setPimsSysCustomerBasedataDao(PimsSysCustomerBasedataDao pimsSysCustomerBasedataDao) {
        this.dao = pimsSysCustomerBasedataDao;
        this.pimsSysCustomerBasedataDao = pimsSysCustomerBasedataDao;
    }

    @Override
    public List<PimsSysCustomerBasedata> getCustomerDataList(GridQuery gridQuery) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Select A.Basedataid,A.Bascustomercode,A.baspathologyid,A.bastype,A.basrefdataid,a.basuseflag,a.bascreatetime,a.basrptItemSort,a.basrefdataalias,")
                .append("(Select L.name From lab_hospital L where To_Number(A.Bascustomercode)=L.id) as Bascustomername,")
                .append("(Select P.patnamech From Pims_Sys_Pathology P where A.baspathologyid=P.Pathologyid) as baspathologyname,")
                .append("(select case ")
                .append("        when A.bastype=1 then (select m.matname from Pims_Sys_Req_Material m where m.materialid=a.basrefdataid) ")
                .append("        when A.bastype=2 then (select M.Fieelementname from Pims_Sys_Req_field m where M.Fieldid=a.basrefdataid)")
                .append("        when A.bastype=3 then (select M.Rptname from Pims_Sys_Report_Items m where M.Reportitemid=a.basrefdataid)")
                .append("        when A.bastype=4 then (select M.Teschinesename from Pims_Sys_Req_Testitem m where m.testitemid=a.basrefdataid)")
                .append("        else null end basrefdataname from dual")
                .append(") as basrefdataname").append(" From PIMS_SYS_CUSTOMER_BASEDATA a where 1=1");
        String query = gridQuery.getQuery();
        String sidx = gridQuery.getSidx();
        if (query != null && !"".equals(query.trim())) {
            buffer.append(" and a.Bascustomercode='").append(query).append("'");
        }
        sidx = (sidx == null || sidx.trim().equals("")) ? "a.Basedataid " : sidx;
        buffer.append(" order by  ").append(sidx).append(gridQuery.getSord());

        List<Object[]> data = pimsSysCustomerBasedataDao.sqlPagingQuery(buffer.toString(), gridQuery.getStart(), gridQuery.getEnd());
        List<PimsSysCustomerBasedata> result = new ArrayList<>();
        if(data.size() > 0) {
            for(Object[] obj : data) {
                PimsSysCustomerBasedata pscbd = new PimsSysCustomerBasedata();
                pscbd.setBasedataid(((BigDecimal)obj[0]).longValue());
                pscbd.setBascustomercode(String.valueOf(obj[1]));
                pscbd.setBaspathologyid(((BigDecimal)obj[2]).longValue());
                pscbd.setBastype(((BigDecimal)obj[3]).longValue());
                pscbd.setBasrefdataid(((BigDecimal)obj[4]).longValue());
                pscbd.setBasuseflag(((BigDecimal)obj[5]).longValue());
                pscbd.setBascreatetime((Date)obj[6]);
                pscbd.setBasrptItemSort(((BigDecimal)obj[7]).intValue());
                pscbd.setBasrefdataalias(obj[8]==null?"":String.valueOf(obj[8]));
                pscbd.setBascustomername(String.valueOf(obj[9]));
                pscbd.setBaspathologyname(String.valueOf(obj[10]));
                pscbd.setBasrefdataname(String.valueOf(obj[11]));
                result.add(pscbd);
            }
        }
        return result;
    }

    @Override
    public Integer countCustomerData(String query) {
        StringBuilder builder = new StringBuilder("select count(1) cnt from PIMS_SYS_CUSTOMER_BASEDATA a where 1=1");
        if (query != null && !"".equals(query.trim())) {
            builder.append(" and a.Bascustomercode='").append(query).append("'");
        }
        return pimsSysCustomerBasedataDao.countTotal(builder.toString());
    }

    @Override
    public List<PimsSysCustomerBasedata> getCustomerDataList(Long hospitalId, Long pathologyId) {
        String sql = "select {pb.*} from Pims_Sys_Report_Items pr,Pims_Sys_Customer_Basedata pb, Pims_Sys_Req_Field rf where Pr.Reportitemid=pb.Basrefdataid and Rf.Fieldid =Pr.Rptelementid and Rf.Fieuseflag=1 and pb.bastype=3 and Pb.Baspathologyid=:pathologyId and Pb.Bascustomercode=:hospitalId";
        return pimsSysCustomerBasedataDao.getCustomerDataList(sql, hospitalId, pathologyId);
    }
}
