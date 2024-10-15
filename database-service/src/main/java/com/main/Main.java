package com.main;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.nio.file.Paths;

  public class Main {
    public static void main(String[] args) {
      FastAutoGenerator.create("jdbc:mysql://192.168.1.107:3306/spring_security_leaning?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8", "root", "123456")
          .globalConfig(builder -> builder
              .outputDir("C:\\Users\\Mark42\\IdeaProjects\\SpringProject\\database-service\\src\\main\\java")
              .author("Your Name") // 添加作者信息
              .enableSwagger() // 开启 Swagger 模式
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
              .enableLombok() // 开启 Lombok
              .build() // 必须调用 build()
              .mapperBuilder()
              .build() // 必须调用 build()
              .serviceBuilder()
              .build() // 必须调用 build()
              .controllerBuilder()
              .build()// 必须调用 build()
          )
          .templateEngine(new FreemarkerTemplateEngine())
          .execute();
    }
}