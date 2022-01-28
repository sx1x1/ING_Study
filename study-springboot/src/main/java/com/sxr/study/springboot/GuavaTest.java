package com.sxr.study.springboot;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Table;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.sxr.study.springboot.Role.*;
import static com.sxr.study.springboot.Button.*;
import static com.sxr.study.springboot.Status.*;

/**
 * @author sxr
 * @date 2021/10/29 11:35 下午
 */
public class GuavaTest {
    public static void main(String[] args) {
        final long l1 = System.currentTimeMillis();
        Table<Role, Status, List<Button>> table = HashBasedTable.create();
        table.put(SP,DAI_JIE_DAN, Arrays.asList(REMARK,JIE_DAN));
        table.put(SP,DAI_PAI_GONG, Arrays.asList(REMARK,ZHI_PAI_WANG_DIAN));
        table.put(SP,DAI_WANG_DIAN_PAI_GONG, Arrays.asList(REMARK,GAI_PAI_WANG_DIAN,ZHI_PAI_GONG_CHENG_SHI));
        table.put(SP,DAI_YU_YUE, Arrays.asList(REMARK,GAI_PAI_GONG_CHENG_SHI,QUE_REN_YU_YUE));
        table.put(SP,YI_YU_YUE, Arrays.asList(REMARK,GAI_PAI_GONG_CHENG_SHI,QUE_REN_CHU_FA));
        table.put(SP,YI_CHU_FA, Arrays.asList(REMARK,QUE_REN_DAO_DA));
        table.put(SP,YI_DAO_DA, Arrays.asList(REMARK,BEN_CI_WEI_WAN_GONG,QUE_REN_WAN_GONG));
        table.put(SP,YI_WAN_GONG, Collections.singletonList(REMARK));
        table.put(SP,YI_QU_XIAO, Collections.singletonList(REMARK));


        table.put(MANAGER,DAI_WANG_DIAN_PAI_GONG, Arrays.asList(REMARK,GAI_PAI_WANG_DIAN,ZHI_PAI_GONG_CHENG_SHI));
        table.put(MANAGER,DAI_YU_YUE, Arrays.asList(REMARK,GAI_PAI_GONG_CHENG_SHI,QUE_REN_YU_YUE));
        table.put(MANAGER,YI_YU_YUE, Arrays.asList(REMARK,GAI_PAI_GONG_CHENG_SHI,QUE_REN_CHU_FA));
        table.put(MANAGER,YI_CHU_FA, Arrays.asList(REMARK,QUE_REN_DAO_DA));
        table.put(MANAGER,YI_DAO_DA, Arrays.asList(REMARK,BEN_CI_WEI_WAN_GONG,QUE_REN_WAN_GONG));
        table.put(MANAGER,YI_WAN_GONG, Collections.singletonList(REMARK));
        table.put(MANAGER,YI_QU_XIAO, Collections.singletonList(REMARK));

        table.put(ENGINEER,DAI_YU_YUE, Arrays.asList(REMARK,QUE_REN_YU_YUE));
        table.put(ENGINEER,YI_YU_YUE, Arrays.asList(REMARK,QUE_REN_CHU_FA));
        table.put(ENGINEER,YI_CHU_FA, Arrays.asList(REMARK,QUE_REN_DAO_DA));
        table.put(ENGINEER,YI_DAO_DA, Arrays.asList(REMARK,BEN_CI_WEI_WAN_GONG,QUE_REN_WAN_GONG));
        table.put(ENGINEER,YI_WAN_GONG, Collections.singletonList(REMARK));
        table.put(ENGINEER,YI_QU_XIAO, Collections.singletonList(REMARK));

        System.out.println(System.currentTimeMillis()-l1);

        System.out.println(table.get(ENGINEER,DAI_YU_YUE));

        System.out.println(table.row(ENGINEER).keySet());
        
        
        
        
    }
}

enum Role{
    SP,
    MANAGER,
    ENGINEER
}

enum Status{
    DAI_JIE_DAN,
    DAI_PAI_GONG,
    DAI_WANG_DIAN_PAI_GONG,
    DAI_YU_YUE,
    YI_YU_YUE,
    YI_CHU_FA,
    YI_DAO_DA,
    YI_WAN_GONG,
    YI_QU_XIAO
}

enum Button{
    REMARK,
    JIE_DAN,
    ZHI_PAI_WANG_DIAN,
    GAI_PAI_WANG_DIAN,
    ZHI_PAI_GONG_CHENG_SHI,
    GAI_PAI_GONG_CHENG_SHI,
    QUE_REN_YU_YUE,
    QUE_REN_CHU_FA,
    QUE_REN_DAO_DA,
    BEN_CI_WEI_WAN_GONG,
    QUE_REN_WAN_GONG,
}

