package mx.nic.rdap.test.json;

import org.junit.BeforeClass;
import org.junit.Test;

import mx.nic.rdap.renderer.Renderer;
import mx.nic.rdap.renderer.json.JsonRenderer;
import mx.nic.rdap.renderer.test.AutnumTestRenderer;
import mx.nic.rdap.renderer.test.DomainSearchTestRenderer;
import mx.nic.rdap.renderer.test.DomainTestRenderer;
import mx.nic.rdap.renderer.test.EntitySearchTestRenderer;
import mx.nic.rdap.renderer.test.EntityTestRenderer;
import mx.nic.rdap.renderer.test.ExceptionTestRenderer;
import mx.nic.rdap.renderer.test.HelpTestRenderer;
import mx.nic.rdap.renderer.test.IpNetworkTestRenderer;
import mx.nic.rdap.renderer.test.NameserverSearchTestRenderer;
import mx.nic.rdap.renderer.test.NameserverTestRenderer;

public class TestJsonRenderer {

	private static Renderer renderer;

	@BeforeClass
	public static void init() {
		renderer = new JsonRenderer();
	}

	@Test
	public void testEntity() {
		EntityTestRenderer test = new EntityTestRenderer();
		test.testRenderer(renderer);
	}
	
	@Test
	public void testAutnum() {
		AutnumTestRenderer test = new AutnumTestRenderer();
		test.testRenderer(renderer);
	}

	@Test
	public void testIpNetwork() {
		IpNetworkTestRenderer test = new IpNetworkTestRenderer();
		test.testRenderer(renderer);
	}

	@Test
	public void testNameserver() {
		NameserverTestRenderer test = new NameserverTestRenderer();
		test.testRenderer(renderer);
	}

	@Test
	public void testDomain() {
		DomainTestRenderer test = new DomainTestRenderer();
		test.testRenderer(renderer);
	}

	@Test
	public void testSearchEntity() {
		EntitySearchTestRenderer test = new EntitySearchTestRenderer();
		test.testRenderer(renderer);
	}

	@Test
	public void testSearchDomain() {
		DomainSearchTestRenderer test = new DomainSearchTestRenderer();
		test.testRenderer(renderer);
	}

	@Test
	public void testSearchNS() {
		NameserverSearchTestRenderer test = new NameserverSearchTestRenderer();
		test.testRenderer(renderer);
	}

	@Test
	public void testException() {
		ExceptionTestRenderer test = new ExceptionTestRenderer();
		test.testRenderer(renderer);
	}

	@Test
	public void testHelp() {
		HelpTestRenderer test = new HelpTestRenderer();
		test.testRenderer(renderer);
	}

}
