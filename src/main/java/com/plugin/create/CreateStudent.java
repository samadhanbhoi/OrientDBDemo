package com.plugin.create;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orientechnologies.orient.server.config.OServerCommandConfiguration;
import com.orientechnologies.orient.server.network.protocol.http.OHttpRequest;
import com.orientechnologies.orient.server.network.protocol.http.OHttpResponse;
import com.orientechnologies.orient.server.network.protocol.http.OHttpUtils;
import com.orientechnologies.orient.server.network.protocol.http.command.OServerCommandAuthenticatedDbAbstract;
import com.plugin.commonUtils.CommonUtils;

@SuppressWarnings("unchecked")
public class CreateStudent extends OServerCommandAuthenticatedDbAbstract {
  
  ObjectMapper objectMapper = new ObjectMapper();
  
  public CreateStudent(final OServerCommandConfiguration iConfiguration)
  {
    super();
  }
  
  public boolean execute(final OHttpRequest iRequest, OHttpResponse iResponse) throws Exception
  {
    Map<String, Object> requestMap = objectMapper.readValue(iRequest.content.toString(),HashMap.class);
    String result = CommonUtils.createVertexByType(requestMap, "Student").getId().toString();
    iResponse.send(OHttpUtils.STATUS_OK_CODE, "OK", OHttpUtils.CONTENT_TEXT_PLAIN, result, null);
    return false;
  }
  
  public String[] getNames()
  {
    return new String[] { "POST|createStudent/*" };
  }
  
}