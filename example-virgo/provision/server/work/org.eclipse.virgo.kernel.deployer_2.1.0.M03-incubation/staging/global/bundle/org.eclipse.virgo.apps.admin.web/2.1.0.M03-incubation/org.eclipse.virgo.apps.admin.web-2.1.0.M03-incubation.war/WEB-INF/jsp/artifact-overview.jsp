<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %><%-- 
--%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%-- 
--%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %><%-- 
--%><jsp:include page="top.jsp" />

<c:choose>
		<c:when test="${empty result}">
			<h1>Artifact Console</h1>
		</c:when>
		<c:otherwise>
			<h1>Artifact Console: '${result}'</h1>
		</c:otherwise>
</c:choose>

<script language="JavaScript" type="text/javascript">
/* <![CDATA[ */
	function validateUploadForm() {
		if (document.uploadForm.application.value == null || document.uploadForm.application.value == '') {
			alert("Please select the artifact you would like to upload.");
			document.uploadForm.application.focus();
			return false;
		} else {
			return true;
		}
	}
/* ]]> */
</script>


<form name="uploadForm" action="<c:url value="deploy.htm" />" method="post" enctype="multipart/form-data">
	<table id="deploy_application" class="bordered-table">
		<tr>
			<th colspan="2">Select an artifact to upload and deploy to Virgo Web Server</th>
		</tr>
		<tr>
			<td id="deploy_application_file" ><input id="deploy_application_file_field" type="file" name="application" size="80"/></td>
			<td id="deploy_application_submit" ><input id="deploy_application_submit_button" type="submit" value="Upload" onclick="validateUploadForm"/></td>
		</tr>
	</table>     
</form>
<br/>

<script type="text/javascript" language="Javascript">
/* <![CDATA[ */
	dojo.require("dijit.tree.ForestStoreModel");
	dojo.require("dijit.Tree");
	dojo.require("dojox.data.QueryReadStore");
	dojo.require("dijit.form.Button");

//Data Model
	
	var treeStore = new dojox.data.QueryReadStore({url: "data"});

//Tree Model
	
	function loadChildren(parentItem, complete_cb, error_cb) {
		if(this.store.hasAttribute(parentItem, "name")) {
			requestType    = this.store.getValue(parentItem, "type");
			requestName    = this.store.getValue(parentItem, "name");
			requestVersion = this.store.getValue(parentItem, "version");
			requestParentId    = this.store.getValue(parentItem, "id");
			this.store.fetch({ query: {parent: requestParentId, type: requestType, name: requestName, version: requestVersion}, onComplete: complete_cb, onError: error_cb});
		} else if (this.store.hasAttribute(parentItem, "type")) {
			requestType        = this.store.getValue(parentItem, "type");
			requestParentId    = this.store.getValue(parentItem, "id");
			this.store.fetch({ query: {parent: requestParentId, type: requestType}, onComplete: complete_cb, onError: error_cb});
		}
		return this.inherited(arguments);	
	}
	
	dojo.declare("console.ForestStoreModel", dijit.tree.ForestStoreModel, { getChildren: loadChildren });

//Tree Node
	
	function getNewImageNode(iconClass){
		var newNode = dojo.doc.createElement('img');  // dojo.doc is the current document.
		dojo.attr(newNode, "class", "dijitTreeIcon treeAtribute-" + iconClass.toLowerCase());
		dojo.attr(newNode, "wairole", "dijitTreeIcon");
		dojo.attr(newNode, "dojoattachpoint", "iconNode");
		dojo.attr(newNode, "alt", "");
		dojo.attr(newNode, "title", iconClass);
		dojo.attr(newNode, "src", "../../resources/js/dojo/resources/blank.gif");
		dojo.attr(newNode, "role", "presentation");
		return newNode;
	}
	
	function nodePostCreate() {
		if(!this.item.root){
			dojo.attr(this.domNode, "id", this.item.i.id); //Set the id of any node other than the root

			if(treeStore.hasAttribute(this.item, "children")) { //Add any extra icons
	
				var firstRenderedNode = this.domNode.firstElementChild;
				if(firstRenderedNode != null){
					
					var nextNode = firstRenderedNode.lastElementChild;
					if(nextNode != null){
						
						var goal = this.domNode.firstElementChild.lastElementChild.firstElementChild;
						if(goal != null){
							if(treeStore.hasAttribute(this.item, "tooltip")) {
								dojo.attr(goal, "title", treeStore.getValue(this.item, "tooltip"));
							} 		
							if(treeStore.hasAttribute(this.item, "state")) {					
								var newNode = getNewImageNode(treeStore.getValue(this.item, "state"));
								dojo.place(newNode, goal, "after");
							} 
						}
						
					}
					
				}
				
			}
		
		}
		this.inherited(arguments);	
	}
	
	dojo.declare("console.TreeNode", dijit._TreeNode, { postCreate: nodePostCreate });

