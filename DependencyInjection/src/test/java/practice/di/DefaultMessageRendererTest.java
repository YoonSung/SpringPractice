package practice.di;

import static org.junit.Assert.assertEquals;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/di.xml")
public class DefaultMessageRendererTest {

	@Autowired
	MessageRenderer renderer;
	
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
		 assertEquals("Hello World!", renderer.getRenderMessage());
	}
	
	@Test
	public void renderFromSpringJunitLibrary() {
		assertEquals("Hello World!", renderer.getRenderMessage());
	}
}
