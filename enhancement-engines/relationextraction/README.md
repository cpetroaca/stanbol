<!-- 
  Licensed to the Apache Software Foundation (ASF) under one or more
  contributor license agreements.  See the NOTICE file distributed with
  this work for additional information regarding copyright ownership.
  The ASF licenses this file to You under the Apache License, Version 2.0
  (the "License"); you may not use this file except in compliance with
  the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
-->

# Relation extraction engine

In order to be fully operational, the engine needs to be updated with the dbpedia_ontology_map file. To do so, follow these steps:

1. After stanbol is started, go to the Administration Console and stop the relationextraction bundle
2. Copy the file https://github.com/cpetroaca/stanbol-indices/releases/download/trunk-relation-dbpedia-ontologimappimgs/dbpedia_ontology_map to STANBOL_FOLDER/datafiles/
3. Start the relationextraction bundle.