//Tree
	
	function getTreeIconClass(item, opened){
		var nodeClass = "dijitLeaf";
		if(treeStore.hasAttribute(item, "type")) {
			nodeClass = "treeArtifact-" + treeStore.getValue(item, "type").toLowerCase();
		} else if (treeStore.hasAttribute(item, "icon")){
			nodeClass = "dijitLeaf treeAtribute-" + treeStore.getValue(item, "icon").toLowerCase();
		}
		return nodeClass;
	}
	
	function getTreeLabelClass(item, opened){
		var labelClass = "dijitTreeLabel";
		if(treeStore.hasAttribute(item, "link")) {
			labelClass = "dijitTreeLabel treeLink";
		}
		return labelClass;
	}
	
	function newChild(args) {
		return new console.TreeNode(args);	
	}

	dojo.declare("console.Tree", dijit.Tree, { 
		_createTreeNode: newChild, 
		getIconClass: getTreeIconClass,
		getLabelClass: getTreeLabelClass }
	);

//General page functions
	
	function setButtonsDisabled(disabled) {
		startButton.setDisabled(disabled);
		stopButton.setDisabled(disabled);
		refreshButton.setDisabled(disabled);
		uninstallButton.setDisabled(disabled);
	};
	
	function artifactOperation(operation) {
		setButtonsDisabled(true);
		dojo.xhrGet({
		url: "do/" + operation,
		content: {
			type: treeStore.getValue(lastItem, "type"),
			name: treeStore.getValue(lastItem, "name"),
			version: treeStore.getValue(lastItem, "version")
			},
		handle: function () {
				location = "/admin/web/artifact/overview.htm";
			}
		});	
	}

	function itemClicked(item) {
		if (treeStore.hasAttribute(item, "link")) {
			location = treeStore.getValue(item, "link");
		} else {
			if (treeStore.hasAttribute(item, "type") && treeStore.hasAttribute(item, "name")) {
				setButtonsDisabled(false);
				lastItem = item;
			} else {
				setButtonsDisabled(true);
			}
		}
	}	
	
/* ]]> */
</script>

<div dojoType="console.ForestStoreModel" store="treeStore" childrenAttrs="children" jsId="treeModel"> </div>

<div class="consoleContentPane">

	<div jsId="startButton" dojoType="dijit.form.Button" disabled="true">
		Start
		<script type="dojo/connect" event="onClick">
			artifactOperation("start");
		</script>
	</div>  
	
	<div jsId="stopButton" dojoType="dijit.form.Button" disabled="true">
		Stop
		<script type="dojo/connect" event="onClick">
			artifactOperation("stop");
		</script>
	</div>  
	
	<div jsId="refreshButton" dojoType="dijit.form.Button" disabled="true">
		Refresh
		<script type="dojo/connect" event="onClick">
			artifactOperation("refresh");
		</script>
	</div>  
	
	<div jsId="uninstallButton" dojoType="dijit.form.Button" disabled="true">
		Uninstall
		<script type="dojo/connect" event="onClick">
			artifactOperation("uninstall");
		</script>
	</div>
	<div class="consoleFootnote">
		Select an Artifact in the tree to perform an action upon it.
	</div>
	<div id="preloader" class="consoleLoading">
		Loading the Artifact Tree...	
	</div>
	<p>
		<div dojoType="console.Tree" model="treeModel" jsId="itemTree" persist="false"  showRoot="false">
			<script type="dojo/connect" event="onClick" args="item">
				itemClicked(item);
			</script>
			<script type="dojo/connect" event="onClose" args="item">
				itemClicked(item);
			</script>
			<script type="dojo/connect" event="onOpen" args="item">
                dojo.style("preloader", "display", "none");
				itemClicked(item);
			</script>
		</div>
	</p>
</div>

<jsp:include page="bottom.jsp" />