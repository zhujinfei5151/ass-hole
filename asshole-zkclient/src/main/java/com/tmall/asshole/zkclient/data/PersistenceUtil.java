package com.tmall.asshole.zkclient.data;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author tangjinou (jiuxian.tjo)
 *
 */
public class PersistenceUtil {
	  private static transient Log log = LogFactory.getLog(PersistenceUtil.class);
	
	  public static byte[]  serializable(NodeData data) throws IOException{
		  
		  ByteArrayOutputStream bout = new ByteArrayOutputStream(); 
		  
		  ObjectOutputStream out=new ObjectOutputStream(bout);
		  
		  out.writeObject(data);
		  out.flush();
		  byte[] bytes = bout.toByteArray();  
		  
		  bout.close();
		  
		  out.close();

		  return bytes;
	   }
	  
	  public static NodeData deSerializable(byte[] bytes) {
		   if(bytes.length==0){
			   return null;
		   }
		   try {
		  
		   ByteArrayInputStream bi = new ByteArrayInputStream(bytes);   
		   ObjectInputStream oi=  new ObjectInputStream(bi);
		   Object obj = oi.readObject();   
		   bi.close();   
		   oi.close();   
		   
		   return obj instanceof NodeData ?(NodeData)obj:null;   
		   
		} catch (Exception e) {
			log.error(e.getStackTrace());
			return null;
		}   
	  }

}
