public class Hello {
    public static void main(String[] args) {
        int i = 1;
        int j = 2;
        int r1 = i+j;
        int r2 = i-j;
        int r3 = i*j;
        int r4 = i/j;
        int r5 = i%j;
        if(r2 == -1){
            r1 = i;
        }
        for(int k=0;k<i;k--){
            i++;
        }
    }
}