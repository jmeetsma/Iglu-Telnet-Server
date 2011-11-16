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

import org.ijsberg.iglu.server.connection.ClientSession;
import org.ijsberg.iglu.server.connection.ClientSessionAware;

/**
 */
public class SampleInvokableObject implements ClientSessionAware {
	private String message = "Hi!";
	private ClientSession session;


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void exit() {
		session.sendMessage("Bye");
		session.terminate();
	}

	@Override
	public void setSession(ClientSession session) {
		this.session = session;
	}
}
