package com.smart.service.impl.lis;

import com.pims.webapp.controller.GridQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.lis.HospitalDao;
import com.smart.model.lis.Hospital;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.HospitalManager;

import java.util.List;
import java.util.Map;

@Service("hospitalManager")
public class HospitalManagerImpl extends GenericManagerImpl<Hospital, Long> implements HospitalManager {

	private HospitalDao hospitalDao;

	@Autowired
	public void setHospitalDao(HospitalDao hospitalDao) {
		this.dao = hospitalDao;
		this.hospitalDao = hospitalDao;
	}


	@Override
	public List<Hospital> getHospitalList(GridQuery gridQuery) {
		StringBuilder hql = new StringBuilder("from Hospital psp where 1=1");
		String sidx = gridQuery.getSidx();
		sidx = (sidx == null || sidx.trim().equals(""))?"psp.id ":sidx;
		hql.append(" order by  ").append(sidx).append(gridQuery.getSord());
		return hospitalDao.pagingList(hql.toString(), gridQuery.getStart(), gridQuery.getEnd());
	}

	@Override
	public Integer getHospital(String query) {
		StringBuilder hql = new StringBuilder("select count(1) cnt from lab_hospital psp");

		return hospitalDao.countTotal(hql.toString());
	}

	@Override
	public Map<Long, Hospital> getHosMap(String sampleids) {
		return hospitalDao.getHosMap(sampleids);
	}
}
