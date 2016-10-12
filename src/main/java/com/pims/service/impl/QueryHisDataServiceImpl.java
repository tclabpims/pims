package com.pims.service.impl;

import com.pims.model.HisChargePrice;
import com.pims.service.QueryHisDataService;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 909436637@qq.com on 2016/10/12.
 * Description:
 */
public class QueryHisDataServiceImpl implements QueryHisDataService {

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer queryHisChargePrice(String query) {
        StringBuffer buffer = new StringBuffer("select count(1) cnt from V_HSBHJ_CHARGEPRICE h");
        if (query != null && !"".equals(query.trim())) {
            buffer.append(" where h.sfxmmc like '%").append(query).append("%'");
        }
        return jdbcTemplate.queryForObject(buffer.toString(), Integer.class);
    }

    //查询HIS系统中的收费项目信息
    @Override
    public List queryHisChargePrice(final String query, final int start, final int end) {

        StringBuffer sql = new StringBuffer("select * from (select H.Sfxmid,H.Sfxmmc,H.Sfxmdj, rownum as rowno from V_HSBHJ_CHARGEPRICE h");
        if (query != null && !"".equals(query.trim())) {
            sql.append(" where h.sfxmmc like '%").append(query).append("%'");
        }
        sql.append(" ) where rowno between ? and ?");


        return jdbcTemplate.query(sql.toString(), new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1, start);
                preparedStatement.setInt(2, end);
            }
        }, new ResultSetExtractor<List>() {
            @Override
            public List extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                List result = new ArrayList();
                while (resultSet.next()) {
                    HisChargePrice hcp = new HisChargePrice(resultSet.getLong("sfxmid"), String.valueOf(resultSet.getObject("sfxmmc")), resultSet.getDouble("sfxmdj"));
                    result.add(hcp);
                }
                return result;
            }
        });
    }
}
