package com.deyatech.admin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deyatech.admin.entity.Dictionary;
import com.deyatech.admin.entity.DictionaryIndex;
import com.deyatech.admin.service.DictionaryIndexService;
import com.deyatech.admin.vo.DictionaryVo;
import com.deyatech.admin.mapper.DictionaryMapper;
import com.deyatech.admin.service.DictionaryService;
import com.deyatech.common.Constants;
import com.deyatech.common.base.BaseServiceImpl;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.deyatech.common.entity.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

/**
 * <p>
 * 系统数据字典明细信息 服务实现类
 * </p>
 *
 * @Author lee.
 * @since 2019-03-07
 */
@Service
public class DictionaryServiceImpl extends BaseServiceImpl<DictionaryMapper, Dictionary> implements DictionaryService {


    @Autowired
    DictionaryIndexService dictionaryIndexService;

    /**
     * 逻辑删除字典索引的时候，根据字典索引同步逻辑删除字典子项目
     *
     * @param ids
     * @return
     */
    @Override
    public boolean removeByIndexIds(List<String> ids) {
        List<DictionaryIndex> list = (List<DictionaryIndex>) dictionaryIndexService.listByIds(ids);
        if(!list.isEmpty()){
            List<String> indexIds = new ArrayList<>();
            for(DictionaryIndex dictionaryIndex:list){
                indexIds.add(dictionaryIndex.getKey());
            }
            boolean b = remove(new QueryWrapper<Dictionary>()
                    .in("index_id",indexIds));
            if(!b){
                return false;
            }
        }
        return true;
    }

    /**
     * 根据字典英文代码和索引验证是否已存在
     *
     * @param dictionary
     * @return
     */
    @Override
    public boolean validataByCodeAndIndexId(Dictionary dictionary) {
        if(StrUtil.isEmpty(dictionary.getId())){
            List<Dictionary> list = getBaseMapper().selectList(new QueryWrapper<Dictionary>()
                    .eq("code_",dictionary.getCode())
                    .eq("index_id",dictionary.getIndexId()));
            if(!list.isEmpty()){
                return false;
            }
        }else{
            List<Dictionary> list = getBaseMapper().selectList(new QueryWrapper<Dictionary>()
                    .eq("code_",dictionary.getCode())
                    .eq("index_id",dictionary.getIndexId())
                    .notIn("id_",dictionary.getId()));
            if(!list.isEmpty()){
                return false;
            }
        }
        return true;
    }

    /**
     * 单个将对象转换为vo系统数据字典明细信息
     *
     * @param dictionary
     * @return
     */
    @Override
    public DictionaryVo setVoProperties(Dictionary dictionary){
        DictionaryVo dictionaryVo = new DictionaryVo();
        BeanUtil.copyProperties(dictionary, dictionaryVo);
        return dictionaryVo;
    }

    /**
     * 批量将对象转换为vo系统数据字典明细信息
     *
     * @param dictionarys
     * @return
     */
    @Override
    public List<DictionaryVo> setVoProperties(Collection dictionarys){
        List<DictionaryVo> dictionaryVos = CollectionUtil.newArrayList();
        if (CollectionUtil.isNotEmpty(dictionarys)) {
            for (Object dictionary : dictionarys) {
                DictionaryVo dictionaryVo = new DictionaryVo();
                BeanUtil.copyProperties(dictionary, dictionaryVo);
                dictionaryVos.add(dictionaryVo);
            }
        }
        return dictionaryVos;
    }

    /**
     * 根据Dictionary对象属性检索的tree对象
     *
     * @param dictionary
     * @return
     */
    @Override
    public Collection<DictionaryVo> getDictionaryTree(Dictionary dictionary) {
        dictionary.setSortSql("sortNo asc");
        List<DictionaryVo> dictionaryVos = setVoProperties(super.listByBean(dictionary));
        List<DictionaryVo> rootDictionarys = CollectionUtil.newArrayList();
        if (CollectionUtil.isNotEmpty(dictionaryVos)) {
            for (DictionaryVo dictionaryVo : dictionaryVos) {
                dictionaryVo.setLabel(dictionaryVo.getCodeText());
                if(StrUtil.isNotBlank(dictionaryVo.getTreePosition())){
                    String[] split = dictionaryVo.getTreePosition().split(Constants.DEFAULT_TREE_POSITION_SPLIT);
                    dictionaryVo.setLevel(split.length);
                }else{
                    dictionaryVo.setLevel(Constants.DEFAULT_ROOT_LEVEL);
                }
                if (ObjectUtil.equal(dictionaryVo.getParentId(), Constants.ZERO)) {
                    rootDictionarys.add(dictionaryVo);
                }
                for (DictionaryVo childVo : dictionaryVos) {
                    if (ObjectUtil.equal(childVo.getParentId(), dictionaryVo.getId())) {
                        if (ObjectUtil.isNull(dictionaryVo.getChildren())) {
                            List<DictionaryVo> children = CollectionUtil.newArrayList();
                            children.add(childVo);
                            dictionaryVo.setChildren(children);
                        } else {
                            dictionaryVo.getChildren().add(childVo);
                        }
                    }
                }
            }
        }
        return rootDictionarys;
    }
}
