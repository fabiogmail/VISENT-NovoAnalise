package br.com.visent.analise.props;

import java.util.ResourceBundle;

import javax.enterprise.inject.Produces;

public class PropertiesProvider {

	private ResourceBundle bundle;
	
	@Produces @SystemProperties
    public ResourceBundle getBundle() {
       if (bundle == null) {
           bundle = ResourceBundle.getBundle("system");
       }
       return bundle;
   }
	
}
