package com.smart.dao.hibernate.lis;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.HospitalDao;
import com.smart.model.lis.Hospital;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("hospitalDao")
public class HospitalDaoHibernate extends GenericDaoHibernate<Hospital, Long> implements HospitalDao {

	public HospitalDaoHibernate() {
		super(Hospital.class);
	}

	@Override
	public Map<Long, Hospital> getHosMap(String sampleids) {
		Map<Long, Hospital> map = new HashMap<>();
		StringBuffer sb = new StringBuffer();
		sb.append(" from Hospital where id in ("+sampleids+")");
		List<Hospital> pspList = getSession().createQuery(sb.toString()).list();
		for(Hospital psp:pspList){
			map.put(psp.getId(),psp);
		}
		return map;
	}
}
