import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author: lee.
 * @since: 2018-12-13 17:52
 */
public class CodeGenerator {
    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setAuthor("lee.");
        gc.setServiceName("Service");
        gc.setOpen(false);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        gc.setSwagger2(true);
        gc.setFileOverride(true);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUrl("jdbc:mysql://mysql:3306/deyatech-cms?useUnicode=true&useSSL=false&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setUsername("root");
        dsc.setPassword("dyt@88352636");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(scanner("模块名"));
        pc.setParent("com.deyatech");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String mapperTemplatePath = "/templates/mapper.xml.ftl";
        String entityTemplatePath = "/templates/entity.java.ftl";
        String entityVoTemplatePath = "/templates/entityVo.java.ftl";
        String mapperJavaTemplatePath = "/templates/mapper.java.ftl";
        String serviceTemplatePath = "/templates/service.java.ftl";
        String serviceImplTemplatePath = "/templates/serviceImpl.java.ftl";
        String controllerTemplatePath = "/templates/controller.java.ftl";
        String vueApiTemplatePath = "/templates/vue-api.ftl";
        String vueViewTemplatePath = "/templates/vue.ftl";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(entityTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return projectPath + "/" + pc.getModuleName() + "/" + pc.getModuleName() + "-facade/"
                        + "/src/main/java/" + pc.getParent().replaceAll("\\.", "/") + "/" + pc.getEntity()
                        + "/" + tableInfo.getEntityName() + StringPool.DOT_JAVA;
            }
        });
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(entityVoTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return projectPath + "/" + pc.getModuleName() + "/" + pc.getModuleName() + "-facade/"
                        + "/src/main/java/" + pc.getParent().replaceAll("\\.", "/") + "/vo/"
                        + tableInfo.getEntityName().concat("Vo") + StringPool.DOT_JAVA;
            }
        });
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(mapperJavaTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return projectPath + "/" + pc.getModuleName() + "/" + pc.getModuleName() + "-service/"
                        + "/src/main/java/" + pc.getParent().replaceAll("\\.", "/") + "/" + pc.getMapper()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_JAVA;
            }
        });
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(mapperTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return projectPath + "/" + pc.getModuleName() + "/" + pc.getModuleName() + "-service/"
                        + "/src/main/resources/mapper/"
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(serviceTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return projectPath + "/" + pc.getModuleName() + "/" + pc.getModuleName() + "-service/"
                        + "/src/main/java/" + pc.getParent().replaceAll("\\.", "/") + "/" + pc.getService()
                        + "/" + tableInfo.getEntityName() + "Service" + StringPool.DOT_JAVA;
            }
        });
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(serviceImplTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return projectPath + "/" + pc.getModuleName() + "/" + pc.getModuleName() + "-service/"
                        + "/src/main/java/" + pc.getParent().replaceAll("\\.", "/") + "/" + pc.getServiceImpl().replaceAll("\\.", "/")
                        + "/" + tableInfo.getEntityName() + "ServiceImpl" + StringPool.DOT_JAVA;
            }
        });
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(controllerTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return projectPath + "/" + pc.getModuleName() + "/" + pc.getModuleName() + "-service/"
                        + "/src/main/java/" + pc.getParent().replaceAll("\\.", "/") + "/" + pc.getController()
                        + "/" + tableInfo.getEntityName() + "Controller" + StringPool.DOT_JAVA;
            }
        });
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(vueApiTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return "/Users/kangdou/Downloads" + "/" + pc.getModuleName() +
                        "/" + StringUtils.firstCharToLower(tableInfo.getEntityName()) + ".js";
            }
        });
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(vueViewTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return "/Users/kangdou/Downloads" + "/" + pc.getModuleName() +
                        "/" + StringUtils.firstCharToLower(tableInfo.getEntityName()) + ".vue";
            }
        });
        cfg.setFileOutConfigList(focList);
        //自己写算法实现java文件以及xml文件merge功能
        //cfg.setFileCreate()
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        // templateConfig.setEntity();
        // templateConfig.setService();
        // templateConfig.setController();

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityColumns("id_", "enable_", "version_", "remark_", "create_by", "create_time", "update_by", "update_time");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setSuperEntityClass("BaseEntity");
        strategy.setSuperMapperClass("BaseMapper");
        strategy.setSuperServiceClass("BaseService");
        strategy.setSuperServiceImplClass("BaseServiceImpl");
        strategy.setSuperControllerClass("BaseController");
        strategy.setInclude(scanner("表名,多个用英文逗号分开").split(","));
        strategy.setTablePrefix(pc.getModuleName() + "_");
        strategy.setEntityTableFieldAnnotationEnable(true);
        strategy.setControllerMappingHyphenStyle(true);
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
