package com.example.gonggu.smallTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestOpString_v2 {
    public static void main(String[] args) {
        String option_1  = "사이즈>L,M,S";
        String option_2  = "사이즈>L,M,S/색상>빨,파,흰";
        String option_3 = "사이즈>L,M,S/색상>빨,파,흰/로고>나이키,아디다스,뉴발란스";

        String eachPart[] = option_3.split("/");
        List title = new ArrayList();
        List options = new ArrayList();
        List optionArray = new ArrayList();

        StringBuilder sb = new StringBuilder();

        for(String str : eachPart){
            String temp[] = str.split(">");
            title.add(temp[0]);
            optionArray.add(temp[1]);
//            options.addAll(Arrays.asList(temp[1].split(",")));
//            options.add("/");
//            OptionInfo optionInfo = new OptionInfo(str);
//            System.out.println(optionInfo.toString());
        }

        int optionArrSize = optionArray.size();
        String[] opArr_1 = optionArray.get(0).toString().split(",");
        String[] opArr_2;
        String[] opArr_3;

        String parentStr;
        String grParentStr;

        switch (optionArrSize){
            case 1:
                for(int i=0;i<opArr_1.length ;i++){
                    sb.append(opArr_1[i]);
                    if(i != opArr_1.length-1) sb.append(" ");
                }
                break;
            case 2:
                opArr_2 = optionArray.get(1).toString().split(",");
                for(int i=0;i<opArr_1.length;i++) {
                    parentStr = opArr_1[i];
                    for (int p = 0; p < opArr_2.length; p++) {
                        sb.append(parentStr);
                        sb.append(",");
                        sb.append(opArr_2[p]);
                        sb.append(" ");
                    }
                }
                break;
            case 3:
                opArr_2 = optionArray.get(1).toString().split(",");
                opArr_3 = optionArray.get(2).toString().split(",");
                for(int i=0;i<opArr_1.length;i++){
                    grParentStr = opArr_1[i];
                    for(int p=0;p<opArr_2.length;p++){
                        parentStr = grParentStr + "," + opArr_2[p];
                        for(int k=0;k<opArr_3.length;k++){
                            sb.append(parentStr);
                            sb.append(",");
                            sb.append(opArr_3[k]);
                            sb.append(" ");
                        }
                    }
                }
                break;
            default:
                break;
        }


        System.out.println(sb.toString().trim());

    }

    public static class OptionInfo{
        public String title;
        public String[] options;

        public OptionInfo(String orizInfo){
            String temp[] = orizInfo.split(">");
            this.title = temp[0];
            this.options = temp[1].split(",");
        }

        @Override
        public String toString(){
            return "title : "+ title +"\noptions : "+ Arrays.toString(options);
        }


    }
}
