/*

   Derby - Class nserverdemo.NetworkServerUtil

   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */

package com.carl.conf;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;

import org.apache.derby.drda.NetworkServerControl; //derby network server

/**
 * Class for starting the Derby NetworkServer on a separate Thread. This class
 * provides methods to start, and shutdown the server
 * 启动derby数据库的网络服务
 */

public class NetworkServerUtil {

	private int portNum;
	private NetworkServerControl serverControl;
	private PrintWriter pw;

	public NetworkServerUtil(int port, PrintWriter pw) {

		this.portNum = port;
		this.pw = pw;
		try {
			serverControl = new NetworkServerControl(InetAddress.getByName("localhost"), portNum);
			pw.println("Derby Network Server created");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * trace utility of server
	 */
	public void trace(boolean onoff) {
		try {
			serverControl.trace(onoff);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Try to test for a connection Throws exception if unable to get a
	 * connection
	 */
	public void testForConnection() throws Exception {
		serverControl.ping();
	}

	/**
	 * Shutdown the NetworkServer
	 */
	public void shutdown() {
		try {
			serverControl.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Start Derby Network server
	 * 
	 */
	public void start() {
		try {
			serverControl.start(pw);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 阻塞，等待回车结束程序
	 * @throws Exception
	 */
	private static void waitForExit() throws Exception {
		System.out.println("Clients can continue to connect: ");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Press [Enter] to stop Server");
		in.readLine();
	}

	public static void main(String[] args) {
		PrintWriter pw = new PrintWriter(System.out, true);
		NetworkServerUtil util = new NetworkServerUtil(1527, pw);
		util.start();
		try {
			//不开启跟踪
			util.trace(false);
			util.testForConnection();
			//waitForExit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//util.shutdown();
	}

}
