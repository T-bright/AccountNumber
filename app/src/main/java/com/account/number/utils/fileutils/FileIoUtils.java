package com.account.number.utils.fileutils;

import android.os.Environment;
import com.account.number.MyApplication;


import java.io.*;

/**
 * 文件IO相关操作的工具类
 * 读取文件；存储文件
 */
public class FileIoUtils {

    //缓冲区的大小
    private static final int BufferSize = 8192;

    /**
     * 获取保存文件的绝对路径
     *
     * @param fileName 文件名字，注意这里的filename是没有路径的，仅仅就是文件的名字
     * @return 结果类似如下：
     * <pre>path: /storage/emulated/0/Android/data/package/files/fileName</pre>
     */
    public static String getCacheRootPath(String fileName) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            return FilePathUtils.getAppExtFilePath() + "/" + fileName;
        } else {
            return FilePathUtils.getInAppFilesPath() + "/" + fileName;
        }
    }

    /**
     * 获取保存文件的绝对路径
     *
     * @param category 类别：通俗的就是分类，将不同功能的file文件保存到相应的文件夹下
     * @param fileName 文件名字，注意这里的filename是没有路径的，仅仅就是文件的名字
     * @return 结果类似如下：
     * <pre>path: /storage/emulated/0/Android/data/package/files/category/fileName</pre>
     */
    public static String getCacheRootPath(String category, String fileName) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            return FilePathUtils.getAppExtFilePath() + "/" + category + "/" + fileName;
        } else {
            return FilePathUtils.getInAppFilesPath() + "/" + category + "/" + fileName;
        }
    }

    /**
     * 获取缓存到目标文件的File对象
     *
     * @param fileName 文件名字，注意这里的filename是没有路径的，仅仅就是文件的名字
     * @return 返回File对象
     */
    public static File getCacheRootFile(String fileName) {
        return FileUtils.getFileByPath(getCacheRootPath(fileName));
    }

    /**
     * 获取缓存到目标文件的File对象
     *
     * @param category 类别：通俗的就是分类，将不同功能的file文件保存到相应的文件夹下
     * @param fileName 文件名字，注意这里的filename是没有路径的，仅仅就是文件的名字
     * @return 返回File对象
     */
    public static File getCacheRootFile(String category, String fileName) {
        return FileUtils.getFileByPath(getCacheRootPath(category, fileName));
    }


    /**
     * 将内容保存到文件
     *
     * @param fileName 文件的绝对路径,文件路径的获取推荐使用{@link FileIoUtils#getCacheRootPath(String, String)}},统一获取，注意这里的filename是带有路径的
     * @param content  保存的内容
     */
    public static void save(String fileName, String content) {
        //默认覆盖原有的文件
        save(fileName, content, false);
    }

    /**
     * 将内容保存到文件
     *
     * @param fileName 文件的绝对路径,文件路径的获取推荐使用{@link FileIoUtils#getCacheRootPath(String, String)},统一获取，注意这里的filename是带有路径的
     * @param content  保存的内容
     */
    public static void save(String fileName, String content, boolean append) {
        save(FileUtils.getFileByPath(fileName), content, append);
    }


    /**
     * 将内容保存到文件
     *
     * @param file    保存到的那个文件对象，文件对象的获取推荐使用{@link FileIoUtils#getCacheRootFile(String, String)},统一获取
     * @param content 保存的内容
     */
    public static void save(File file, String content) {
        //默认覆盖原有的文件
        save(file, content, false);
    }

    /**
     * 将内容保存到文件
     *
     * @param file    保存到的那个文件对象，文件对象的获取推荐使用{@link FileIoUtils#getCacheRootFile(String, String)},统一获取
     * @param content 保存的内容
     * @param append  {@code true}: 在原有的文件后面进行续写 ;{@code false}: 覆盖原有的文件
     */
    public static void save(File file, String content, boolean append) {
        try {
            save(file, content.getBytes("UTF-8"), append);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将内容保存到文件
     *
     * @param file    保存到的那个文件对象，文件对象的获取推荐使用{@link FileIoUtils#getCacheRootFile(String, String)},统一获取
     * @param content 保存的内容，字节类型
     * @param append  {@code true}: 在原有的文件后面进行续写 ;{@code false}: 覆盖原有的文件
     */
    public static void save(File file, byte[] content, boolean append) {
        try {
            if (!FileUtils.createFile(file)) {
                //创建文件夹失败
                return;
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file, append));
            bos.write(content, 0, content.length);
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将内容保存到文件
     *
     * @param file    保存到的那个文件对象，文件对象的获取推荐使用{@link FileIoUtils#getCacheRootFile(String, String)},统一获取
     * @param content 保存的内容，流
     * @param append  {@code true}: 在原有的文件后面进行续写 ;{@code false}: 覆盖原有的文件
     */
    public static void save(File file, InputStream content, boolean append) {
        try {
            if (!FileUtils.createFile(file)) {
                //创建文件夹失败
                return;
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file, append));
            byte[] buffer = new byte[BufferSize];
            int count = 0;
            while ((count = content.read(buffer)) > 0) {
                bos.write(buffer, 0, count);
            }
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文件中的内容
     *
     * @param fileName 如果保存文件时路径是通过{@link FileIoUtils#getCacheRootFile(String, String)}获取的，
     *                 这里的fileName参数也可以通过{@link FileIoUtils#getCacheRootFile(String, String)}获取，注意这里的filename是带有路径的
     * @return {@code String}:文件中存储的内容
     */
    public static String read(String fileName) {
        return read(FileUtils.getFileByPath(fileName));
    }

    /**
     * 读取文件中的内容
     *
     * @param file 如果保存文件时路径是通过{@link FileIoUtils#getCacheRootFile(String, String)}获取的，
     *             这里的fileName参数也可以通过{@link FileIoUtils#getCacheRootFile(String, String)}获取
     * @return {@code String}:文件中存储的内容
     */
    public static String read(File file) {
        StringBuffer content = new StringBuffer();
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            //自己定义一个缓冲区,不设置，默认的
            byte[] buffer = new byte[BufferSize];
            int flag = 0;
            while ((flag = bis.read(buffer)) != -1) {
                content.append(new String(buffer, 0, flag));
            }
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    /**
     * 从Raw中获取文件内容
     *
     * @param resId raw文件夹的资源id
     * @return 文件内容
     */
    public static String readFromRaw(int resId) {
        StringBuffer content = new StringBuffer();
        try {
            //得到资源中的Raw数据流
            InputStream in = MyApplication.getInstance().getResources().openRawResource(resId);
            //得到数据的大小
            int length = in.available();
            byte[] buffer = new byte[BufferSize];
            //读取数据
            int flag = 0;
            while ((flag = in.read(buffer)) != -1) {
                content.append(new String(buffer, 0, flag));
            }
            //关闭
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    /**
     * 从Assets的文件中获取文件内容
     *
     * @param fileName 文件名
     * @return 文件内容
     */
    public static String readStrFromAssets(String fileName) {
        StringBuffer content = new StringBuffer();
        try {
            InputStream in = MyApplication.getInstance().getResources().getAssets().open(fileName);
            // 创建byte数组
            byte[] buffer = new byte[BufferSize];
            //读取数据
            int flag = 0;
            while ((flag = in.read(buffer)) != -1) {
                content.append(new String(buffer, 0, flag));
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    /**
     * 从Assets的文件中获取文件流
     *
     * @param fileName 文件名
     * @return 文件流
     */
    public static InputStream readISFromAssets(String fileName) {
        InputStream in = null;
        try {
            in = MyApplication.getInstance().getResources().getAssets().open(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return in;
    }
}
