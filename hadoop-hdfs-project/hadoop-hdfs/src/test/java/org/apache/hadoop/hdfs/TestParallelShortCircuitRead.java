/**
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
package org.apache.hadoop.hdfs;

import java.io.File;

import org.apache.hadoop.net.unix.DomainSocket;
import org.apache.hadoop.net.unix.TemporarySocketDirectory;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class TestParallelShortCircuitRead extends TestParallelReadUtil {
  private static TemporarySocketDirectory sockDir;

  @BeforeClass
  static public void setupCluster() throws Exception {
    sockDir = new TemporarySocketDirectory();
    HdfsConfiguration conf = new HdfsConfiguration();
    conf.set(DFSConfigKeys.DFS_DATANODE_DOMAIN_SOCKET_PATH_KEY,
      new File(sockDir.getDir(), "TestParallelLocalRead.%d.sock").getAbsolutePath());
    conf.setBoolean(DFSConfigKeys.DFS_CLIENT_READ_SHORTCIRCUIT_KEY, true);
    conf.setBoolean(DFSConfigKeys.
        DFS_CLIENT_READ_SHORTCIRCUIT_SKIP_CHECKSUM_KEY, false);
    DomainSocket.disableBindPathValidation();
    setupCluster(1, conf);
  }

  @AfterClass
  static public void teardownCluster() throws Exception {
    sockDir.close();
    TestParallelReadUtil.teardownCluster();
  }
}