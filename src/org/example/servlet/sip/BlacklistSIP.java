package org.example.servlet.sip;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.sip.Address;
import javax.servlet.sip.Proxy;
import javax.servlet.sip.SipServlet;
import javax.servlet.sip.SipServletRequest;
import javax.servlet.sip.SipServletResponse;
import javax.servlet.sip.TooManyHopsException;
import javax.servlet.sip.URI;

public class BlacklistSIP extends SipServlet {
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("Blacklist SIP Server Started v2");
		super.init(config);
	}
	
	public void doInvite(SipServletRequest request) {
		// TODO: Add logic
		String blacklistAddr = this.getInitParameter("blacklist_addr");
		// Caller's address object --> String.
		String addrFrom = request.getFrom().getURI().toString();
		URI uri = request.getRequestURI();
		// If the FROM in the Address object is equal to the address specified in the AOR...
		
		// Check address comparison.
		System.out.println("From address is: " + addrFrom);
		System.out.println("Blacklist address is: " + blacklistAddr);
		
		if(addrFrom.equals(blacklistAddr)) {
			// block it.
			try 
			{
				SipServletResponse resp = request.createResponse(411, "In blackist.");
				System.out.println("Blocked -- in blacklist");
				// Blocked it by sending the response back to the container.
				resp.send();
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();	
			}
		}	
		else {
				// Call through proxy.
			try {
				System.out.println("Not blacklisted -- calling.");
				Proxy proxy = request.getProxy();
				proxy.proxyTo(uri);
			} 
			catch (TooManyHopsException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	} // END METHOD
	
	public void doResponse(SipServletResponse resp) {
		System.out.println("Respond -- invoked.");
	}
	
	public void doBye(SipServletRequest request) {
		// TODO: Add logic
	}
}
