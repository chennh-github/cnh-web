package com.cnh.frame.wraps;


import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;


public class BaiduApiWrap {
	
	public static String convertStreamToString(InputStream is) {      
        /*  
          * To convert the InputStream to String we use the BufferedReader.readLine()  
          * method. We iterate until the BufferedReader return null which means  
          * there's no more data to read. Each line will appended to a StringBuilder  
          * and returned as String.  
          */     
         BufferedReader reader = new BufferedReader(new InputStreamReader(is));      
         StringBuilder sb = new StringBuilder();      
     
         String line = null;      
        try {      
            while ((line = reader.readLine()) != null) {      
                 sb.append(line + "\n");      
             }      
         } catch (IOException e) {      
             e.printStackTrace();      
         } finally {      
            try {      
                 is.close();      
             } catch (IOException e) {      
                 e.printStackTrace();      
             }      
         }      
     
        return sb.toString();      
     }
	
	public static String getLngLatByPlace(String place,String city) {
		HttpClient client = new HttpClient();
		InputStream is = null;
		String url = "http://api.map.baidu.com/geocoder/v2/?output=json";
		try {
			if(place!=null&&!place.trim().equals("")){
				url+="&address="+URLEncoder.encode(place,"utf-8");
			}
			if(city!=null&&!city.trim().equals("")){
				url+="&city="+URLEncoder.encode(city,"utf-8");
			}
			url+="&ak=A8ecebe510613488c3e4f98db0190274";
			GetMethod method = new GetMethod(url);
			client.executeMethod(method);
			is = method.getResponseBodyAsStream();
			String json = convertStreamToString(is);
			JSONObject jsonStr=JSONObject.fromObject(json);
			if(jsonStr.getInt("status")==0){
				return jsonStr.getJSONObject("result").getJSONObject("location").getDouble("lng")+","+
				jsonStr.getJSONObject("result").getJSONObject("location").getDouble("lat");
			}else{
				
				return "0.0,0.0";
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					//e.printStackTrace();
				}
			}
		}
		return null;
	}
	public static Object getPlaceByLngLat(String lat,String lng,String coordtype) {
		HttpClient client = new HttpClient();
		InputStream is = null;
		String url = "http://api.map.baidu.com/geocoder/v2/?output=json";
		try {
			if(coordtype!=null&&!coordtype.trim().equals("")){
				url+="&coordtype="+coordtype;
			}
			if(lng!=null&&!lng.trim().equals("")&&lat!=null&&!lat.trim().equals("")){
				url+="&location="+lat+","+lng;
			}
			url+="&ak=A8ecebe510613488c3e4f98db0190274";
			GetMethod method = new GetMethod(url);
			client.executeMethod(method);
			is = method.getResponseBodyAsStream();
			String json = convertStreamToString(is); 
			JSONObject jsonStr=JSONObject.fromObject(json);
			if(jsonStr.getInt("status")==0){
				return jsonStr.getJSONObject("result").getJSONObject("location").getDouble("lng");
			}
			return JSONObject.fromObject(json).get("result");
		} catch (Exception e) {
			//e.printStackTrace();
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
//				/	e.printStackTrace();
				}
			}
		}
		return null;
	}
	public static void main(String[] args) {
		System.err.println(getLngLatByPlace("东街口", null));
//		System.err.println(getPlaceByLngLat("24.468728076403", "118.13453488213", ""));
	}
}
