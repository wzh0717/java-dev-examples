package com.wangzh.app.commons.process;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @Description:
 * @Auther:wangzh
 * @Date: 2019/03/24 16:21
 */

public abstract class ProcessUtils {

    /**
     * 执行命令脚本
     *
     * @param command
     * @return
     */
    public static boolean execCommand(String command) {
        try {
            Process p = Runtime.getRuntime().exec(command);
            if (null == p) return false;
            //读取标准输出流
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            System.out.println("标准输出流");
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
            //读取标准错误流
            System.out.println("标准错误流");
            BufferedReader brError = new BufferedReader(new InputStreamReader(p.getErrorStream(), StandardCharsets.UTF_8));
            while ((line = brError.readLine()) != null) {
                System.out.println(line);
            }

            int exitVal = p.waitFor();
            System.out.println("process exit value is " + exitVal);
            return exitVal == 0 ? true : false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
