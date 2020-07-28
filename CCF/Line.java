import java.util.Scanner;

public class Line {
    public static Object[][] point;
    public static int[][] check_line;
    public static int num;
    public static int check;
    public static String[] result;

    public static void main(String[] args) {
        String[] res;
        getin();
        res = dealing();
        for (int i=0;i<check;i++){
            System.out.println(res[i]);
        }
    }

    public static void getin(){
        Scanner get = new Scanner(System.in);
        num = get.nextInt();
        check = get.nextInt();
        result = new String[check];
        point = new Object[num][3];
        check_line = new int[check][3];

//        point[0][0]=1;point[0][1]=1;point[0][2]="A";
//        point[1][0]=1;point[1][1]=0;point[1][2]="A";
//        point[2][0]=1;point[2][1]=-1;point[2][2]="A";
//        point[3][0]=2;point[3][1]=2;point[3][2]="B";
//        point[4][0]=2;point[4][1]=3;point[4][2]="B";
//        point[5][0]=0;point[5][1]=1;point[5][2]="A";
//        point[6][0]=3;point[6][1]=1;point[6][2]="B";
//        point[7][0]=1;point[7][1]=3;point[7][2]="B";
//        point[8][0]=2;point[8][1]=0;point[8][2]="A";
//        check_line[0][0]=0;check_line[0][1]=2;check_line[0][2]=-3;
//        check_line[1][0]=-3;check_line[1][1]=0;check_line[1][2]=2;
//        check_line[2][0]=-3;check_line[2][1]=1;check_line[2][2]=1;

        for (int i=0;i<num;i++){
            point[i][0] = get.nextInt();
            point[i][1] = get.nextInt();
            point[i][2] = get.next();
        }
        for (int i=0;i<check;i++){
            check_line[i][0] = get.nextInt();
            check_line[i][1] = get.nextInt();
            check_line[i][2] = get.nextInt();
        }
    }

    public static String[] dealing(){
        String[] res = new String[check];
        for (int i=0;i<check;i++){
            int x=0,yy=0, a1=check_line[i][0], a2=check_line[i][1], a3=check_line[i][2];
            float y=0;
            String type = (String) point[0][2];
            yy = (int) point[0][1];
            x = (int) point[0][0];
            y = (float) (-a1-a2*x)/a3;
            boolean pos = (yy - y) > 0;
            for (int j=1;j<num;j++){
                String letter = (String) point[j][2];
                yy = (int) point[j][1];
                x = (int) point[j][0];
                y = (float) (-a1-a2*x)/a3;
                boolean neg = (yy - y) > 0;
                if (pos==neg && type.equals(letter)){
                    res[i] =  "Yes";
                }else if (pos!=neg && !type.equals(letter)){
                    res[i] = "Yes";
                } else {
                    res[i] =  "No";
                    break;
                }
            }
        }
        return res;
    }
}
