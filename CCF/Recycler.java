import java.util.*;

public class Recycler {
    public static void main(String[] args) {
        select();
    }
    public static void select(){
        Scanner input = new Scanner(System.in);
        int num = input.nextInt();
        long[][] location = new long[num][2];
        long[][] station = new long[num][2];
        int N = 0;
        int a=0, b=0, c=0, d=0, e=0;
        for (int i=0;i<num;i++){
            location[i][0] = input.nextInt();
            location[i][1] = input.nextInt();
        }

        for (int i=0;i<num;i++){  //判断是否为垃圾站
            int near = 0;
            for (int j=0;j<num;j++){
                if (location[i][0]==location[j][0] && i!=j){ //定横判纵,上下
                    if (location[i][1]==location[j][1]-1 || location[i][1]==location[j][1]+1){
                        near++;
                    }
                }
                if (location[i][1]==location[j][1] && i!=j){ //定纵判横，左右
                    if (location[i][0]==location[j][0]-1 || location[i][0]==location[j][0]+1){
                        near++;
                    }
                }
                if (near==4){
                    station[N][0] = location[i][0];
                    station[N][1] = location[i][1];
                    N++;
                    break;
                }
            }
        }

        for (int i=0;i<N;i++){  //判断垃圾站得分
            int score = 0;
            for (int j=0;j<num;j++){
                if (station[i][1]==location[j][1]-1 || station[i][1]==location[j][1]+1){
                    if (station[i][0]==location[j][0]-1 || station[i][0]==location[j][0]+1){
                        score++;
                    }
                }
            }
            if (score==0){
                a++;
            }else if (score==1){
                b++;
            }else if (score==2){
                c++;
            }else if (score==3){
                d++;
            }else if (score==4){
                e++;
            }
        }
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);
        System.out.println(e);
    }
} 
