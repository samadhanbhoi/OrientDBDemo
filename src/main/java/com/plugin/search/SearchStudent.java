package com.plugin.search;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orientechnologies.orient.core.db.ODatabaseDocumentInternal;
import com.orientechnologies.orient.core.db.record.OIdentifiable;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.orientechnologies.orient.server.config.OServerCommandConfiguration;
import com.orientechnologies.orient.server.network.protocol.http.OHttpRequest;
import com.orientechnologies.orient.server.network.protocol.http.OHttpResponse;
import com.orientechnologies.orient.server.network.protocol.http.command.OServerCommandAuthenticatedDbAbstract;

public class SearchStudent extends OServerCommandAuthenticatedDbAbstract {
	ObjectMapper objectMapper = new ObjectMapper();
	public SearchStudent(final OServerCommandConfiguration iConfiguration) {
		super();
	}

	public boolean execute(final OHttpRequest iRequest, OHttpResponse iResponse) throws Exception {
		String[] urlParts = checkSyntax(iRequest.url, 3, "Syntax error: hello/<database>/<name>");
		String sId = urlParts[2];
		System.out.println("Student Id " + sId);
		 iResponse.writeRecords(getStudentById(iRequest,sId));
		return false;
	}

	public String[] getNames() {
		return new String[] { "GET|getStudent/*" };
	}
	
	public List<OIdentifiable> getStudentById(OHttpRequest iRequest,String sId) {
		List<OIdentifiable> response = null;
		String text="SELECT FROM Student WHERE sId=" + sId;
		try {
			   ODatabaseDocumentInternal dbInstace = getProfiledDatabaseInstance(iRequest);
		      response = (List<OIdentifiable>) dbInstace.command(new OSQLSynchQuery<OIdentifiable>(text)).execute();
		} catch (Exception e) {
		}
		return response;
	}
}