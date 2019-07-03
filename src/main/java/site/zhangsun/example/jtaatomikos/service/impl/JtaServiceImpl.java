package site.zhangsun.example.jtaatomikos.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.zhangsun.example.jtaatomikos.mapper.master.JtaMasterMapper;
import site.zhangsun.example.jtaatomikos.mapper.slaver01.JtaSlaver01Mapper;
import site.zhangsun.example.jtaatomikos.pojo.entity.JtaEntity;
import site.zhangsun.example.jtaatomikos.service.JtaService;

/**
 * Functions: JTA Service Implement
 *
 * @author Murphy Zhang Sun
 * @date 2019-07-03 18:53
 * @since v4.0.1
 */
@Slf4j
@Service
public class JtaServiceImpl implements JtaService {

    private final JtaMasterMapper jtaMasterMapper;
    private final JtaSlaver01Mapper jtaSlaver01Mapper;

    public JtaServiceImpl(JtaMasterMapper jtaMasterMapper, JtaSlaver01Mapper jtaSlaver01Mapper) {
        this.jtaMasterMapper = jtaMasterMapper;
        this.jtaSlaver01Mapper = jtaSlaver01Mapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(JtaEntity jtaEntity) {
        jtaMasterMapper.insert(jtaEntity);
        if (StringUtils.isNotBlank(jtaEntity.getName())) {
            Integer name = Integer.valueOf(jtaEntity.getName());
            log.info("Cast Successfully! name: {}", name);
        }
        jtaSlaver01Mapper.insert(jtaEntity);
        return 0;
    }
}