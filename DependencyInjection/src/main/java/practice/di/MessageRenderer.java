package practice.di;

public interface MessageRenderer {
	void render();
	void setMessageProvider(MessageProvider messageProvider);
	String getRenderMessage();
}
