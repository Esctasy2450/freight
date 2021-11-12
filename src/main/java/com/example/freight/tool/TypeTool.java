package com.example.freight.tool;

import com.example.freight.domain.Model;

public class TypeTool {


    public static boolean typeTool(String str) {
        return str.equals("y") || str.equals("Y") || str.equals("yes") || str.equals("YES");
    }

    public static boolean superLong(Model model, boolean type, int i) {
        if (type && 1 == i){
            return model.getVLength() <=48 && model.getVWidth() <= 48 && model.getVHeight() <= 48;
        }else {
            return model.getWidth() <= 48 && model.getLength() <= 48 && model.getHeight() <= 48;
        }
    }

    public static boolean judgmentNull(Model model) {
        if (model.getSku().isEmpty())
            return true;

        if (model.getLength() == 0 || model.getWidth() == 0 || model.getHeight() == 0 || model.getWeight() == 0 || model.getGroupNum() == 0)
            return true;

            return model.getType() == 1 && (model.getVLength() == 0D || model.getVWidth() == 0D || model.getVHeight() == 0D);
    }
}
