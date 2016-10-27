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
var request="<api>";
request=request+"<header>";
if(typeof(requestObj.api.header.id)!="undefined"){
request=request+"<id>"+requestObj.api.header.id+"</id>";
}
if(typeof(requestObj.api.header.version)!="undefined"){
request=request+"<version>"+requestObj.api.header.version+"</version>";
}
request=request+"</header>";
if(typeof(requestObj.api.name)!="undefined"){
request=request+"<name>"+requestObj.api.name+"</name>";
}
if(typeof(requestObj.api.age)!="undefined"){
request=request+"<age>"+requestObj.api.age+"</age>";
}
if (typeof (requestObj.api.tp) != "undefined") {
 for ( var index = 0; index < requestObj.api.tp.length; index++) {
request=request+"<tp>";
if(typeof(requestObj.api.tp[index].no)!="undefined"){
request=request+"<no>"+requestObj.api.tp[index].no+"</no>";
}
request= request + "</tp>";
}
}
request=request+"<obj>";
request=request+"<aaa>";
if(typeof(requestObj.api.obj.aaa.aa1)!="undefined"){
request=request+"<aa1>"+requestObj.api.obj.aaa.aa1+"</aa1>";
}
if(typeof(requestObj.api.obj.aaa.aa2)!="undefined"){
request=request+"<aa2>"+requestObj.api.obj.aaa.aa2+"</aa2>";
}
if (typeof (requestObj.api.obj.aaa.aa3) != "undefined") {
 for ( var index = 0; index < requestObj.api.obj.aaa.aa3.length; index++) {
request=request+"<aa3>";
if(typeof(requestObj.api.obj.aaa.aa3[index].id)!="undefined"){
request=request+"<id>"+requestObj.api.obj.aaa.aa3[index].id+"</id>";
}
request= request + "</aa3>";
}
}
request=request+"</aaa>";
if(typeof(requestObj.api.obj.bbb)!="undefined"){
request=request+"<bbb>"+requestObj.api.obj.bbb+"</bbb>";
}
request=request+"</obj>";
request=request+"</api>";
mc.setPayloadXML(new XML(request));
```



