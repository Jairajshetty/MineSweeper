
package minesweeper;

import java.util.Random;
import java.util.Scanner;

public class Main {
    private static String[][] Mines=new String[9][9];
    private static String[][] MinesCopy=new String[9][9];
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        System.out.print("How many mines do you want on the field? ");
        int num=scanner.nextInt();


        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                Mines[i][j]=".";
                MinesCopy[i][j]=".";
            }
        }
        PrintMines(MinesCopy);
//        OutputModifiedMines(num);


        while (finishedCondition(num).equals("inProgress")){
            System.out.print("Set/unset mines marks or claim a cell as free:");
            int col=scanner.nextInt();
            int row=scanner.nextInt();
            String task=scanner.next();
            switch (task){
                case "free":FreeFunction(row-1,col-1,num);
                break;
                case "mine":MineFunction(row-1,col-1);
                break;
                default:break;
            }

        }
        if(finishedCondition(num).equals("lost")){
            for(int i=0;i<9;i++){
                for(int j=0;j<9;j++){
                    if(Mines[i][j].equals("X")){
                        MinesCopy[i][j]="X";
                    }
                }
            }
            PrintMines(MinesCopy);
            System.out.println("You stepped on a mine and failed!");
        }else{
            PrintMines(MinesCopy);
            System.out.println("Congratulations! You found all mines!");
        }

    }

    public static void randomMines(int num,int row,int col ){
        int NumberOfMines=num;
//        Random rand=new Random();
//        while(NumberOfMines>0){
//            int RandomNum1=rand.nextInt(9);
//            int RandomNum2=rand.nextInt(9);
//            if(Mines[RandomNum1][RandomNum2].equals(".")){
//                Mines[RandomNum1][RandomNum2]="X";
//                NumberOfMines--;
//            }
//        }
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(NumberOfMines==0){
                    break;
                }else if(i!=row&&j!=col){
                    Mines[i][j]="X";
                    NumberOfMines--;
                }
            }
        }
    }
    public static void PrintMines(String[][] Mines ){
        System.out.println(" |123456789|");
        System.out.println("-|---------|");
        for(int i=0;i<9;i++){
            System.out.print((i+1)+"|");
            for(int j=0;j<9;j++){
                    System.out.print(Mines[i][j]);
            }
            System.out.println("|");
        }
        System.out.println("-|---------|");
    }

    public static void lookAround(){
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(Mines[i][j].equals(".")){
                    if(MinesAround(i,j)>0){
                        Mines[i][j]=String.valueOf(MinesAround(i,j));
                    }else{
                        Mines[i][j]="/";
                    }
                }
            }
        }
    }

    public static int MinesAround(int row,int col){
        int count=0;
        for(int i=row-1;i<row+2;i++){
            for(int j=col-1;j<col+2;j++){
                if(i>=0&&i<9&&j>=0&&j<9){
                    if(Mines[i][j].equals("X")){
                        count++;
                    }
                }
            }
        }
        return count;
    }
    public static String finishedCondition(int num){
        int countMarked=0;
        int countFree=0;
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(MinesCopy[i][j].equals("X")){
                    return "lost";
                }
                if(MinesCopy[i][j].equals("*")){
                    countMarked++;
                }
                if(MinesCopy[i][j].equals(".")){
                    countFree++;
                }
            }
        }

        if(countMarked==num&&countFree==0){
            return "won";
        }else if(countMarked==0&&countFree==num){
            return "won";
        }else{
            return "inProgress";
        }
    }
    public static void MineFunction(int row,int col){
        if(MinesCopy[row][col].equals(".")){
            MinesCopy[row][col]="*";
        }else if(MinesCopy[row][col].equals("*")){
            MinesCopy[row][col]=".";
        }
        PrintMines(MinesCopy);
    }
    public static void FreeFunction(int row,int col,int num){

        boolean first=true;
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(!MinesCopy[i][j].equals(".")&&!MinesCopy[i][j].equals("*")){
                    first=false;
                    break;
                }
            }
        }

        if(first==true){

            OutputModifiedMines(num,row,col);
            PerformFree(row,col);
            PrintMines(MinesCopy);

        }else{
            PerformFree(row,col);
            PrintMines(MinesCopy);
        }
    }

    public static void OutputModifiedMines(int num,int row,int col){
        randomMines(num,row,col);
        lookAround();
    }

    public static void PerformFree(int row,int col){
       if(Mines[row][col].equals("/")) {
            FreeFunctionality(row,col);
        }else{
            MinesCopy[row][col]=Mines[row][col];
        }

    }
    public static void FreeFunctionality(int row,int col){
        for(int i=row-1;i<row+2;i++){
            for(int j=col-1;j<col+2;j++){
                if(i>=0&&i<9&&j>=0&&j<9){
                    if(!Mines[i][j].equals("/")){
                        MinesCopy[i][j]=Mines[i][j];
                    }else if(Mines[i][j].equals("/")&&(MinesCopy[i][j].equals(".")||MinesCopy[i][j].equals("*"))){
                        MinesCopy[i][j]=Mines[i][j];
                        FreeFunctionality(i,j);
                    }

                }
            }
        }

    }
}
