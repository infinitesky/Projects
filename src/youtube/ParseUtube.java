package youtube;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseUtube {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ArrayList<String> urlList = new ArrayList<String>();
		ArrayList<JSONObject> addObject = new ArrayList<JSONObject>();
		
		BufferedReader br = new BufferedReader(new FileReader("src/ParseLinksExample.txt"));
		String line;
		while ((line = br.readLine()) != null) {

			urlList.add(line);
			
		}

		for (String url : urlList)
		{

			
			Document doc = Jsoup.connect(url).get();
			
			System.out.println(doc.title());
			System.out.println("---------------------------------------");

			
			for (int i = 0; i<25; i++)
			{
				
				JSONObject obj = new JSONObject();
				Element title1 = doc.select("h3.yt-lockup-title > a[title]").get(i);
				obj.put("title", title1.attr("title"));
				obj.put("URL", "https://www.youtube.com" + title1.attr("href"));
				
				
				
				System.out.println(title1.attr("title"));
				System.out.println("https://www.youtube.com" + title1.attr("href"));

				
				Element views = doc.select("ul.yt-lockup-meta-info").get(i);

				obj.put("Views", views.html());

				
				System.out.println(views.html());
				addObject.add(obj);
				
			}
			
		}
		
		WriteDataToJSONFile(addObject);
		
		
	}
	
	public static void WriteDataToJSONFile(ArrayList<JSONObject> data) throws IOException
	{
		//JSONObject obj =  data;
            
        FileWriter file = new FileWriter("src/ParsedData.txt");
        try {
        	for (JSONObject obj: data)
        	{
            file.write(obj.toJSONString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + obj.toJSONString());
        	}
 
        } catch (IOException e) {
            e.printStackTrace();
 
        } finally {
            file.flush();
            file.close();
        }
	}

}
