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

import org.ijsberg.iglu.samples.telnet.authentication.SampleAuthenticator;
import org.ijsberg.iglu.samples.telnet.invocation.ProtectedObjectInvocationConnectionFactory;
import org.ijsberg.iglu.server.connection.Connection;
import org.ijsberg.iglu.server.connection.ConnectionFactory;
import org.ijsberg.iglu.server.connection.invocation.ObjectInvocationConnectionFactory;
import org.ijsberg.iglu.server.connection.socket.module.StandardSocketServer;

import java.util.Properties;

/**
 * Starts a telnet server on port 17623.
 * The server gives telnet clients direct access to public methods of a test object.
 */
public class EmbeddedServer {

	private static final Properties properties = new Properties();
	{
		properties.setProperty("port", "17623");
		properties.setProperty("keep_alive", "false");
		properties.setProperty("so_timeout", "0");//ms
		properties.setProperty("timeout", "900");//s
		properties.setProperty("so_linger_active", "false");
		properties.setProperty("so_linger_time", "10");//s
	}


	public void start() {

		StandardSocketServer socketServer = new StandardSocketServer();
		//setProperties overwrites default properties
		socketServer.setProperties(properties);
		//TODO provide your own invokable here ...
		ConnectionFactory connectionFactory = new ObjectInvocationConnectionFactory(new SampleInvokableObject());
		//TODO uncomment next line to try password protected sessions (users to found in resources/users.properties)
		//ConnectionFactory connectionFactory = new ProtectedObjectInvocationConnectionFactory
		//		(new SampleInvokableObject(), new SampleAuthenticator());
		socketServer.setConnectionFactory(connectionFactory);
		socketServer.start();
	}

	public static void main(String[] args) {
		EmbeddedServer server = new EmbeddedServer();
		server.start();
	}
}
