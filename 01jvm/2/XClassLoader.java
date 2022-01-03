import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Base64;

public class XClassLoader extends ClassLoader {
    public static void main(String[] args){
        try {
            Class clazz = new XClassLoader().loadClass("Hello");
            Object instance = clazz.newInstance();
            Method method = clazz.getDeclaredMethod("hello", null);
            method.invoke(instance);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected Class<?> findClass(String name) throws ClassNotFoundException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("Hello.xlass");
        try {
            int length = inputStream.available();
            byte[] classBytes = new byte[length];
            inputStream.read(classBytes);
            for(int i =0; i<classBytes.length;i++){
                classBytes[i] = (byte) (255 - classBytes[i]);
            }
            return defineClass(name, classBytes, 0, classBytes.length);
        } catch (Exception e1) {
            throw new RuntimeException("load class failed!");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}