package br.com.zoot.api.exception;

public class StandardErro {
	
	private String msgUsuario;
	private String msgDesenvolvedor;
	
	public StandardErro(String msgU, String msgD) {
		this.msgUsuario = msgU;
		this.msgDesenvolvedor = msgD;
	}

	public String getMsgUsuario() {
		return msgUsuario;
	}

	public void setMsgUsuario(String msgUsuario) {
		this.msgUsuario = msgUsuario;
	}

	public String getMsgDesenvolvedor() {
		return msgDesenvolvedor;
	}

	public void setMsgDesenvolvedor(String msgDesenvolvedor) {
		this.msgDesenvolvedor = msgDesenvolvedor;
	}
}
