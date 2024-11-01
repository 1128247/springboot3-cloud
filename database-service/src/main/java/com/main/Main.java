package com.main;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.nio.file.Paths;

  public class Main {
    public static void main(String[] args) {
      FastAutoGenerator.create("jdbc:mysql://192.168.1.102:3306/spring_security_leaning?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8", "root", "123456")
          .globalConfig(builder -> builder
              .outputDir("C:\\Users\\Mark42\\IdeaProjects\\springboot3-cloud\\database-service\\src\\main\\java")
              .author("Andres") // 添加作者信息
              .commentDate("yyyy-MM-dd")
          )
          .packageConfig(builder -> builder
              .parent("com.main")
              .entity("entity")
              .mapper("mapper")
              .xml("mapper.xml")
          )
          .strategyConfig(builder -> builder
              .entityBuilder()
              .enableFileOverride()//是否覆盖
              .enableLombok()// 开启 Lombok
              .build() // 必须调用 build()
              .mapperBuilder()
              .enableFileOverride()
              .enableBaseColumnList()
              .enableBaseResultMap()
              .build() // 必须调用 build()
              .serviceBuilder().disable()
              .build() // 必须调用 build()
              .controllerBuilder().disable()
              .build()// 必须调用 build()

          )
          .templateEngine(new FreemarkerTemplateEngine())
          .execute();
    }
}