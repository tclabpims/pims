package com.pims.service.impl;

import com.pims.model.*;
import com.pims.service.QueryHisDataService;
import com.pims.service.UpdateReportDataService;
import com.pims.service.pimspathologysample.PimsPathologySampleManager;
import com.smart.Constants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UpdateReportDataServiceImpl implements UpdateReportDataService {

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insert(PimsPathologySample sample) {
        StringBuffer sb = new StringBuffer();
        sb.append(" insert into t_pims_pathology_report ()");
    }
}
