public class ImgInfo {
    String urlString;
    String md5String;
    String dirName;
    int count;
    public String getUrlString() {
        return urlString;
    }
    public void setUrlString(String urlString) {
        this.urlString = urlString;
    }
    public String getMd5String() {
        return md5String;
    }
    public void setMd5String(String md5String) {
        this.md5String = md5String;
    }
    public String getDirName() {
        return dirName;
    }
    public void setDirName(String dirName) {
        this.dirName = dirName;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

    public ImgInfo(String urlString,String md5String,String dirName,int count) {
        this.urlString = urlString;
        this.md5String = md5String;
        this.dirName = dirName;
        this.count = count;
    }
}