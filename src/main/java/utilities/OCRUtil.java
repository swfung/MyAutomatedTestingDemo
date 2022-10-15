package utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import org.apache.commons.lang3.ArrayUtils;
import org.openqa.selenium.Point;

import com.ConstantFile;

public class OCRUtil {

	public static Point getLocation(String target, String origion) {
		String baseDir = System.getProperty("user.dir");
		Point point = null;

		String[] arguments = new String[] { "python",///usr/local/bin/python3
				baseDir + File.separator + "python"+ File.separator+"GetLocation.py", baseDir + File.separator + "picture"+ File.separator+target, origion };
//		for(String arg:arguments) {
//			System.out.println(arg);
//		}
//		System.out.println();
		String result = "";

		try {
			Process process = Runtime.getRuntime().exec(arguments);
			BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			String line = null;

			while ((line = in.readLine()) != null) {
				result += line;
			}
			
			while ((line = stdError.readLine()) != null) {
				result = result + line + System.getProperty("line.separator");
			}
			
			System.out.println(result);

			in.close();
			// java代码中的process.waitFor()返回值为0表示我们调用python脚本成功，
			// 返回值为1表示调用python脚本失败，这和我们通常意义上见到的0与1定义正好相反
			int exitCode = process.waitFor();
			System.out.println(exitCode);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if ("".equals(result)) {
			return point;
		}
		
		String[] array = result.split(",");
//		int x = (int) (Integer.parseInt(array[0].substring(1)) * 1.15);
//		int y = (int) (Integer.parseInt(array[1].trim().substring(0,array[1].trim().length()-1))*1.15);

		int x = Integer.parseInt(array[0].substring(1));
		int y = Integer.parseInt(array[1].trim().substring(0,array[1].trim().length()-1));
		
		
		point = new Point(x,y);
		
		return point;
	}

	public static void main(String[] args) throws Exception {
		Point p = getLocation("TCHI1.png",//"TChi"
				//"/Users/aiait/Desktop/WechatIMG1.jpeg"
				ConstantFile.BASE_DIR +File.separator+"output"+File.separator+"screenshots"+File.separator+"Language Option.png"
				);
		System.out.println(p.x + "," + p.y);
		
	//	execShell("shell/getLocation.sh","python/getLocation.py","/Users/aiait/eclipse-workspace_as/AIA_Connect/picture/SChi.png","/Users/aiait/Desktop/WechatIMG1.jpeg");
	
	//	execShell("/Users/aiait/Desktop/shellScript/startappium1.sh");
		
	//	execShell("shell/test2.sh");
	}
	
	private static String execShell(String scriptPath,String ... para) throws Exception {
		String[] cmd = new String[] {scriptPath};
		cmd = ArrayUtils.addAll(cmd, para);
		System.out.println(cmd);
		
		//handle error: Permission denied 
		ProcessBuilder builder = new ProcessBuilder("/bin/chmod","755",scriptPath);
		Process process = builder.start();
		process.waitFor();
		
		Process ps = Runtime.getRuntime().exec(cmd);
		BufferedReader in = new BufferedReader(new InputStreamReader(ps.getInputStream(), "GBK"));
		BufferedReader stdError = new BufferedReader(new InputStreamReader(ps.getErrorStream()));
		
		String line = null;
		String result = "";
		while ((line = in.readLine()) != null) {
			// System.out.println(line);
			result += line;
		}
		
		while ((line = stdError.readLine()) != null) {
			// System.out.println(line);
			result = result + line + System.getProperty("line.separator");
		}
		
		System.out.println(result);

		in.close();
		// java代码中的process.waitFor()返回值为0表示我们调用python脚本成功，
		// 返回值为1表示调用python脚本失败，这和我们通常意义上见到的0与1定义正好相反
		int exitCode = ps.waitFor();
		System.out.println(exitCode);
		
		return result;
	}
}
