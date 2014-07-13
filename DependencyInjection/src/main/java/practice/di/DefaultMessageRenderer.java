package practice.di;

public class DefaultMessageRenderer implements MessageRenderer {
	
	MessageProvider messageProvider;
	
	public void setMessageProvider(MessageProvider messageProvider) {
		this.messageProvider = messageProvider;
	}
	
	public void render() {
		System.out.println(this.messageProvider.getMessage());
	}

	public String getRenderMessage() {
		return messageProvider.getMessage();
	}
}
