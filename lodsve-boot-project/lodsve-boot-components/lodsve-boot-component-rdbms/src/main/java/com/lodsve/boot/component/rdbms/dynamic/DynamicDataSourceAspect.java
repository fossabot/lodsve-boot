/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lodsve.boot.component.rdbms.dynamic;

import com.lodsve.boot.component.rdbms.annotations.SwitchDataSource;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * aop动态切换数据源.
 *
 * @author <a href="mailto:sunhao.java@gmail.com">sunhao(sunhao.java@gmail.com)</a>
 * @date 2017/12/14 下午6:16
 */
@Aspect
public class DynamicDataSourceAspect {
    @Around("@annotation(com.lodsve.boot.component.rdbms.annotations.SwitchDataSource)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        String dataSource = StringUtils.EMPTY;
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        if (method.isAnnotationPresent(SwitchDataSource.class)) {
            SwitchDataSource annotation = method.getAnnotation(SwitchDataSource.class);
            // 取出注解中的数据源名
            dataSource = annotation.value();
        }

        try (DataSourceHolder dsh = DataSourceHolder.getInstance()) {
            dsh.set(dataSource);
            return point.proceed();
        }
    }
}
