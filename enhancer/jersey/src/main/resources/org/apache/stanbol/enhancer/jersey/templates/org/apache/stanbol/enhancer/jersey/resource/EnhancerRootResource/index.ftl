<#--
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
<#import "/imports/common.ftl" as common>
<#escape x as x?html>
<@common.page title="Apache Stanbol Enhancer" hasrestapi=true>


<div class="panel" id="webview">
<#if !it.executionNodes??>
  <p><em>There seams to be a problem with the Enhancement Chain <b>${it.chain.name}</b>. 
   To fix this an Administrator needs to install, configure and enable enhancement 
   chains and -engines by using the <a href="/system/console">OSGi console</a>.</em></p>
<#elseif it.executionNodes?size == 0>
  <p><em>There is no active engines for Enhancement Chain <b>${it.chain.name}</b>. 
   Administrators can install, configure and enable enhancement chains and 
   -engines by using the <a href="/system/console">OSGi console</a>.</em></p>
<#else>
  <#assign executionNodes = it.executionNodes>
  <div class="enginelisting">
  <#if it.chainAvailable>
    <div class="collapsed">
  <#else>
    <div>
  </#if>
  <p class="collapseheader">Enhancement Chain: 
    <#if it.chainAvailable>
      <span style="color:#006600">
    <#else>
      <span style="color:#660000">
    </#if>
    <strong>${it.chain.name}</strong></span> 
    <#if it.activeNodes?size &lt; it.executionNodes?size>
      <strong>${it.activeNodes?size}/</strong><#else> all </#if><strong>${it.executionNodes?size}</strong>
    engines available 
      <span style="float: right; margin-right: 25px;">
        &lt; List of <a href="#">Enhancement Chains</a> &gt;
      </span>
    </p>
    <div class="collapsable">
    <ul>
      <#list executionNodes as node>
        <li>
        <#if node.engineActive>
          <span style="color:#006600">
        <#elseif node.optional>
          <span style="color:#666666">
        <#else>
          <span style="color:#660000">
        </#if>
          <b>${node.engineName}</b> 
          <small>(
          <#if node.optional> optional <#else> required </#if>, 
          <#if node.engineActive>
            ${node.engine.class.simpleName})</small></li>
          <#else>
            currently not available)</small>
          </span>
          </li>
        </#if>
      </#list>
    </ul>
    <p class="note">You can enable, disable and deploy new engines using the
      <a href="/system/console/components">OSGi console</a>.</p>
    </div>
    </div>
  </div>
  
<script>
$(".enginelisting p").click(function () {
  $(this).parents("div").toggleClass("collapsed");
})
.find("a").click(function(e){
    e.stopPropagation();
    //link to all active Enhancement Chains
    window.location = "${it.publicBaseUri}enhancer/chain";
    return false;
});     
</script>
</#if>
<#if it.chainAvailable>
  <p>Paste some text below and submit the form to let the Enhancement Chain ${it.chain.name} enhance it:</p>
  <form id="enginesInput" method="POST" accept-charset="utf-8">
    <p><textarea rows="15" name="content"></textarea></p>
    <p class="submitButtons">Output format:
      <select name="format">
      	<option value="application/json">JSON-LD</option>
        <option value="application/rdf+xml">RDF/XML</option>
        <option value="application/rdf+json">RDF/JSON</option>
        <option value="text/turtle">Turtle</option>
        <option value="text/rdf+nt">N-TRIPLES</option>
      </select> <input class="submit" type="submit" value="Run engines">
    </p>
  </form>
<script language="javascript">
function registerFormHandler() {
   $("#enginesInput input.submit", this).click(function(e) {
     // disable regular form click
     e.preventDefault();
     
     var data = {
       content: $("#enginesInput textarea[name=content]").val(),
       ajax: true,
       format:  $("#enginesInput select[name=format]").val()
     };
     var base = window.location.href.replace(/\/$/, "");
     
     $("#enginesOuputWaiter").show();
     
     // submit the form query using Ajax
     $.ajax({
       type: "POST",
       url: base,
       data: data,
       dataType: "html",
       cache: false,
       success: function(result) {
         $("#enginesOuputWaiter").hide();
         $("#enginesOuput").html(result);
       },
       error: function(result) {
         $("#enginesOuputWaiter").hide();
         $("#enginesOuput").text('Invalid query.');
       }
     });
   });
 }
 $(document).ready(registerFormHandler);
</script>
  <div id="enginesOuputWaiter" style="display: none">
    <p>Stanbol is analysing your content...</p>
    <p><img alt="Waiting..." src="${it.staticRootUrl}/home/images/ajax-loader.gif" /></p>
  </div>
  <p id="enginesOuput"></p>
</#if>
</div>

<div class="panel" id="restapi" style="display: none;">
<h3>Stateless REST analysis</h3>

<p>This stateless interface allows the caller to submit content to the Stanbol enhancer engines and
get the resulting enhancements formatted as RDF at once without storing anything on the
server-side.</p>

<p>The content to analyze should be sent in a POST request with the mimetype specified in
the <code>Content-type</code> header. The response will hold the RDF enhancement serialized
in the format specified in the <code>Accept</code> header:</p>
   
<pre>
curl -X POST -H "Accept: text/turtle" -H "Content-type: text/plain" \
     --data "John Smith was born in London." ${it.serviceUrl}
</pre> 

<p>The list of mimetypes accepted as inputs depends on the deployed engines. By default only
 <code>text/plain</code> content will be analyzed</p>
 
<p>Stanbol enhancer is able to serialize the response in the following RDF formats:</p>
<ul>
<li><code>application/json</code> (JSON-LD)</li>
<li><code>application/rdf+xml</code> (RDF/XML)</li>
<li><code>application/rdf+json</code> (RDF/JSON)</li>
<li><code>text/turtle</code> (Turtle)</li>
<li><code>text/rdf+nt</code> (N-TRIPLES)</li>
</ul> 

<p> Additional supported QueryParameters:<ul>
<li><code>uri={content-item-uri}</code>: By default the URI of the content 
    item being enhanced is a local, non de-referencable URI automatically built 
    out of a hash digest of the binary content. Sometimes it might be helpful 
    to provide the URI of the content-item to be used in the enhancements RDF 
    graph.
<code>uri</code> request parameter
<li><code>executionmetadata=true/false</code>: 
    Allows the include of execution metadata in the response. Such data include
    the ExecutionPlan as provided by the enhancement chain as well as
    information about the actual execution of that plan. The default value
    is <code>false</code>.</li>
</ul>

<p>The following example shows how to send an enhancement request with a
custom content item URI that will include the execution metadata in the
response.</p>

<pre>
curl -X POST -H "Accept: text/turtle" -H "Content-type: text/plain" \
     --data "John Smith was born in London." \
     "${it.serviceUrl}?uri=urn:fise-example-content-item&executionmetadata=true"
</pre> 

</div>


</@common.page>
</#escape>
