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

package org.ijsberg.iglu.samples.telnet.authentication;

import org.ijsberg.iglu.exception.ResourceException;
import org.ijsberg.iglu.util.io.FileSupport;

import java.awt.image.ImagingOpException;
import java.io.IOException;
import java.util.Properties;

/**
 * Authenticates users.
 */
public class SampleAuthenticator implements NamePasswordAuthenticator {

	Properties props = new Properties();

	public SampleAuthenticator() {
		try {
			props.load(FileSupport.getInputStreamFromClassLoader("users.properties"));
		} catch (IOException ioe) {
			throw new ResourceException("can not load user properties", ioe);
		}
	}

	@Override
	public boolean authenticate(String userName, String password) {
		return props.getProperty(userName) != null;
	}
}
