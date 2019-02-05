package com.plugin.paging;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orientechnologies.orient.core.db.ODatabaseDocumentInternal;
import com.orientechnologies.orient.core.db.record.OIdentifiable;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.orientechnologies.orient.server.config.OServerCommandConfiguration;
import com.orientechnologies.orient.server.network.protocol.http.OHttpRequest;
import com.orientechnologies.orient.server.network.protocol.http.OHttpResponse;
import com.orientechnologies.orient.server.network.protocol.http.command.OServerCommandAuthenticatedDbAbstract;

public class PagingStudent extends OServerCommandAuthenticatedDbAbstract {
	// DECLARE THE PARAMETERS
	ObjectMapper objectMapper = new ObjectMapper();


	public PagingStudent(final OServerCommandConfiguration iConfiguration) {
		super();
	}

	public boolean execute(final OHttpRequest iRequest, OHttpResponse iResponse) throws Exception {
		String[] urlParts = checkSyntax(iRequest.url, 4, "Syntax error: getStudentList/<database>/<orderBy>/<pageNo>");
		String orderBy = urlParts[2];
		int pageNo = Integer.parseInt(urlParts[3]);
		int limit = 5;
		int from=pageNo*limit;
		 iResponse.writeRecords(getStudentList(iRequest,from,limit,orderBy));
		return false;
	}

	public String[] getNames() {
		return new String[] { "GET|getStudentList/*" };
	}
	
	
	public List<OIdentifiable> getStudentList(OHttpRequest iRequest,int from,int limit, String orderBy) {
		List<OIdentifiable> response = null;
		String text="SELECT FROM Student ORDER BY "+orderBy+" ASC SKIP "+from+" LIMIT "+limit;
		try {
			   ODatabaseDocumentInternal dbInstace = getProfiledDatabaseInstance(iRequest);
		      response = (List<OIdentifiable>) dbInstace.command(new OSQLSynchQuery<OIdentifiable>(text)).execute();
		} catch (Exception e) {
		}
		return response;
	}
}