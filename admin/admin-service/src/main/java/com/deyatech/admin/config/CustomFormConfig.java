package com.deyatech.admin.config;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 自定义表单配置解析
 *
 */
public class CustomFormConfig {

    private static CustomFormConfig instance = null;
    
    private Document doc = null;
    
    private Map<String,DataType> dataType = Maps.newLinkedHashMap();
    
    private Map<String,DataShow> dataShow = Maps.newLinkedHashMap();
        
    private Map<String,Validate> validate = Maps.newLinkedHashMap();
    
    private Map<String,DataSource> dataSource = Maps.newLinkedHashMap();
    
    private Map<String,DataLength> dataLength = Maps.newLinkedHashMap();
    
    /**
     * 构造函数
     */
    private CustomFormConfig() {
        if (doc == null) {
            SAXReader reader = new SAXReader();
            try {
                doc = reader.read(getClass().getResourceAsStream(
                        "/meta/customForm.xml"));
                parseDataSource();
                parseValidate();
                parseDataLengths();
                parseDataShow();
                parseDataType();
                
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    /**
     * 获取实例
     * 
     * @return
     */
    public static synchronized CustomFormConfig getIntance() {
        if (instance == null) {
            instance = new CustomFormConfig();
        }
        return instance;
    }
    
    /**
     * 解析DataSource
     */
    @SuppressWarnings("unchecked")
    public void parseDataSource(){
        List<Element> ds = doc.selectNodes("config/dataSource/ds");
        if (ds != null && ds.size() > 0){
            for(Element element : ds){
                DataSource temp = new DataSource();
                temp.setId(element.attributeValue("id"));
                temp.setBean(element.attributeValue("bean"));
                temp.setName(element.attributeValue("name"));
                //解析参数
                List<Element> paraElement = element.selectNodes("paras/para");
                if(paraElement != null && paraElement.size() > 0){
                    for(Element p : paraElement){
                        temp.getPara().put(p.attributeValue("name"), p.getText());
                    }
                }
                dataSource.put(temp.getId(), temp);
            }
        }
    }
    /**
     * 解析数据长度
     */
    @SuppressWarnings("unchecked")
    public void parseDataLengths(){
        List<Element> dl = doc.selectNodes("/config/dataLengths/dataLength");
        if(dl != null && dl.size() >0){
            for(Element element :dl){
                DataLength temp = new DataLength();
                temp.setId(element.attributeValue("id"));
                temp.setName(element.attributeValue("name"));
                temp.setLength(element.getText());
                dataLength.put(temp.getId(), temp);
            }
        }
    }
    /**
     * 解析校验
     */
    @SuppressWarnings("unchecked")
    public void parseValidate(){
        List<Element> validateElement = doc.selectNodes("/config/validates/validate");
        if (validateElement != null && validateElement.size() > 0){
            for(Element element : validateElement){
                Validate temp = new Validate();
                temp.setId(element.attributeValue("id"));
                temp.setName(element.attributeValue("name"));
                temp.setValue(element.getText());
                validate.put(temp.getId(), temp);
            }
        }
    }
    
    /**
     * 解析显示类型
     */
    @SuppressWarnings("unchecked")
    public void parseDataShow(){
        List<Element> dtElement = doc.selectNodes("/config/dataShows/dataShow");
        if (dtElement != null && dtElement.size() > 0){
            for(Element element : dtElement){
                DataShow temp = new DataShow();
                temp.setId(element.attributeValue("id"));
                temp.setName(element.attributeValue("name"));
                //数据校验
//                Node validateElement = element.selectSingleNode("validate");
//                if(validateElement != null){
//                    String[] value = validateElement.getText().split(",");
//                    for(String v : value){
//                        if(validate.get(v) != null){
//                            temp.getValidate().add(validate.get(v));
//                        }
//                    }
//                }
                //数据源
                Node dsElement = element.selectSingleNode("ds");
                if(dsElement != null){
                    String[] value = dsElement.getText().split(",");
                    for(String v : value){
                        if(dataSource.get(v) != null){
                            temp.getDataSource().add(dataSource.get(v));
                        }
                    }
                }
                Node lengthElement = element.selectSingleNode("dataLength");
                if(lengthElement!=null){
                    String[] value = lengthElement.getText().split(",");
                    for(String v:value){
                        if(dataLength.get(v) != null){
                            temp.getDataLengths().add(dataLength.get(v));
                        }
                    }
                }
                dataShow.put(temp.getId(), temp);
            }
        }
    }
    
    /**
     * 解析数据类型
     */
    @SuppressWarnings("unchecked")
    public void parseDataType(){
        List<Element> dtElement = doc.selectNodes("/config/dataTypes/dataType");
        if (dtElement != null && dtElement.size() > 0){
            for(Element element : dtElement){
                DataType temp = new DataType();
                temp.setId(element.attributeValue("id"));
                temp.setName(element.attributeValue("name"));
                //数据展示
                List<Element> dtShow = element.selectNodes("dataShow");
                if(dtShow != null && dtShow.size() > 0){
                    for(Element dShow : dtShow ){
                        DataShow orgin = dataShow.get(dShow.attributeValue("ref"));
                        if(orgin != null){
                            DataShow newdShow = orgin.newDataShow();
                            newdShow.setMustValidate(Boolean.parseBoolean(dShow.attributeValue("mustValidate")));
                            //解析校验
                            Node validateElement = dShow.selectSingleNode("validate");
                            if(validateElement != null){
                                String[] value = validateElement.getText().split(",");
                                for(String v : value){
                                    if(validate.get(v) != null){
                                        newdShow.getValidate().add(validate.get(v));
                                    }
                                }
                            }
                            /*Node lengthElement = dShow.selectSingleNode("dataLength");
                            if(lengthElement!=null){
                                String[] value = lengthElement.getText().split(",");
                                for(String v:value){
                                    if(dataLength.get(v) != null){
                                        newdShow.getDataLengths().add(dataLength.get(v));
                                    }
                                }
                            }*/
                            temp.getDataShow().add(newdShow);
                        }
                    }
                }
                dataType.put(temp.getId(), temp);
            }
        }
    }
    /**
     * 根据id获取DataSource Config
     * @param id
     * @return
     */
    public static DataSource getDsConfigById(String id){
        CustomFormConfig config = CustomFormConfig.getIntance();
        return config.dataSource.get(id);
    }
    /**
     * 根据id获取dataLengths
     * @param id
     * @return
     */
    public static DataLength getDataLengthsId(String id){
        CustomFormConfig config = CustomFormConfig.getIntance();
        return config.dataLength.get(id);
    }
    
    /**
     * 根据id获取DataShow
     * @param id
     * @return
     */
    public static DataShow getDataShowById(String id){
        CustomFormConfig config = CustomFormConfig.getIntance();
        return config.dataShow.get(id);
    }
    
    /**
     * 根据id获取DataType
     * @param id
     * @return
     */
    public static DataType getDataTypeById(String id){
        CustomFormConfig config = CustomFormConfig.getIntance();
        return config.dataType.get(id);
    }
    
    /**
     * 根据 DataTypeId 查找DataShow
     * @param id
     * @return
     */
    public static List<DataShow> getDataShowByDataTypeId(String id){
        CustomFormConfig config = CustomFormConfig.getIntance();
        DataType dt = config.dataType.get(id);
        if(dt != null ){
            return dt.getDataShow();
        }
        return null;
    }
    /**
     * 根据id获取Validate
     * @param id
     * @return
     */
    public static Validate getValidateById(String id){
        CustomFormConfig config = CustomFormConfig.getIntance();
        return config.validate.get(id);
    }
    
//    /**
//     * 根据ds配置id获取 AbstractDataSource
//     * @param id
//     * @return
//     */
//    public static AbstractDataSource getDataSourceById(String id){
//        DataSource dsConfig =  getDsConfigById(id);
//        AbstractDataSource dataSource = null;
//        if(dsConfig != null){
//            dataSource = ApplicationContextSpring.getBean(dsConfig.getBean());
//        }
//        return dataSource;
//    }
    
    /**
     * 获取所有的DataShow
     * @return
     */
    public static List<DataShow> getDataShow(){
        CustomFormConfig config = CustomFormConfig.getIntance();
        List<DataShow> dataShow = null;
        if(config.dataShow.size() > 0){
            dataShow = Lists.newArrayList();
            Iterator<String> itor = config.dataShow.keySet().iterator();
            while(itor.hasNext()){
                dataShow.add(config.dataShow.get(itor.next()));
            }
        }
        return dataShow;
    }
    
    /**
     * 获取所有的DataType
     * @return
     */
    public static List<DataType> getAllDataType(){
        CustomFormConfig config = CustomFormConfig.getIntance();
        List<DataType> dType = null;
        if(config.dataType.size() > 0){
            dType = Lists.newArrayList();
            Iterator<String> itor = config.dataType.keySet().iterator();
            while(itor.hasNext()){
                dType.add(config.dataType.get(itor.next()));
            }
        }
        return dType;
    }
    
    /**
     * 根据dataTypeId和dataShowId获取校验方式
     * @param dataTypeId
     * @param dataShowId
     * @return
     */
    public static List<Validate> getValidate(String dataTypeId, String dataShowId){
        DataType dt = getDataTypeById(dataTypeId);
        List<Validate> validate = null;
        if(dt != null){
            List<DataShow> dataShow = dt.getDataShow();
            DataShow temp = null;
            if(dataShow != null && dataShow.size() > 0){
                for(DataShow ds : dataShow){
                    if(ds.getId().equals(dataShowId)){
                        temp = ds;
                        break;
                    }
                }
                if(temp != null){
                    validate = temp.getValidate();
                }
            }
        }
        return validate;
    }
}
