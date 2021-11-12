package com.example.freight.tool;

import com.example.freight.domain.Model;

public class TypeTool {


    public static boolean typeTool(String str) {
        return str.equals("y") || str.equals("Y") || str.equals("yes") || str.equals("YES");
    }

    public static boolean superLong(Model model, boolean type, int i) {
        if (type && 1 == i){
            return model.getvHeight() <=48 && model.getvLength() <= 48 && model.getvWidth() <= 48;
        }else {
            return model.getWidth() <= 48 && model.getLength() <= 48 && model.getHeight() <= 48;
        }
    }

    public static boolean judgmentNull(Model model) {
        if (model.getSku().isEmpty())
            return true;

        if (model.getLength() == 0 || model.getWidth() == 0 || model.getHeight() == 0 || model.getWeight() == 0 || model.getGroupNum() == 0)
            return true;

            return model.getType() == 1 && (model.getvLength() == 0D || model.getvWidth() == 0D || model.getvHeight() == 0D);
    }
}
