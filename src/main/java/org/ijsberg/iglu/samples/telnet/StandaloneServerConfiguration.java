/*
 * Copyright 2011 Jeroen Meetsma
 *
 *
 * This file is part of Iglu.
 *
 * Iglu is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Iglu is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Iglu.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.ijsberg.iglu.samples.telnet;

import org.ijsberg.iglu.Cluster;
import org.ijsberg.iglu.Component;
import org.ijsberg.iglu.Configuration;
import org.ijsberg.iglu.configuration.StandardCluster;
import org.ijsberg.iglu.configuration.StandardComponent;
import org.ijsberg.iglu.invocation.RootConsole;
import org.ijsberg.iglu.runtime.module.ServerEnvironment;
import org.ijsberg.iglu.server.connection.ConnectionFactory;
import org.ijsberg.iglu.server.connection.invocation.ObjectInvocationConnectionFactory;
import org.ijsberg.iglu.server.connection.socket.SocketServer;
import org.ijsberg.iglu.server.connection.socket.module.StandardSocketServer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Sample class that embeds a Telnet Server in a standalone runtime environment.
 * The environment can be controlled using shell scripts.
 */
public class StandaloneServerConfiguration implements Configuration {

	private Map clusterMap = new HashMap();

	private static final Properties properties = new Properties();
	{
		properties.setProperty("port", "17623");
		properties.setProperty("keep_alive", "false");
		properties.setProperty("so_timeout", "0");//ms
		properties.setProperty("timeout", "900");//s
		properties.setProperty("so_linger_active", "false");
		properties.setProperty("so_linger_time", "10");//s
	}

	/**
	 *
	 */
	public StandaloneServerConfiguration() {

		//Cluster connects related modules and takes care of dependency injection
		Cluster core = new StandardCluster();
		clusterMap.put("core", core);

		//ServerEnvironment takes care of starting and stopping runtime environment and individual modules
		ServerEnvironment serverEnvironment = new ServerEnvironment();
		Component serverEnvironmentModule = new StandardComponent(serverEnvironment);
		core.connect("ServerEnvironment", serverEnvironmentModule);

		//RootConsole makes modules in clusters callable from command shell (by invoke.sh or invoke.bat)
		RootConsole rootConsole = new RootConsole(this);
		Component rootConsoleModule = new StandardComponent(rootConsole);
		core.connect("RootConsole", rootConsoleModule);

		//methods of SampleInvokableObject can be invoked command-line
		//TODO replace by your own implementation
		SampleInvokableObject invokable = new SampleInvokableObject();

		//ConnectionFactory creates connections for SocketServer
		ConnectionFactory connectionFactory = new ObjectInvocationConnectionFactory(invokable);
		Component connectionFactoryModule = new StandardComponent(connectionFactory);
		core.connect("ConnectionFactory", connectionFactoryModule);

		//SocketServer listens to TCP-port and spawns connections
		SocketServer socketServer = new StandardSocketServer();
		Component socketServerModule = new StandardComponent(socketServer);
		socketServerModule.setProperties(properties);
		core.connect("SocketServer", socketServerModule);

		//start configured assembly
		serverEnvironment.start();
	}

	@Override
	public Map<String, Cluster> getClusters() {
		return clusterMap;
	}

	/**
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		new StandaloneServerConfiguration();
	}

}
