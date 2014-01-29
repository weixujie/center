package net.tiangu.center.controller.rest;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.Restful;
import com.jfinal.log.Logger;
import net.tiangu.center.commons.CenterConstants;
import net.tiangu.center.commons.Result;
import net.tiangu.center.interceptor.DSInterceptor;
import net.tiangu.center.station.info.Flow;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Before({Restful.class, DSInterceptor.class})
public class FlowRest
        extends Controller {
    private static Logger logger = Logger.getLogger(FlowRest.class);
    @Resource(mappedName = CenterConstants.dsName_5301KMA)
    private DataSource DS_5301KMA;
    @Resource(mappedName = CenterConstants.dsName_5301KMB)
    private DataSource DS_5301KMB;
    @Resource(mappedName = CenterConstants.dsName_5301KMC)
    private DataSource DS_5301KMC;
    @Resource(mappedName = CenterConstants.dsName_5301KMD)
    private DataSource DS_5301KMD;
    @Resource(mappedName = CenterConstants.dsName_5301KME)
    private DataSource DS_5301KME;
    @Resource(mappedName = CenterConstants.dsName_TEST)
    private DataSource DS_TEST;
    private static Map<String, QueryRunner> runnerMap = new HashMap<String, QueryRunner>();

    private void loadDataSource() {
        runnerMap.put("5301KMA", new QueryRunner(this.DS_5301KMA));
        runnerMap.put("5301KMB", new QueryRunner(this.DS_5301KMB));
        runnerMap.put("5301KMC", new QueryRunner(this.DS_5301KMC));
        runnerMap.put("5301KMD", new QueryRunner(this.DS_5301KMD));
        runnerMap.put("5301KME", new QueryRunner(this.DS_5301KME));
        runnerMap.put("TEST", new QueryRunner(this.DS_TEST));
    }

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public void index() {
        try {
            String date = getPara("date");
            String station = StringUtils.upperCase(getPara("station"));
            String type = StringUtils.upperCase(getPara("type"));
            if (StringUtils.isBlank(station)) {
                throw new Exception("客运站不能为空");
            }
            if (StringUtils.isBlank(type)) {
                type = "ALL";
            }
            if (StringUtils.isBlank(date)) {
                Calendar calendar = Calendar.getInstance();
                if ("ALL".equals(type)) {
                    calendar.add(Calendar.DATE, -1);
                    date = sdf.format(calendar.getTime());
                }
                if ("HALF".equals(type)) {
                    date = sdf.format(calendar.getTime());
                }
            }
            loadDataSource();
            renderJson(Result.exec("tickets", Flow.get(runnerMap, type, station, date)));
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.debug(e.getMessage(), e);
            } else {
                logger.error(e.getMessage());
            }
            renderJson(Result.exec(e));
        }
    }
}