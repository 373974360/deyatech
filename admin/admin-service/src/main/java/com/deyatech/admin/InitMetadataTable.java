package com.deyatech.admin;

import com.deyatech.admin.service.MetadataCollectionService;
import com.deyatech.admin.util.MetaUtils;
import com.deyatech.admin.vo.MetadataCollectionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author doukang
 * @description TODO
 * @date 2019/8/20 11:04
 */
//@Component
public class InitMetadataTable implements ApplicationRunner {

    @Autowired
    private MetadataCollectionService metadataCollectionService;

    @Override
    public void run(ApplicationArguments args) {
//        List<MetadataCollectionVo> metadataCollectionVoList = metadataCollectionService.findAllData(null);
//        MetaUtils.initMetadataTable(metadataCollectionVoList);
    }
}
