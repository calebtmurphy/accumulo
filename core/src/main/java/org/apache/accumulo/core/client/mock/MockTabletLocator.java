/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.accumulo.core.client.mock;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.accumulo.core.client.AccumuloException;
import org.apache.accumulo.core.client.AccumuloSecurityException;
import org.apache.accumulo.core.client.TableNotFoundException;
import org.apache.accumulo.core.client.impl.TabletLocator;
import org.apache.accumulo.core.data.KeyExtent;
import org.apache.accumulo.core.data.Mutation;
import org.apache.accumulo.core.data.Range;
import org.apache.accumulo.core.security.Credentials;
import org.apache.hadoop.io.Text;

public class MockTabletLocator extends TabletLocator {
  public MockTabletLocator() {}
  
  @Override
  public TabletLocation locateTablet(Credentials credentials, Text row, boolean skipRow, boolean retry) throws AccumuloException, AccumuloSecurityException,
      TableNotFoundException {
    throw new UnsupportedOperationException();
  }
  
  @Override
  public <T extends Mutation> void binMutations(Credentials credentials, List<T> mutations, Map<String,TabletServerMutations<T>> binnedMutations, List<T> failures)
      throws AccumuloException, AccumuloSecurityException, TableNotFoundException {
    TabletServerMutations<T> tsm = new TabletServerMutations<T>();
    for (T m : mutations)
      tsm.addMutation(new KeyExtent(), m);
    binnedMutations.put("", tsm);
  }
  
  @Override
  public List<Range> binRanges(Credentials credentials, List<Range> ranges, Map<String,Map<KeyExtent,List<Range>>> binnedRanges) throws AccumuloException,
      AccumuloSecurityException, TableNotFoundException {
    binnedRanges.put("", Collections.singletonMap(new KeyExtent(new Text(), null, null), ranges));
    return Collections.emptyList();
  }
  
  @Override
  public void invalidateCache(KeyExtent failedExtent) {}
  
  @Override
  public void invalidateCache(Collection<KeyExtent> keySet) {}
  
  @Override
  public void invalidateCache() {}
  
  @Override
  public void invalidateCache(String server) {}
}