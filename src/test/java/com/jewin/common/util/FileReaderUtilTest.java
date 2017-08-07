package com.jewin.common.util;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jianyang on 17/8/2.
 */
public class FileReaderUtilTest {

    @Test
    public void testReadAndProcessEachLine() throws Exception {
        String filePath = "/Users/jianyang/work/code/java/mywork/jtool/src/main/java/com/jewin/common/util/FileReaderUtil.java";
        List<String> lines = new LinkedList<String>();
        Integer count = FileReaderUtil.readAndProcessEachLine(filePath, "UTF-8", new FileReaderUtil.ProcessCallback<Integer>(){
            @Override
            public Integer callBack(int lineIndex, String line) {
                System.out.println(line);
                lines.add(line);
                if(lineIndex % 10 == 0){
                    System.out.println(lines);
                    lines.clear();
                }
                return lineIndex;
            }
        });
        System.out.println(count);
    }
}
