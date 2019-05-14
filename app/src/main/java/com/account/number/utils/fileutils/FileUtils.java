package com.account.number.utils.fileutils;


import android.text.TextUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

/**
 * 这个类主要是文件和文件夹的相关操作
 * 目前有的功能：
 * 判断文件夹是否存在；文件改名；判断是文件还是文件夹；创建文件或者文件夹；获取文件或者文件夹大小；
 * 删除文件或文件夹
 */
public class FileUtils {
    /**
     * 通过路径获取文件夹
     *
     * @param filePath 文件夹路径
     * @return 文件夹
     */
    public static File getFileByPath(final String filePath) {
        return TextUtils.isEmpty(filePath) ? null : new File(filePath);
    }

    /**
     * 判断文件夹是否存在
     *
     * @param filePath 文件夹路径
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public static boolean isFileExists(final String filePath) {
        return isFileExists(getFileByPath(filePath));
    }

    /**
     * 判断文件夹是否存在
     *
     * @param file 文件夹
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public static boolean isFileExists(final File file) {
        return file != null && file.exists();
    }

    /**
     * 文件夹改名
     *
     * @param filePath 当前需要改名的文件夹路径
     * @param newName  改名后的文件夹名字
     * @return {@code true}: 改名成功 <br>{@code false}: 改名失败
     */
    public static boolean rename(final String filePath, final String newName) {
        return rename(getFileByPath(filePath), newName);
    }

    /**
     * 文件夹改名
     *
     * @param file    当前需要改名的文件夹
     * @param newName 改名后的文件夹名字
     * @return {@code true}: 改名成功 <br>{@code false}: 改名失败
     */
    public static boolean rename(final File file, final String newName) {
        if (file == null) return false;
        if (!file.exists()) return false;
        if (TextUtils.isEmpty(newName)) return false;
        if (newName.equals(file.getName())) return true;
        File newFile = new File(file.getParent() + File.separator + newName);
        return !newFile.exists() && file.renameTo(newFile);
    }

    /**
     * 判断是不是文件夹
     *
     * @param dirPath 需要判断的目标文件夹路径
     * @return {@code true}: 是文件夹 <br>{@code false}: 不是文件夹
     */
    public static boolean isDir(final String dirPath) {
        return isDir(getFileByPath(dirPath));
    }

    /**
     * 判断是不是文件夹
     *
     * @param file 需要判断的目标文件夹
     * @return {@code true}: 是文件夹 <br>{@code false}: 不是文件夹
     */
    public static boolean isDir(final File file) {
        return file != null && file.exists() && file.isDirectory();
    }

    /**
     * 判断是不是文件
     *
     * @param filePath 需要判断的目标文件路径
     * @return {@code true}: 是文件 <br>{@code false}: 不是文件
     */
    public static boolean isFile(final String filePath) {
        return isFile(getFileByPath(filePath));
    }

    /**
     * 判断是不是文件
     *
     * @param file 需要判断的目标文件路径
     * @return {@code true}: 是文件 <br>{@code false}: 不是文件
     */
    public static boolean isFile(final File file) {
        return file != null && file.exists() && file.isFile();
    }

    /**
     * 创建一个文件夹
     *
     * @param dirPath 需要创建的文件夹路径
     * @return {@code true}:文件夹存在或创建成功<br>{@code false}: 创建文件夹失败或存在相同名字的文件
     */
    public static boolean createDir(final String dirPath) {
        return createDir(getFileByPath(dirPath));
    }

