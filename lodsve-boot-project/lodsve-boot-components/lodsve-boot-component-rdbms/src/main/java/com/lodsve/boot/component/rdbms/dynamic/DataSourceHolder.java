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

/**
 * 多数据源保存选择的数据源.
 *
 * @author <a href="mailto:sunhao.java@gmail.com">sunhao(sunhao.java@gmail.com)</a>
 * @date 2017/12/14 下午6:03
 */
public class DataSourceHolder implements AutoCloseable {
    private static final ThreadLocal<String> DATA_SOURCE = new ThreadLocal<>();
    private static final DataSourceHolder INSTANCE = new DataSourceHolder();

    public static DataSourceHolder getInstance() {
        return INSTANCE;
    }

    public String get() {
        return DATA_SOURCE.get();
    }

    public void set(String dataSource) {
        DATA_SOURCE.set(dataSource);
    }

    @Override
    public void close() {
        DATA_SOURCE.remove();
    }
}
