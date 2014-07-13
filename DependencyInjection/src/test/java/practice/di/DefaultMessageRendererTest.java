package practice.di;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DefaultMessageRendererTest {

	@Test
	public void render() {
		//MessageRenderer messageRenderer = new DefaultMessageRenderer(new HelloWorldMessageProvider());
		MessageRenderer messageRenderer = new DefaultMessageRenderer();
		messageRenderer.setMessageProvider(new HelloWorldMessageProvider());
		
		//messageRenderer.render();
		assertEquals("Hello World!", messageRenderer.getRenderMessage());
	}
	
	@Test
	public void renderFromXML() {
		 ApplicationContext ac = new ClassPathXmlApplicationContext("di.xml");
		 MessageRenderer renderer = (MessageRenderer) ac.getBean("messageRenderer");
		 //assertEquals("Hello World!", renderer.getRenderMessage());
	}
}
