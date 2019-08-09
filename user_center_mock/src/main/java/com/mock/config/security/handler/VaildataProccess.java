package com.mock.config.security.handler;

public abstract class VaildataProccess implements VaildataProccessHandler {
	
	
	@Override
	public String creatCode() {
		String code= generateCode();
		send(code);
		return code;
	}

	@Override
	public boolean vaildata(String code) {
		return checkCode(code);
	}
	
	protected abstract String generateCode();
	
	protected abstract void send(String code);
	
	protected abstract boolean checkCode(String code);

}
