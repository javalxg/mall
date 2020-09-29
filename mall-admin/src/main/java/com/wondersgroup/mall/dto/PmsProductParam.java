package com.wondersgroup.mall.dto;

import com.wondersgroup.mall.model.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author lxg
 * @Description: 创建和修改商品时使用的参数
 * @date 2020/9/2421:58
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PmsProductParam extends PmsProduct{
    @ApiModelProperty("商品阶梯价格设置")
    public List<PmsProductLadder> productLadderList;
    @ApiModelProperty("商品价格满减设置")
    public List<PmsProductFullReduction> productFullReductionList;
    @ApiModelProperty("商品会员价格设置")
    private List<PmsMemberPrice> memberPriceList;
    @ApiModelProperty("商品的sku库存信息")
    private List<PmsSkuStock> skuStockList;
    @ApiModelProperty("商品参数及自定义规格属性")
    private List<PmsProductAttributeValue> productAttributeValueList;
    @ApiModelProperty("专题和商品关系")
    private List<CmsSubjectProductRelation> subjectProductRelationList;
    @ApiModelProperty("优选专区和商品的关系")
    private List<CmsPrefrenceAreaProductRelation> prefrenceAreaProductRelationList;
}
