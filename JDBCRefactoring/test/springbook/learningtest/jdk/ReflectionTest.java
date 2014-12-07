package springbook.learningtest.jdk;

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.Test;
import org.springframework.aop.framework.ProxyFactoryBean;

import static org.hamcrest.CoreMatchers.*;

public class ReflectionTest {
	@Test
	public void invokeMethod() throws Exception {
		String name = "Spring";
		
		// length()
		assertThat(name.length(), is(6));
		
		Method lengthMethod = String.class.getMethod("length");
		assertThat((Integer)lengthMethod.invoke(name), is(6));
		
		
		// charAt()
		assertThat(name.charAt(0), is('S'));
		
		Method charAtMethod = String.class.getMethod("charAt", int.class);
		assertThat((Character)charAtMethod.invoke(name, 0), is('S'));
	}
	
	@Test
	public void simpleProxy() throws Exception {
		Hello hello = new HelloTarget();
		assertThat(hello.sayHello("Yoon"), is("Hello Yoon"));
		assertThat(hello.sayHi("Yoon"), is("Hi Yoon"));
		assertThat(hello.sayThankYou("Yoon"), is("Thank You Yoon"));
	}
	
	@Test
	public void invokeHandlerProxy() throws Exception {
		Hello proxieHello = (Hello)Proxy.newProxyInstance(
				getClass().getClassLoader(), 
				new Class[]{Hello.class}, 
				new UppercaseHandler(new HelloTarget()));
		
		assertThat(proxieHello.sayHello("Yoon"), is("HELLO YOON"));
		assertThat(proxieHello.sayHi("Yoon"), is("HI YOON"));
		assertThat(proxieHello.sayThankYou("Yoon"), is("THANK YOU YOON"));
	}
	
	@Test
	public void proxyFactoryBean() throws Exception {
		ProxyFactoryBean pfBean = new ProxyFactoryBean();
		pfBean.setTarget(new HelloTarget());
		pfBean.addAdvice(new UppercaseAdvice());
		
		Hello proxiedHello = (Hello) pfBean.getObject();
		assertThat(proxiedHello.sayHello("Yoon"), is("HELLO YOON"));
		assertThat(proxiedHello.sayHi("Yoon"), is("HI YOON"));
		assertThat(proxiedHello.sayThankYou("Yoon"), is("THANK YOU YOON"));
	}
}
