package com.jewin.common.util;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by jianyang on 17/8/2.
 */
public class FileReaderUtilTest {

    @Test
    public void testReadAndProcessEachLine() throws Exception {
//        String filePath = "/Users/jianyang/Documents/50元流量买一赠一活动目标号码 12224.txt";
//        List<String> lines = new LinkedList<String>();
//        Set<String> set = new HashSet<String>();
//        Map<String,Set<String>> telnoPrefixAndTelnosMap = new HashMap<>();
//        Integer count = FileReaderUtil.readAndProcessEachLine(filePath, "UTF-8", new FileReaderUtil.ProcessCallback<Integer>(){
//            @Override
//            public Integer callBack(int lineIndex, String line) {
//                if(line.length() < 8){
//                    System.out.println(line + "  " + lineIndex);
//                }else{
//                    boolean status = set.add(line);
//                    if(!status){
//                        System.out.println("重复：" + line);
//                    }
//
//                    String prefix = null;
//                    if (line.startsWith("10648") || line.startsWith("14765")){
//                        prefix = line.substring(0, 9);
//                        System.out.println("----------9位号段------:" + line);
//                    } else{
//                        prefix = line.substring(0, 7);
//                    }
//
//                    if(telnoPrefixAndTelnosMap.containsKey(prefix)){
//                        telnoPrefixAndTelnosMap.get(prefix).add(line);
//                    }else{
//                        Set<String> telnoSet = new HashSet<>();
//                        telnoSet.add(line);
//                        telnoPrefixAndTelnosMap.put(prefix, telnoSet);
//                    }
//                }
//                return lineIndex;
//            }
//        });
//        System.out.println("data = " + telnoPrefixAndTelnosMap);
    }

    @Test
    public void testGenTelnoFile() throws Exception{
        String telnoPath = "/Users/jianyang/work/code/java/tools/jtool/src/test/java/com/jewin/common/util/telno.txt";
        StringBuffer sb = new StringBuffer();
        for(long telno = 12300000000l; telno < 12300020000l; telno++){
            sb.append(telno).append("\n");
        }
        FileUtils.writeStringToFile(new File(telnoPath), sb.toString(), "UTF-8");
    }


}