    /**
     * 创建一个文件夹
     *
     * @param file 需要创建的文件夹
     * @return {@code true}:文件夹存在或创建成功<br>{@code false}: 创建文件夹失败或存在相同名字的文件
     */
    public static boolean createDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }


    /**
     * 创建一个文件
     *
     * @param filePath 需要创建的文件路径
     * @return {@code true}:文件存在或创建成功<br>{@code false}: 创建文件失败或存在相同名字的文件夹
     */
    public static boolean createFile(final String filePath) {
        return createFile(getFileByPath(filePath));
    }

    /**
     * 创建一个文件
     *
     * @param file 需要创建的文件
     * @return {@code true}:文件存在或创建成功<br>{@code false}: 创建文件失败或存在相同名字的文件夹
     */
    public static boolean createFile(final File file) {
        if (file == null) return false;
        if (file.exists()) return file.isFile();
        if (!createDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取文件或文件夹的大小
     *
     * @param dirPath 目标文件的路径
     * @return 目标文件的大小
     */
    public static long getFileSize(final String dirPath) {
        return getFileSize(getFileByPath(dirPath));
    }

    /**
     * 获取文件或文件夹的大小
     * @param dir 目标文件
     * @return 目标文件的大小
     */
    public static long getFileSize(final File dir) {
        long size = 0;
        try {
            File[] fileList = dir.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    size = size + getFileSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }


    /**
     * 删除文件夹
     *
     * @param dirPath 需要删除的文件夹路径
     * @return {@code true}: 删除成功 <br>{@code false}: 删除失败
     */
    public static boolean deleteDir(final String dirPath) {
        return deleteDir(getFileByPath(dirPath));
    }

    /**
     * 删除文件夹
     *
     * @param dir 需要删除的文件夹
     * @return {@code true}: 删除成功 <br>{@code false}: 删除失败
     */
    public static boolean deleteDir(final File dir) {
        if (dir == null) return false;
        if (!dir.exists()) return true;
        if (!dir.isDirectory()) return false;
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete()) return false;
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) return false;
                }
            }
        }
        return dir.delete();
    }

    /**
     * 删除文件
     *
     * @param srcFilePath 需要删除的文件路径
     * @return {@code true}: 删除成功 <br>{@code false}: 删除失败
     */
    public static boolean deleteFile(final String srcFilePath) {
        return deleteFile(getFileByPath(srcFilePath));
    }

    /**
     * 删除文件
     *
     * @param file 需要删除的文件
     * @return {@code true}: 删除成功 <br>{@code false}: 删除失败
     */
    public static boolean deleteFile(final File file) {
        return file != null && (!file.exists() || file.isFile() && file.delete());
    }

    /**
     * 删除指定文件夹下的所有东西
     *
     * @param dirPath 目标文件夹路径
     * @return {@code true}: 删除成功 <br>{@code false}: 删除失败
     */
    public static boolean deleteAllInDir(final String dirPath) {
        return deleteAllInDir(getFileByPath(dirPath));
    }

    /**
     * 删除指定文件夹下的所有东西
     *
     * @param dir 目标文件夹
     * @return {@code true}: 删除成功 <br>{@code false}: 删除失败
     */
    public static boolean deleteAllInDir(final File dir) {
        return deleteFilesInDirWithFilter(dir, new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                //这里删除目标文件夹下的所有东西
                return true;
            }
        });
    }


    /**
     * 删除指定文件夹下不需要的东西，不需要删除的文件或文件夹通过FileFilter过滤
     *
     * @param dirPath 目标文件夹
     * @param filter  过滤器，指定哪些文件夹或文件不能删除
     * @return {@code true}: 删除成功 <br>{@code false}: 删除失败
     */
    public static boolean deleteFilesInDirWithFilter(final String dirPath,
                                                     final FileFilter filter) {
        return deleteFilesInDirWithFilter(getFileByPath(dirPath), filter);
    }

    /**
     * 删除指定文件夹下不需要的东西，不需要删除的文件或文件夹通过FileFilter过滤
     *
     * @param dir    目标文件
     * @param filter 过滤器，指定哪些文件夹或文件不能删除
     * @return {@code true}: 删除成功 <br>{@code false}: 删除失败
     */
    public static boolean deleteFilesInDirWithFilter(final File dir, final FileFilter filter) {
        if (dir == null) return false;
        // dir doesn't exist then return true
        if (!dir.exists()) return true;
        // dir isn't a directory then return false
        if (!dir.isDirectory()) return false;
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (filter.accept(file)) {
                    if (file.isFile()) {
                        if (!file.delete()) return false;
                    } else if (file.isDirectory()) {
                        if (!deleteDir(file)) return false;
                    }
                }
            }
        }
        return true;
    }
}
