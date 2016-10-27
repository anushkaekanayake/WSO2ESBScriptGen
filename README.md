Automating tool for generating ESB script mediator (.js) which requires when converting SOAP webservice and expose it as a REST webservice via WSO2 ESB

<b>Requirements</b>

1. First need to get soap request and select the required soap body section
2. Annotate soap request with the given annotations

<b>Annotations</b>
<table>
<thead><th>Annotation</th><th>Description</th><th>Using Location</th></thead>
<tbody>
<tr><td>&lt;!--optional--&gt;</td><td>Uses to annotate an optiona property</td><td>Upper line to the tag</td></tr>
<tr><td>&lt;!--loop--&gt;</td><td>Uses to annotate a repeating object</td><td>Upper line to the starting tag of the repeating object. (refer sample)</td></tr>
<tr><td>&lt;!--end--&gt;</td><td>Uses to annotate the ending of the repeating object</td><td>Bellowing Line of the ending tag of the repeating Object</td></tr>
</tbody>
</table>

<b>Sample annotated SOAP request</b>
```<api>
	<header>
		<id>?</id>
		<!--optional-->
		<version>?</version>
	</header>
	<name>?</name>
	<age>?</age>
	<!--loop-->
	<tp>
		<no>?</no>
	</tp>
	<!--end-->
	<obj>
		<!--optional-->
		<aaa>
			<aa1>?</aa1>
			<!--optional-->
			<aa2>?</aa2>
			<!--loop-->
			<aa3>
				<id>?</id>
			</aa3>
			<!--end-->
		</aaa>
		<bbb>?</bbb>
	</obj>
</api>
```

<b>Sample OutPut - Generated js code for script mediator for POST or PUT type REST API</b>
```
var requestObj = mc.getPayloadJSON();
var request=&quot;&lt;api&gt;&quot;;
request=request+&quot;&lt;header&gt;&quot;;
if(typeof(requestObj.api.header.id)!=&quot;undefined&quot;){
request=request+&quot;&lt;id&gt;&quot;+requestObj.api.header.id+&quot;&lt;/id&gt;&quot;;
}
if(typeof(requestObj.api.header.version)!=&quot;undefined&quot;){
request=request+&quot;&lt;version&gt;&quot;+requestObj.api.header.version+&quot;&lt;/version&gt;&quot;;
}
request=request+&quot;&lt;/header&gt;&quot;;
if(typeof(requestObj.api.name)!=&quot;undefined&quot;){
request=request+&quot;&lt;name&gt;&quot;+requestObj.api.name+&quot;&lt;/name&gt;&quot;;
}
if(typeof(requestObj.api.age)!=&quot;undefined&quot;){
request=request+&quot;&lt;age&gt;&quot;+requestObj.api.age+&quot;&lt;/age&gt;&quot;;
}
if (typeof (requestObj.api.tp) != &quot;undefined&quot;) {
 for ( var index = 0; index &lt; requestObj.api.tp.length; index++) {
request=request+&quot;&lt;tp&gt;&quot;;
if(typeof(requestObj.api.tp[index].no)!=&quot;undefined&quot;){
request=request+&quot;&lt;no&gt;&quot;+requestObj.api.tp[index].no+&quot;&lt;/no&gt;&quot;;
}
request= request + &quot;&lt;/tp&gt;&quot;;
}
}
request=request+&quot;&lt;obj&gt;&quot;;
request=request+&quot;&lt;aaa&gt;&quot;;
if(typeof(requestObj.api.obj.aaa.aa1)!=&quot;undefined&quot;){
request=request+&quot;&lt;aa1&gt;&quot;+requestObj.api.obj.aaa.aa1+&quot;&lt;/aa1&gt;&quot;;
}
if(typeof(requestObj.api.obj.aaa.aa2)!=&quot;undefined&quot;){
request=request+&quot;&lt;aa2&gt;&quot;+requestObj.api.obj.aaa.aa2+&quot;&lt;/aa2&gt;&quot;;
}
if (typeof (requestObj.api.obj.aaa.aa3) != &quot;undefined&quot;) {
 for ( var index = 0; index &lt; requestObj.api.obj.aaa.aa3.length; index++) {
request=request+&quot;&lt;aa3&gt;&quot;;
if(typeof(requestObj.api.obj.aaa.aa3[index].id)!=&quot;undefined&quot;){
request=request+&quot;&lt;id&gt;&quot;+requestObj.api.obj.aaa.aa3[index].id+&quot;&lt;/id&gt;&quot;;
}
request= request + &quot;&lt;/aa3&gt;&quot;;
}
}
request=request+&quot;&lt;/aaa&gt;&quot;;
if(typeof(requestObj.api.obj.bbb)!=&quot;undefined&quot;){
request=request+&quot;&lt;bbb&gt;&quot;+requestObj.api.obj.bbb+&quot;&lt;/bbb&gt;&quot;;
}
request=request+&quot;&lt;/obj&gt;&quot;;
request=request+&quot;&lt;/api&gt;&quot;;
mc.setPayloadXML(new XML(request));
```



