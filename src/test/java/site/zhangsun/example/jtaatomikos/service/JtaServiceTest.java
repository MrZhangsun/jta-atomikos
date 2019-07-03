package site.zhangsun.example.jtaatomikos.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import site.zhangsun.example.jtaatomikos.JtaAtomikosApplicationTests;
import site.zhangsun.example.jtaatomikos.pojo.entity.JtaEntity;

public class JtaServiceTest extends JtaAtomikosApplicationTests {

    @Autowired
    private JtaService jtaService;

    @Test
    public void insert1() {
        JtaEntity jtaEntity = new JtaEntity();
        jtaEntity.setName("2");
        jtaService.insert(jtaEntity);
    }

    @Test
    public void insert2() {
        JtaEntity jtaEntity = new JtaEntity();
        jtaEntity.setName("a");
        jtaService.insert(jtaEntity);
    }
}