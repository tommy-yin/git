import java.util.*;

public class baoshu {
    public static void main(String[] args) {
        new baoshu().jump();
    }

    public void jump(){
        Scanner input = new Scanner(System.in);
        int N = input.nextInt();
        int A=0, B=0, C=0, D=0;
        int num=0, i=1;
        while (num<N){
            if (i<100 && (i%10==7 || i/10==7 || i%7==0)){
                if (i%4==0)         D++;
                else if (i%4==1)    A++;
                else if (i%4==2)    B++;
                else if (i%4==3)    C++;
            }else if (i>=100 && (i%10==7 || (i/10%10==7) || i/100==7 || i%7==0)){
                if (i%4==0)         D++;
                else if (i%4==1)    A++;
                else if (i%4==2)    B++;
                else if (i%4==3)    C++;
            }else {
                num++;
            }
            i++;
        }
        System.out.println(A);
        System.out.println(B);
        System.out.println(C);
        System.out.println(D);
    }
}
