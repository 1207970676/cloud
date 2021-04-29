import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;

public class RunnableImg extends Thread{
    private Thread t;
    private String threadName;
    LinkedBlockingQueue <ImgInfo> queue;
    RunnableImg(LinkedBlockingQueue <ImgInfo> queue,String name) {
        this.queue=queue;
        threadName=name;
        System.out.println("Creating "+threadName);
    }
    @Override
    public void run() {
        // TODO Auto-generated method stub
        Date d = new Date();
        System.out.println("Running "+threadName);
        while(true) {
            try {
                ImgInfo currentimg = queue.take();
                System.out.println(threadName+" "+currentimg.urlString+" is downloading");

                URL url = new URL(currentimg.urlString);
                HttpURLConnection  con = (HttpURLConnection) url.openConnection();
                InputStream is = con.getInputStream();
                int len;
                String saveImgPath = "D:\\"+currentimg.dirName;
                File sf = new File(saveImgPath);
                if (!sf.exists()) {
                    sf.mkdirs();
                }
                String loadPath= sf.getPath()+"\\"+currentimg.md5String+".jpg";

                File check = new File(loadPath);
                if (!check.exists()) {
                    OutputStream os = new FileOutputStream(loadPath);
                    while((len = is.read())!=-1) {
                        os.write(len);
                    }
                    os.flush();
                    os.close();
                    System.out.println(d.toString()+" "+threadName+" "+currentimg.urlString+" downloaded Success "+currentimg.count);
                }
                is.close();
            } catch (Exception e) {
                System.out.println("Fail "+threadName);
                e.printStackTrace();
            }
        }
    }
}