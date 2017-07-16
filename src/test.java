
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sri
 */
public class test {
    public static String binAdition(String a,String b){ //performs binary addition when two binary numbers are passed
       String result = "";
       int carry =0;
       int size = a.length();
       int size1 = b.length();
       for(int i =size-1;i>=0;i--){
          
           int sum = Integer.parseInt(a.substring(i, i+1))+Integer.parseInt(b.substring(i, i+1))+carry;
           if(sum==0){
               result="0"+result;
               
           }
           else if(sum==1){
               result = "1"+result;
               if(carry==1){
                   carry=0;
               }
           }
           else if(sum==2){
               result="0"+result;
               if(carry==1){
                   carry=0;
               }
               else{
                   carry=1;
               }
           }
           else if(sum==3){
               result="1"+result;
               carry = 1;
           }
           sum=0;
       }
       if(carry==1){
           result = "1"+result;
       }
       
        return result;
    }
    public static int replace(String x){
       
//        String binary = Integer.toBinaryString(x);
//        binary = binary.substring(binary.length()-8);
//        System.out.println(binary);
        long l = Long.parseLong(x, 2);
        int i = (int) l;
        System.out.println(i);
       return i;
    }
    
    
    public static String addBinaryNumbers(String num1, String num2) {
        int p1 = num1.length() - 1;
        int p2 = num2.length() - 1;
        StringBuilder buf = new StringBuilder();
        int carry = 0;
        while (p1 >= 0 || p2 >= 0) {
            int sum = carry;
            if (p1 >= 0) {                         
                sum += num1.charAt(p1) - '0';
                p1--;
            }
            if (p2 >= 0) {
                sum += num2.charAt(p2) - '0';
                p2--;
            }
            carry = sum >> 1;
            sum = sum & 1;
            buf.append(sum == 0 ? '0' : '1');
        }
        if (carry > 0) {
            buf.append('1');
        }
        buf.reverse();
        return buf.toString();
    }
       protected static String binadd(String a,String b){ //performs binary addition when two binary numbers are passed
     int sizeA = a.length();
     int sizeB = b.length();
     
     String addResult = new String();
     int carry = 0;
     if(sizeA<12){
         while(a.length()<12){
             a = "0"+a;
         }
     }
     if(sizeB<12){
         while(b.length()<12){
             b = "0"+b;
         }
     }
        sizeA = a.length();   
        sizeB = b.length();
           for(int i =sizeA-1;i>=0;i--){
          
           int sum = Integer.parseInt(a.substring(i, i+1))+Integer.parseInt(b.substring(i, i+1))+carry;
           if(sum==0){
               addResult="0"+addResult;
               
           }
           else if(sum==1){
               addResult = "1"+addResult;
               if(carry==1){
                   carry=0;
               }
           }
           else if(sum==2){
               addResult="0"+addResult;
               if(carry==1){
                   carry=0;
               }
               else{
                   carry=1;
               }
           }
           else if(sum==3){
               addResult="1"+addResult;
               carry = 1;
           }
           sum=0;
       }
       if(carry==1){
           addResult = "1"+addResult;
       }
        
     return addResult.substring(addResult.length()-12);
    }
    
    public static void main(String args[]){
        System.out.println(replace("11111111111111111111111111111011"));
    }
}
