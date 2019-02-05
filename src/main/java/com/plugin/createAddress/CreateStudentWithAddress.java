package com.plugin.createAddress;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orientechnologies.orient.server.config.OServerCommandConfiguration;
import com.orientechnologies.orient.server.network.protocol.http.OHttpRequest;
import com.orientechnologies.orient.server.network.protocol.http.OHttpResponse;
import com.orientechnologies.orient.server.network.protocol.http.OHttpUtils;
import com.orientechnologies.orient.server.network.protocol.http.command.OServerCommandAuthenticatedDbAbstract;
import com.plugin.commonUtils.CommonUtils;
import com.plugin.commonUtils.DBConfig;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientEdgeType;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import com.tinkerpop.blueprints.impls.orient.OrientVertexType;

public class CreateStudentWithAddress extends OServerCommandAuthenticatedDbAbstract {
  ObjectMapper objectMapper=new ObjectMapper(); 
  public CreateStudentWithAddress(final OServerCommandConfiguration iConfiguration) {
	  super();
  }

  public boolean execute(final OHttpRequest iRequest, OHttpResponse iResponse) throws Exception {
    Map<String, Object> requestMap = objectMapper.readValue(iRequest.content.toString(), HashMap.class);
    addStudentWithAddress(requestMap);
    iResponse.send(OHttpUtils.STATUS_OK_CODE, "OK", OHttpUtils.CONTENT_TEXT_PLAIN, "success",null);
    return false;
  }
  public String[] getNames() {
    return new String[]{"POST|createStudentWithAddress/*"};
  }
  
private void addStudentWithAddress(Map<String, Object> requestMap) {
	OrientGraphFactory factory = new OrientGraphFactory(DBConfig.databaseUrl).setupPool(1,10);
	String result="id : ";
	OrientGraph graph = factory.getTx();
	OrientVertexType vertexType=null;
	OrientEdgeType edgeType=null;
	Vertex v= null;
	 try {
		 
		 vertexType = graph.getVertexType("Student");
		  if(vertexType==null) {
			  vertexType = graph.createVertexType("Student");
		  }
		  Vertex student = graph.addVertex("class:Student");
		 
		 for (Entry<String, Object> entry : requestMap.entrySet()) { 
			 
			 if(entry.getKey().equalsIgnoreCase("addresses")) {
				 List<Map<String, Object>>addresses = (List<Map<String, Object>>) entry.getValue();
				 for (Map<String, Object> address : addresses) {
					 Vertex vAddress=CommonUtils.createVertexByType(graph,address,"Address");
					 Edge eLives = student.addEdge("lives", vAddress);
				 }
				}else {
					 System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 
					 student.setProperty(entry.getKey(), entry.getValue());
			   }
	         
		  }
		 graph.commit();
	} catch (Exception e) {
		 graph.rollback();
		e.printStackTrace();
	} finally {
		graph.shutdown();
	}	
}  
}