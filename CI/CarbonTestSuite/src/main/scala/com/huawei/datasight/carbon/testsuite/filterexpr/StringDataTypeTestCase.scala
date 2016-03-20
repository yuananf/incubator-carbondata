/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.huawei.datasight.carbon.testsuite.filterexpr

import org.scalatest.BeforeAndAfter
import org.apache.spark.sql.common.util.QueryTest
import org.apache.spark.sql.common.util.CarbonHiveContext._
import org.apache.spark.sql.Row
import com.huawei.unibi.molap.datastorage.store.impl.FileFactory
import com.huawei.datasight.molap.load.MolapLoaderUtil

/**
 * Test Class for filter expression query on String datatypes
 * @author N00902756
 *
 */
class StringDataTypeTestCase extends QueryTest with BeforeAndAfter {
  
  import org.apache.spark.sql.common.util.CarbonHiveContext.implicits._
  
  before
  {
	  sql("CREATE CUBE stringtypecube DIMENSIONS (empname String, designation String, workgroupcategoryname String, deptname String) OPTIONS (PARTITIONER [PARTITION_COUNT=1])");
	  sql("LOAD DATA fact from './TestData/data.csv' INTO CUBE stringtypecube PARTITIONDATA(DELIMITER ',', QUOTECHAR '\"')");
  }
  test("select empname from stringtypecube") {
    checkAnswer(
      sql("select empname from stringtypecube"),
      Seq(Row("arvind"),Row("krithin"),Row("madhan"),Row("anandh"),Row("ayushi"),
          Row("pramod"),Row("gawrav"),Row("sibi"),Row("shivani"),Row("bill")))
  }
  after
  {
	  sql("drop cube stringtypecube")
  }
}