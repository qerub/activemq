package org.apache.activemq;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.jms.JMSException;

import org.apache.activemq.broker.BrokerService;
import org.junit.Test;

public class ConnectionParameterValidationTest {
	@Test
	public void testClientParameters() throws Exception {
		try {
			new ActiveMQConnectionFactory("tcp://localhost:42?foo=bar").createConnection();
			fail("Should have thrown an exception");
		}
		catch (Exception e) {
			assertEquals(JMSException.class, e.getClass());
			assertEquals(IllegalArgumentException.class, e.getCause().getClass());
			assertEquals("Invalid connect parameters: {foo=bar}", e.getCause().getMessage());
		}
	}
	
	@Test
	public void testClientSocketParameters() throws Exception {
		BrokerService broker = null;
		
		try {
			broker = new BrokerService();
			broker.setPersistent(false);
			broker.addConnector("tcp://localhost:61616");
			broker.start();
			
			try {
				new ActiveMQConnectionFactory("tcp://localhost:61616?socket.foo=bar").createConnection();
				fail("Should have thrown an exception");
			}
			catch (Exception e) {
				assertEquals(JMSException.class, e.getClass());
				assertEquals(IllegalArgumentException.class, e.getCause().getClass());
				assertEquals("Invalid socket parameters: {foo=bar}", e.getCause().getMessage());
			}
		}
		finally {
			if (broker != null) {
				broker.stop();
			}
		}
	}
	
	@Test
	public void testServerParameters() throws Exception {
		BrokerService broker = null;
		
		try {
			broker = new BrokerService();
			
			try {
				broker.addConnector("tcp://localhost:61616?foo=bar");
				fail("Should have thrown an exception");
			}
			catch (IllegalArgumentException e) {
				assertEquals(IllegalArgumentException.class, e.getClass());
				assertEquals("Invalid connector parameters: {foo=bar}", e.getMessage());
			}
		}
		finally {
			if (broker != null) {
				broker.stop();
			}
		}
	}
	
	@Test
	public void testSSLServerParameters() throws Exception {
		BrokerService broker = null;
		
		try {
			broker = new BrokerService();
			
			try {
				broker.addConnector("ssl://localhost:61616?foo=bar");
				fail("Should have thrown an exception");
			}
			catch (IllegalArgumentException e) {
				assertEquals(IllegalArgumentException.class, e.getClass());
				assertEquals("Invalid connector parameters: {foo=bar}", e.getMessage());
			}
		}
		finally {
			if (broker != null) {
				broker.stop();
			}
		}
	}
}
