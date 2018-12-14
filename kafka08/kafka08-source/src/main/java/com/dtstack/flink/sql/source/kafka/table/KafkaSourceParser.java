/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

 

package com.dtstack.flink.sql.source.kafka.table;

import com.dtstack.flink.sql.table.AbsSourceParser;
import com.dtstack.flink.sql.table.TableInfo;
import com.dtstack.flink.sql.util.MathUtil;

import java.util.Map;


public class KafkaSourceParser extends AbsSourceParser {

    @Override
    public TableInfo getTableInfo(String tableName, String fieldsInfo, Map<String, Object> props) {

        KafkaSourceTableInfo kafka08SourceTableInfo = new KafkaSourceTableInfo();
        kafka08SourceTableInfo.setName(tableName);
        parseFieldsInfo(fieldsInfo, kafka08SourceTableInfo);
        kafka08SourceTableInfo.setParallelism(MathUtil.getIntegerVal(props.get(KafkaSourceTableInfo.PARALLELISM_KEY.toLowerCase())));
        if (props.get(KafkaSourceTableInfo.SOURCE_DATA_TYPE) != null) {
            kafka08SourceTableInfo.setSourceDataType(props.get(KafkaSourceTableInfo.SOURCE_DATA_TYPE).toString());
        }
        if (props.get(KafkaSourceTableInfo.FIELD_DELINITER) != null) {
            kafka08SourceTableInfo.setFieldDelimiter(props.get(KafkaSourceTableInfo.FIELD_DELINITER).toString());
        }
        if (props.get(KafkaSourceTableInfo.LENGTH_CHECK_POLICY) != null) {
            kafka08SourceTableInfo.setLengthCheckPolicy(props.get(KafkaSourceTableInfo.LENGTH_CHECK_POLICY).toString());
        }
        for (String key:props.keySet()) {
            if (!key.isEmpty() && key.startsWith("kafka.")) {
                kafka08SourceTableInfo.addKafkaParam(key.substring(6), props.get(key).toString());
            }
        }
        return kafka08SourceTableInfo;
    }
}
