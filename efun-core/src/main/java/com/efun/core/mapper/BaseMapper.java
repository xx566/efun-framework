package com.efun.core.mapper;

import com.efun.core.domain.BaseEntity;
import com.efun.core.mapper.query.Query;
import org.apache.ibatis.annotations.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * BaseMapper
 * 基础DAO接口
 *
 * @author Galen
 * @since 2016/6/1
 */
public interface BaseMapper<E extends BaseEntity<ID>, ID extends Serializable> {

    /**
     * 根据id查询
     * @param id
     * @param <E>
     * @return
     */
    @SelectProvider(type = BaseSqlProvider.class, method = "findById")
    <E extends BaseEntity> E findById(@Param("id")Object id);

    /**
     * 插入一条记录
     * @param entity
     * @param <E>
     */
    //插入keyProperty和@Param entity对应
    @Options(useGeneratedKeys = true, keyProperty = "entity.id")
    @InsertProvider(type = BaseSqlProvider.class, method = "insert")
    <E extends BaseEntity> int insert(@Param("entity")E entity);

    /**
     * 批量插入
     * @param collection
     * @param <E>
     * @return
     */
    //批量插入keyProperty只能为id，因为Jdbc3KeyGenerator默认批量插入的参数名称只能够 collection、list、array
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @InsertProvider(type = BaseSqlProvider.class, method = "insertBatch")
    <E extends BaseEntity> int insertBatch(@Param("collection")Collection<E> collection);

    /**
     * 更新一条记录，默认ignoreNull为true
     * @param entity
     * @param ignoreNull 不保存null字段，默认为true
     * @param <E>
     * @return
     */
    @UpdateProvider(type = BaseSqlProvider.class, method = "update")
    <E extends BaseEntity> int update(@Param("entity")E entity, @Param("ignoreNull")Boolean ignoreNull);

    /**
     * 根据id删除一条记录
     * @param id
     * @param <E>
     * @return
     */
    @DeleteProvider(type = BaseSqlProvider.class, method = "delete")
    <E extends BaseEntity> int delete(@Param("id")Object id);

    /**
     * 计算记录条数
     * @return
     */
    @SelectProvider(type = BaseSqlProvider.class, method = "count")
    long count();

    /**
     * 根据条件查询记录条数(嵌套查询只支持两层)
     * @param query
     * @return
     */
    @SelectProvider(type = BaseSqlProvider.class, method = "countByQuery")
    long countByQuery(@Param("query")Query query);

    /**
     * 根据条件查询列表(嵌套查询只支持两层)
     * @param query
     * @param <E>
     * @return
     */
    @SelectProvider(type = BaseSqlProvider.class, method = "queryList")
    <E extends BaseEntity> List<E> queryList(@Param("query")Query query);

    /**
     * 根据条件查询列表(嵌套查询只支持两层)
     * @param query
     * @return
     */
    @SelectProvider(type = BaseSqlProvider.class, method = "queryMapList")
    List<Map<String, Object>> queryMapList(@Param("query")Query query);

}
