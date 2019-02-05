package com.plugin.commonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientVertexType;

public class CommonUtils {
	
	  public static Vertex createVertexByType(OrientGraph graph, Map<String, Object> requestMap,String type) {
		   
			OrientVertexType vertexType=null;
			Vertex v = null;
				  vertexType = graph.getVertexType(type);
				  if(vertexType==null) {
					  vertexType = graph.createVertexType(type);
				  }
				  v = graph.addVertex("class:"+type); // 1st OPERATION: IMPLICITLY BEGIN A TRANSACTION
				  for (Entry<String, Object> entry : requestMap.entrySet()) { 
			          System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 
			          v.setProperty(entry.getKey(), entry.getValue());
				  }
				  graph.commit();
			return v;
	  }
	  
	  public static Vertex createVertexByType(Map<String, Object> requestMap,String type) {
			OrientVertexType vertexType=null;
			Vertex v = null;
			OrientGraph graph = DBConfig.getGraphInstance();
				  vertexType = graph.getVertexType(type);
				  if(vertexType==null) {
					  vertexType = graph.createVertexType(type);
				  }
				  v = graph.addVertex("class:"+type); // 1st OPERATION: IMPLICITLY BEGIN A TRANSACTION
				  for (Entry<String, Object> entry : requestMap.entrySet()) { 
			          System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 
			          v.setProperty(entry.getKey(), entry.getValue());
				  }
				  graph.commit();
			return v;
	  }
	  
	
	 
	  
	
	
	
	
	public static void main(String args[]) {
		
		String jsonString="{\r\n" + 
				"    \"sId\":110,\r\n" + 
				"	\"fName\":\"marry\",\r\n" + 
				"	\"lName\":\"kets\",\r\n" + 
				"	\"age\":28,\r\n" + 
				"	\"gender\":\"M\",\r\n" + 
				"	\"addresses\": [\r\n" + 
				"	    {\r\n" + 
				"	      \"city\": \"Pune\",\r\n" + 
				"	      \"street\": \"Vimannagar\",\r\n" + 
				"	      \"pincode\": 411016\r\n" + 
				"	    },\r\n" + 
				"	    {\r\n" + 
				"	      \"city\": \"Vadodara\",\r\n" + 
				"	      \"street\": \"Race Coarse\",\r\n" + 
				"	      \"pincode\": 390010\r\n" + 
				"	    }\r\n" + 
				"	  ]\r\n" + 
				"}";
		 ObjectMapper objectMapper=new ObjectMapper(); 
		 try {
			Map<String, Object> requestMap = objectMapper.readValue(jsonString, HashMap.class);
			 for (Entry<String, Object> entry : requestMap.entrySet()) { 
				 if(entry.getKey().equalsIgnoreCase("addresses")) {
					 List<Map<String, Object>>addresses = (List<Map<String, Object>>) entry.getValue();
//					 List<Map<String, Object>>addresses = objectMapper.readValue(Address,new TypeReference<List<Map<String, Object>>>(){});
					 for (Map<String, Object> address : addresses) {
						 for (Entry<String, Object> addressentry : address.entrySet()) { 
							 System.out.println("Key = " + addressentry.getKey() + ", Value = " + addressentry.getValue()); 
						 }
						
					}
						
					}else {
						 System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 
				 }
		         
			  }
			
//			System.out.println(requestMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
