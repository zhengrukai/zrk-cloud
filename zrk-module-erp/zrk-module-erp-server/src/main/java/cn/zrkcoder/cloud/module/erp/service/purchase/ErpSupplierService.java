package cn.zrkcoder.cloud.module.erp.service.purchase;

import cn.zrkcoder.cloud.module.erp.controller.admin.purchase.vo.supplier.ErpSupplierPageReqVO;
import cn.zrkcoder.cloud.module.erp.controller.admin.purchase.vo.supplier.ErpSupplierSaveReqVO;
import cn.zrkcoder.cloud.module.erp.dal.dataobject.purchase.ErpSupplierDO;
import cn.zrkcoder.cloud.framework.common.pojo.PageResult;
import jakarta.validation.Valid;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static cn.zrkcoder.cloud.framework.common.util.collection.CollectionUtils.convertMap;

/**
 * ERP 供应商 Service 接口
 *
 * @author zrk on 2026/2/24
 */
public interface ErpSupplierService {

    /**
     * 创建供应商
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSupplier(@Valid ErpSupplierSaveReqVO createReqVO);

    /**
     * 更新供应商
     *
     * @param updateReqVO 更新信息
     */
    void updateSupplier(@Valid ErpSupplierSaveReqVO updateReqVO);

    /**
     * 删除供应商
     *
     * @param id 编号
     */
    void deleteSupplier(Long id);

    /**
     * 获得供应商
     *
     * @param id 编号
     * @return 供应商
     */
    ErpSupplierDO getSupplier(Long id);

    /**
     * 校验供应商
     *
     * @param id 编号
     * @return 供应商
     */
    ErpSupplierDO validateSupplier(Long id);

    /**
     * 获得供应商列表
     *
     * @param ids 编号列表
     * @return 供应商列表
     */
    List<ErpSupplierDO> getSupplierList(Collection<Long> ids);

    /**
     * 获得供应商 Map
     *
     * @param ids 编号列表
     * @return 供应商 Map
     */
    default Map<Long, ErpSupplierDO> getSupplierMap(Collection<Long> ids) {
        return convertMap(getSupplierList(ids), ErpSupplierDO::getId);
    }

    /**
     * 获得供应商分页
     *
     * @param pageReqVO 分页查询
     * @return 供应商分页
     */
    PageResult<ErpSupplierDO> getSupplierPage(ErpSupplierPageReqVO pageReqVO);

    /**
     * 获得指定状态的供应商列表
     *
     * @param status 状态
     * @return 供应商列表
     */
    List<ErpSupplierDO> getSupplierListByStatus(Integer status);


}
