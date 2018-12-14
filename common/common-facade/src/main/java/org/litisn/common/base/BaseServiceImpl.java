package org.litisn.common.base;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 *
 * @author: litisn
 * @since: 2018-12-14 11:14
 */
public class BaseServiceImpl<M extends BaseMapper<T>,T> extends ServiceImpl<M,T> implements BaseService<T> {

}
