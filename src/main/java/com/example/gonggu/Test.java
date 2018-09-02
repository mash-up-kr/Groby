package com.example.gonggu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        //1
//        String hello = "사이즈>L:100,M:0,S:-100";
//        String[] optionAndPrice = hello.split(">")[1].split(",");
//        List<String> list = new ArrayList<>();
//        for(String str : optionAndPrice){
//            list.add(str.split(":")[0]);
//        }
//        System.out.println(Arrays.toString(list.toArray()));

        //2
        String hello = "사이즈>L:100,M:0,S:-100/색상>빨:1000,파:1000,흰:1000";
        String[] optionsWrapper =hello.split("/");
        List<String> list = new ArrayList<>();
        String[] firstOptionAndPrice = optionsWrapper[0].split(">")[1].split(",");
        List<String> secondList = Arrays.asList(optionsWrapper[1].split(">")[1].split(","));
        Collections.reverse(secondList);
        String[] secondOptionAndPrice = secondList.toArray(new String[secondList.size()]);

        StringBuilder sb = new StringBuilder();
        for(int f = 0;f<firstOptionAndPrice.length;f++){
            String parentStr = firstOptionAndPrice[f].split(":")[0];
            for(int s=0;s<secondOptionAndPrice.length;s++){
                sb.append(parentStr);
                sb.append(" ");
                sb.append(secondOptionAndPrice[s].split(":")[0]);
                list.add(f*secondOptionAndPrice.length,sb.toString());
                sb.delete(0,sb.length());
            }

        }

        System.out.println(Arrays.toString(list.toArray()));





        //3
//        String world = "사이즈>L:100,M:0,S:-100/색상>빨:1000,파:1000,흰:1000/배송>산간:100,서울:0,집근처:-100";
//        String[] optionsWrapper =world.split("/");
//        List<String> list = new ArrayList<>();
//        String[] firstOptionAndPrice = optionsWrapper[0].split(">")[1].split(",");
//
//        List<String> optionList = Arrays.asList(optionsWrapper[1].split(">")[1].split(","));
//        Collections.reverse(optionList);
//        String[] secondOptionAndPrice = optionList.toArray(new String[optionList.size()]);
//
//        optionList = Arrays.asList(optionsWrapper[2].split(">")[1].split(","));
//        Collections.reverse(optionList);
//        String[] thirdOptionAndPrice = optionList.toArray(new String[optionList.size()]);
//
//        StringBuilder sb = new StringBuilder();
//        for(int f = 0;f<firstOptionAndPrice.length;f++){
//            String firstStr = firstOptionAndPrice[f].split(":")[0];
//            for(int s=0;s<secondOptionAndPrice.length;s++){
//                String secondStr = secondOptionAndPrice[s].split(":")[0];
//                for(int t=0;t<thirdOptionAndPrice.length;t++){
//                    sb.append(firstStr); sb.append(" ");
//                    sb.append(secondStr); sb.append(" ");
//                    sb.append(thirdOptionAndPrice[t].split(":")[0]);
//                    list.add(f*secondOptionAndPrice.length*thirdOptionAndPrice.length + s*thirdOptionAndPrice.length , sb.toString().trim());
//                    sb.delete(0,sb.length());
//                }
//            }
//
//        }
//
//        System.out.println(Arrays.toString(list.toArray()));


        int[] countlist = new int[list.size()];
        String p1 = "L 빨:1>100";
        String p2 = "L 빨:2/S 파:2>200";
        String[] opt = p1.split(">")[0].split("/");
        for(int person = 0;person<opt.length;person++){
            for(int i=0;i<list.size();i++){
                if(list.get(i).equals(opt[person].split(":")[0])){
                    countlist[i] += Integer.parseInt(opt[person].split(":")[1]);
                    continue;
                }
            }
        }
        opt = p2.split(">")[0].split("/");
        for(int person = 0;person<opt.length;person++){
            for(int i=0;i<list.size();i++){
                if(list.get(i).equals(opt[person].split(":")[0])){
                    countlist[i] += Integer.parseInt(opt[person].split(":")[1]);
                    continue;
                }
            }
        }

        System.out.println(Arrays.toString(countlist));


    }
}
