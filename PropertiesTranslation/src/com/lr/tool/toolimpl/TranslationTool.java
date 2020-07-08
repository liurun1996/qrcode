package com.lr.tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lr.bean.TransResultJson;
import com.lr.demo.TransApi;

import java.io.*;

public class TranslationTool implements Translation {
    String logPath;

    public File[] GetFiles(String path) {

        File file = new File(path);
        File[] files;
        if (file.isDirectory()) {
            files = file.listFiles();
        } else {
            files = new File[1];
            files[0] = file;
        }
        return files;
    }


    public TransResultJson ToTranslation(String word, String FromLanguage, String ToLanguage) {
        TransApi api = new TransApi("20200611000492836", "YviZhzhYDz1RmUPZEU4x");
        String str = api.getTransResult(word, FromLanguage, ToLanguage);
//        System.out.println(str);//输出结果，即json字段
        JSONObject jsonObject = JSON.parseObject(str);
        TransResultJson retuenJson = JSON.toJavaObject(jsonObject, TransResultJson.class);
        return retuenJson;
    }
    public void TranslationFile(File file, String FromLanguage, String ToLanguage, String ToPath, int IsReverse) throws IOException, InterruptedException {
        logPath = ToPath + "//log";
        BufferedReader br = new BufferedReader(new FileReader(file));
        if (!ToPath.endsWith("//"))
            ToPath = ToPath + "//";
        PrintStream trans_ps = new PrintStream(new FileOutputStream(ToPath + file.getName(), true), true);
        String line = null;
        int trans = 0;
        int Ntrans = 0;
        int BlankLine = 0;
        while ((line = br.readLine()) != null) {
           /* if (file.getName().endsWith("properties"))
                return;*/
            if ("".equals(line)) {
                BlankLine += 1;
            } else {
                if (!line.contains("{{") && !line.contains("\\") && !line.matches(".*[0-9].*")) {
                    String[] split = line.split("=");
                    String ara = this.ToTranslation(split[1], FromLanguage, ToLanguage).getTrans_result().get(0).getDst();
                    if (IsReverse == 0) {
                        trans_ps.println(split[0] + "=" + ara);
                    } else if (IsReverse == 1) {
                        trans_ps.println(ara + "=" + split[0]);
                    }
                    trans_ps.flush();
                    trans += 1;
                    Thread.sleep(1000);
                } else {
                    trans_ps.println(line);
                    trans_ps.flush();
                    Ntrans += 1;
                }
            }

        }
        br.close();
        trans_ps.close();
        File f = new File(ToPath + "log//");
        if (f.exists() == false)
            f.mkdirs();
        String filename = file.getName();
        String logName = filename.substring(0, filename.indexOf(".") + 1) + "log";
        File logDir = new File(logPath);
        if (logDir.isFile() || !logDir.exists())
            logDir.mkdirs();
        PrintStream log_ps = new PrintStream(new FileOutputStream(logDir + "//log.txt", true), true, "utf-8");
        log_ps.println(file.getName() + "已经翻译" + trans + "行，未翻译带符号" + Ntrans + "行" + BlankLine + "行空白");
        log_ps.close();
    }

    @Override
    public void translation(String FromPath, String ToPath, String FromLanguage, String ToLanguage, int IsReverse) {
        System.err.println(FromPath);
        File From = new File(FromPath);
        File to = new File(ToPath);
        if (From.exists() == false)
            return;

        if (to.exists() == false || to.isFile())
            to.mkdirs();

        File[] files = GetFiles(FromPath);
        System.out.println(files.length);
        for (File f : files) {
            System.out.println(f.getName());
            try {
//              this.TranslationFile(f, FromLanguage, ToLanguage, ToPath,IsReverse);
                this.TranslationFile(f, FromLanguage, ToLanguage, ToPath, IsReverse);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


//    public static void main(String[] args) throws IOException, InterruptedException {
//        TranslationTool t = new TranslationTool();
//        t.translation("C:\\Users\\liurun\\Desktop\\en-US", "D:\\aaa", "en", "ara", 1);
//    }
//    public  Map<String, Map<String, String>> Properties2Map(File PropertiesFile) throws IOException {
//        BufferedReader br = new BufferedReader(new FileReader(PropertiesFile));
//        Map<String, String> map = new HashMap<>();
//        String line = null;
//        String[] split;
//        while ((line = br.readLine()) != null) {
//            split = line.split("=");
//            if (split.length == 2) {
//                map.put(split[0], split[1]);
//            } else {
//                System.out.println(line);
//            }
//
//        }
//        System.out.println(map.size());
//        Map<String, Map<String, String>> FileMap = new HashMap<>();
//        FileMap.put(PropertiesFile.getName(), map);
//        return FileMap;
//    }
//
//    public  List<Map<String, Map<String, String>>> AllFile2Map(File[] files) throws IOException {
//        List<Map<String, Map<String, String>>> AllMap = new ArrayList<>();
//        for (File file : files) {
//            Map<String, Map<String, String>> Properties2Map = Properties2Map(file);
//            AllMap.add(Properties2Map);
//        }
//        return AllMap;
//    }


//    private  void translation(List<Map<String, Map<String, String>>> maps) throws Exception {
//        maps.forEach(map -> {
//            map.forEach((k, v) -> {
//                System.out.println(k);
//                long l = System.currentTimeMillis();
//                System.out.println(v.size());
//                v.forEach((k1, v1) -> {
//                    try {
//                        FileOutputStream fis = new FileOutputStream(new File("d://aaa//" + k), true);
//                        PrintWriter pw = new PrintWriter(fis);
//                        if (!v1.contains("{{") && !v1.contains("\\")) {
//                            String result = Translation(v1, "en","ara").getTrans_result().get(0).getDst();
//                            pw.println(result + "=" + k1);
//                        } else {
//                            pw.println(v1 + "=" + k1);
//                        }
//                        fis.flush();
//                        pw.flush();
//                        Thread.sleep(1000);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                });
//                long l1 = System.currentTimeMillis();
//                System.out.println(k + "用时：" + (l1 - l) / 1000);
//            });
//        });
//    }


}
