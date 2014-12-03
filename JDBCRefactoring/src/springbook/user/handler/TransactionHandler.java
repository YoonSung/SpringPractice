package springbook.user.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TransactionHandler implements InvocationHandler {

	//부가기능을 제공할 타깃 오브젝트. 어떤 타입의 오브젝트에도 적용 가능하도록 Object로 선언했다.
	private Object target;
	
	// 트랜잭션 기능을 제공하는 데 필요한 트랜잭션 매니저
	private PlatformTransactionManager transactionManager;
	
	//트랜잭션을 적용할 메소드 이름 패턴
	private String pattern;
	
	public void setTarget(Object target) {
		this.target = target;
	}
	
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		// 트랜잭션 적용 대상 메소드를 선별해서 트랜잭션 경계설정 기능을 부여해준다.
		if (method.getName().startsWith(pattern)) {
			return invokeInTransaction(method, args);
		} else {
			return method.invoke(target, args);
		}
	}

	private Object invokeInTransaction(Method method, Object[] args) throws Throwable {
		TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
		
		
		try {
			Object	ret = method.invoke(target, args);
			this.transactionManager.commit(status);
			return ret;
		
		// RuntimeException이 아닌 InvocationTargetException을 잡도록 해야 한다.
		// Method.invoke()를 이용해 타깃 오브젝트의 메소드를 호출할 때는 타깃 오브젝트에서 발생하는 예외가
		// InvocationTargetException으로 한번 포장돼서 전달되기 때문이다. 그래서 다시한번 Throw하는 식으로 구현되어 있다.
		} catch (InvocationTargetException e) {
			this.transactionManager.rollback(status);throw e.getTargetException();
		}
	}

}
