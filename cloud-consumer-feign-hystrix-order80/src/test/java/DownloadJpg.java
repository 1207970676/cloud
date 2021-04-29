import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

//下载图片
//测试
public class DownloadJpg {
    public static void main(String args[]) throws Exception{
        LinkedBlockingQueue <ImgInfo> queue = new LinkedBlockingQueue<ImgInfo>();
        download(queue);
    }

    public static void download(LinkedBlockingQueue <ImgInfo> queue) throws Exception {
        String dirPath = "D:\\Git-Space\\nsfw_data_scraper\\raw_data\\porn";
        File f = new File(dirPath);
        File[] fs = f.listFiles();
        int count=0;
        for(File file:fs) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            int line=0;
            String str;
            while((str=reader.readLine())!=null) {
                line++;
                if(line==1)continue;
                String[] info = str.split("    ");
                String urlString = info[0];//str.split("    ")[0];
                String md5String = info[2];
                String dirName=file.getName();
                count++;
                ImgInfo imageInfo = new ImgInfo(urlString,md5String,dirName,count);
                queue.put(imageInfo);
            }
            reader.close();
        }

        RunnableImg r1 = new RunnableImg(queue,"r1");
        RunnableImg r2 = new RunnableImg(queue,"r2");
        RunnableImg r3 = new RunnableImg(queue,"r3");
        RunnableImg r4 = new RunnableImg(queue,"r4");
        r1.start();
        r2.start();
        r3.start();
        r4.start();
    }
}