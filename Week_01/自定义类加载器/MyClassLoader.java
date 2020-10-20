
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MyClassLoader extends ClassLoader {

    private String filePath;
    private byte[] data;

    @Override
    protected Class<?> findClass(String name) {
        if (data == null || data.length == 0) {
            try {
                data = Files.readAllBytes(Paths.get(filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return defineClass(name, data, 0, data.length);
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
