package site.zhangsun.example.jtaatomikos.service;

import site.zhangsun.example.jtaatomikos.pojo.entity.JtaEntity;

/**
 * Functions: JTA Service
 *
 * @author Murphy Zhang Sun
 * @date 2019-07-03 18:53
 * @since v4.0.1
 */
public interface JtaService {
    int insert(JtaEntity jtaEntity);
}