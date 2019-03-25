package selenium.js;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;
public class ExecuteScript {
    public static void main(String[] args) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        try {
            // 获取targe路径
            String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
            System.out.println(path);
            // FileReader的参数为所要执行的js文件的路径
            engine.eval(new FileReader(path+ "js/JavaScript1.js"));
            engine.eval(new FileReader(path+ "js/JavaScript2.js"));
            if (engine instanceof Invocable) {
                Invocable invocable = (Invocable) engine;
                JavaScriptInterface executeMethod = invocable.getInterface(JavaScriptInterface.class);
                System.out.println(executeMethod.execute("str1", "str2"));
                System.out.println(executeMethod.validate("str1", "str2"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
