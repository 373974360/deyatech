package com.deyatech.workflow.config;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.List;
import java.util.Map;

public class WorkFlowConfig {

    private static WorkFlowConfig instance = null;
    
    private Document doc = null;
    
    private Map<String, FlowForm> configs = Maps.newHashMap();
    
    /**
     * 构造函数
     */
    private WorkFlowConfig() {
        if (doc == null) {
                SAXReader reader = new SAXReader();
                try {
                    doc = reader.read(getClass().getResourceAsStream("/workflow.xml"));
                    parseForms();
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
    public static synchronized WorkFlowConfig getIntance() {
        if (instance == null) {
            instance = new WorkFlowConfig();
        }
        return instance;
    }
    
    
    /**
     * 
     */
    @SuppressWarnings("unchecked")
    private void parseForms() {
        List<Element> forms = doc.selectNodes("/flow/forms/form");
        if (forms != null && forms.size() > 0){
            for(Element element : forms){
                FlowForm form = new FlowForm();
                form.setId(element.attributeValue("id"));
                form.setName(element.attributeValue("name"));
                //
                List<Element> rangeList = element.selectNodes("userRange/range");
                if(null != rangeList && rangeList.size() > 0){
                    UserRange userRange = new UserRange();
                    for(Element rangeElement : rangeList){
                        Range r = new Range();
                        r.setStep(rangeElement.attributeValue("step"));
                        r.setRefRule(rangeElement.attributeValue("refRule"));
                        r.setField(rangeElement.attributeValue("field"));
                        userRange.setRange(r);
                    }
                    form.setUserRange(userRange);
                }
                //
                configs.put(form.getId(), form);
            }
        }
    }
    
    
    public static List<FlowForm> getAllEntityForm(){
        return Lists.newArrayList(WorkFlowConfig.getIntance().configs.values());
    }
    
    public static Map<String, FlowForm> getEntityForm(){
        return Maps.newHashMap(WorkFlowConfig.getIntance().configs);
    }
    
    public static FlowForm getEntityForm(String name){
        return WorkFlowConfig.getIntance().configs.get(name);
    }
    
    public static void reLoadConfig(){
        WorkFlowConfig.getIntance().configs.clear();
        instance = new WorkFlowConfig();
    }
}
