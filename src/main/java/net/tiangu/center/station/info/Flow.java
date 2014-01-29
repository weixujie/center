package net.tiangu.center.station.info;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;

import java.math.BigDecimal;
import java.util.Map;

public class Flow {
    static StringBuffer queryAllSql = new StringBuffer();
    static StringBuffer queryHalfSql = new StringBuffer();

    static {
        queryAllSql.append(" SELECT COUNT(seat_.id) AS tickets ");
        queryAllSql.append(" FROM tg_ticket.t_seatState seat_ ");
        queryAllSql.append(" LEFT JOIN tg_dispatch.t_classNumber class_ ON class_.id = seat_.classNumber_id ");
        queryAllSql.append(" WHERE seat_.status >= 10 AND class_.status = 1 AND class_.active = 1 ");
        queryAllSql.append(" AND to_char(class_.classDate, 'yyyy-MM-dd') = ? ");


        queryHalfSql.append(" SELECT COUNT(seat_.id) AS tickets ");
        queryHalfSql.append(" FROM tg_ticket.t_seatState seat_ ");
        queryHalfSql.append(" LEFT JOIN tg_dispatch.t_classNumber class_ ON class_.id = seat_.classNumber_id ");
        queryHalfSql.append(" WHERE seat_.status >= 10 AND class_.status = 1 AND class_.active = 1 AND class_.time < '12:00' ");
        queryHalfSql.append(" AND to_char(class_.classDate, 'yyyy-MM-dd') = ? ");
    }

    public static Integer get(Map<String, QueryRunner> runnerMap, String method, String station, String date)
            throws Exception {
        QueryRunner runner = runnerMap.get(station);
        BigDecimal tickets;
        if ("ALL".equals(method)) {
            tickets = (BigDecimal) ((Map) runner.query(queryAllSql.toString(), new MapHandler(), date)).get("tickets");
        } else {
            tickets = (BigDecimal) ((Map) runner.query(queryHalfSql.toString(), new MapHandler(), date)).get("tickets");
        }
        return tickets.intValue();
    }
}
