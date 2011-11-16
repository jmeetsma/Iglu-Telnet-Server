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

package org.ijsberg.iglu.samples.telnet.invocation;

import org.ijsberg.iglu.samples.telnet.authentication.NamePasswordAuthenticator;
import org.ijsberg.iglu.server.connection.CommandLineClientAdapter;
import org.ijsberg.iglu.server.connection.invocation.CommandLineObjectInvoker;

/**
 */
public class ProtectedCLObjectInvoker extends CommandLineObjectInvoker{

	private CommandLineClientAdapter adapter;
	private NamePasswordAuthenticator authenticator;
	private String userName;
	private boolean isLoggedIn;

	/**
	 * @param invokable
	 */
	public ProtectedCLObjectInvoker(Object invokable, NamePasswordAuthenticator authenticator) {
		super(invokable);
		this.authenticator = authenticator;
	}

	/**
	 *
	 * @param adapter
	 */
	@Override
	public void initiateSession(CommandLineClientAdapter adapter) {

		this.adapter = adapter;
		adapter.send("user name: ");
	}

	/**
	 *
	 * @param commandLine input from the client connection
	 * @return
	 */
	@Override
	public String processCommandLine(String commandLine) {
		if(userName == null) {
			userName = commandLine;
			adapter.disableEcho();
			return "password: ";
		} else if (!isLoggedIn) {
			if(authenticator.authenticate(userName, commandLine)) {
				isLoggedIn = true;
				adapter.enableEcho();
				super.initiateSession(adapter);
				return "";
			} else {
				adapter.send("authentication failed ...");
				adapter.terminateSession();
			}
		}
		return super.processCommandLine(commandLine);
	}

}
