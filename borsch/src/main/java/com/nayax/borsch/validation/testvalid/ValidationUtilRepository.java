package com.nayax.borsch.validation.testvalid;


import com.nayax.borsch.repository.impl.TablesType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ValidationUtilRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Boolean checkId(Long id, TablesType nameTable) {
        String table = getNameTable(nameTable);
        String sql =
                " declare @table nvarchar(50) = ?;\n" +
                "  declare @id bigint = ?; \n" +
                "  declare @SqlStr nvarchar(max) SET @SqlStr = N' SELECT id From ' + @table + ' WHERE id = ' + convert(nvarchar,@id)  EXEC sp_executesql @SqlStr ";

        List<Long> listId = jdbcTemplate.query(sql, new SingleColumnRowMapper<>(Long.class), table, id);

        return listId.size() == 1;
    }

    public Boolean checkEmail(String email) {

        String sql = "SELECT Email FROM User WHERE Email LIKE ? ";

        List<String> listEmail = jdbcTemplate.query(sql, new SingleColumnRowMapper<>(String.class), email);
        return listEmail.size() == 0;
    }

    private String getNameTable(TablesType tablesType) {
        String result = null;
        for (TablesType tab : TablesType.values()) {
            if (tab == tablesType) {
                result = tab.nameTable;
            }
        }
        return result;
    }

    public Boolean checkDateTimeAfterCurrentOrderStart(LocalDateTime dateTime){
        String sql = "SELECT [id] FROM [OrderSummary] WHERE [StartTime] < ? AND [StopTime] IS NULL ; ";
        //There is at least one open order at given time
        return 0 < jdbcTemplate.query(sql, new SingleColumnRowMapper<>(Long.class), dateTime).size();
    }
}
