package net.prietopalacios.josemanuel.i18n.translator.proxy;

public class JavaProxy {
	
	private String host;
	private int port;
	private String user;
	private String pass;
	
	public JavaProxy(){
		
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	@Override
	public String toString() {
		return "JavaProxy [host=" + host + ", port=" + port + ", user=" + user
				+ ", pass=" + pass + "]";
	}
	
}
