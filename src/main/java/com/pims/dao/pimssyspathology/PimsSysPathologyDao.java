package com.pims.dao.pimssyspathology;

import com.pims.model.PimsSysPathology;
import com.smart.dao.GenericDao;

import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/9/28.
 * Description:病种类别 DAO
 */
public interface PimsSysPathologyDao extends GenericDao<PimsSysPathology, Long> {

    List<PimsSysPathology> getPimsSysPathology(String hql, int offset, int pageSize, String sortColumn, String direct);

    List<PimsSysPathology> getPimsSysPathologyList(PimsSysPathology pimsSysPathology, int offset, int pageSize, String sortColumn, String direct);

    List<PimsSysPathology> getPimsSysPathologyList(String s, int start, int end);

    Integer getPimsSysPathologyTotal(String s);
}
