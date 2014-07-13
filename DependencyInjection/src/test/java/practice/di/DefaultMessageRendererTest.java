package practice.di;

import static org.junit.Assert.*;

import org.junit.Test;

public class DefaultMessageRendererTest {

	@Test
	public void render() {
		MessageRenderer messageRenderer = new DefaultMessageRenderer(new HelloWorldMessageProvider());
		//messageRenderer.render();
		assertEquals("Hello World!", messageRenderer.getRenderMessage());
	}
}
