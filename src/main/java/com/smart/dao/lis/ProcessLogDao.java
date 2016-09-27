package com.smart.dao.lis;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.lis.ProcessLog;

public interface ProcessLogDao extends GenericDao<ProcessLog, Long> {

	@Transactional
	List<ProcessLog> getBySampleId(Long id);

	@Transactional
	ProcessLog getBySampleLogId(Long logid);

}
