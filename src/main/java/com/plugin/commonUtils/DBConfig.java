package com.plugin.commonUtils;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;

public class DBConfig {
	public static final String databaseUrl = "plocal:D:/CS/orientdb-community-2.2.22/databases/TestPluginDB";
	public static final String userName = "root";
	public static final String password = "pass@123";
	static OrientGraph graph = new OrientGraphFactory(DBConfig.databaseUrl).setupPool(1,10).getTx();
	public static OrientGraph getGraphInstance() {
		if(graph==null) {
		  return new OrientGraphFactory(DBConfig.databaseUrl).setupPool(1,10).getTx();
		}
		return graph;
    }
}
