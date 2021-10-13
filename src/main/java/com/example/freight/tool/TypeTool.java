package com.example.freight.tool;

import com.example.freight.domain.Model;
import org.apache.poi.ss.usermodel.Cell;

import java.util.HashMap;
import java.util.Map;

public class TypeTool {

//    public static boolean typeTool;

    public static boolean typeTool(String str) {
        return str.equals("y") || str.equals("Y") || str.equals("yes") || str.equals("YES");
    }

    public static boolean superLong(int w, int l, int h) {
        return w <= 48 && l <= 48 && h <= 48;
    }

    public static boolean judgmentNull(Model model) {
        if (model.getSku().isEmpty()) {
            return true;
        } else if (model.getLength() == 0 || model.getWidth() == 0 || model.getHeight() == 0 || model.getWeight() == 0 || model.getGroupNum() == 0) {
            return true;
        } else
            return model.getType() == 1 && (model.getvLength() == 0D || model.getvWidth() == 0D || model.getvHeight() == 0D);
    }


}
