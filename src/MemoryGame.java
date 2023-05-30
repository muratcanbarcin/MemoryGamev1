import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.io.FileWriter;


public class MemoryGame {
    //Attributes
    private SingleLinkedList SLL1;
    private SingleLinkedList SLL2;
    private DoubleLinkedList SLL3;
    private Integer PairNumber;
    private Integer Score;
    private  Integer StepNumber;


    public MemoryGame() {  //Constructor
        SLL1 = new SingleLinkedList();
        SLL2 = new SingleLinkedList();
        SLL3 = new SingleLinkedList();
        SLL4 = new SingleLinkedList();
        PairNumber = 0;
        Score = 0;
        StepNumber = 1;
    }

    //Gerekli set() ve get() metotları
    public void setPairNumber(Integer pairNumber) {
        PairNumber = pairNumber;
    }

    public Integer getScore() {
        return Score;
    }

    public Integer getStepNumber() {
        return StepNumber;
    }

    public void setScore(Integer score) {
        Score = score;
    }

    public void setStepNumber(Integer stepNumber) {
        StepNumber = stepNumber;
    }

    //Dosyadan verileri çekip SLL1'e atayan fonksiyon
    public void fileRead(){
        String filePath = "animals.txt";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String Line = reader.readLine();
            SLL1.unsortedAdd(Line);
            while (Line != null) {
                Line = reader.readLine();
                SLL1.unsortedAdd(Line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Oyunun oynanacağı metot
    public void game(){
        fileRead();

        System.out.println("Animal SLL:");
        SLL1.display();

        //İlgili inputları almak için scanner kullanımı
        Scanner scanner = new Scanner(System.in);
        System.out.println(" ");
        System.out.print("Enter the n ( 1-" + SLL1.size() +" ):" );
        int pair = scanner.nextInt();
        scanner.nextLine();
        while (pair>SLL1.size() || pair<=0){
            System.out.println(" ");
            System.out.println("!!!Incorrect Input!!!");
            System.out.print("Enter the n ( 1-" + SLL1.size() +" ):" );
            pair = scanner.nextInt();
            scanner.nextLine();
        }
        System.out.println(" ");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        setPairNumber(pair);
        System.out.println(" ");
        scanner.close();
        //SLL2 oluşturmadan önce SLL1 üzerindeki işlemler
        Random rnd =new Random();
        SingleLinkedList tempSLL = SLL1.copy();
        int[] numbers = new int[SLL1.size()];

        int count =0;
        int index =0;
        boolean flag2 = true;
        //Random bir şekilde SLL2 oluşumu
        while(count < PairNumber){
            flag2 = true;
            int DataNumber = rnd.nextInt(SLL1.size());
            for(int i =0; i<numbers.length;i++){
                if(numbers[i] == DataNumber+1){
                    flag2 = false;
                    break;
                }
            }

            if(flag2){
                Object Data = SLL1.get(DataNumber);
                SLL2.unsortedAdd(Data);
                count++;
                numbers[index] = DataNumber+1;
                index++;
            }

        }
        //Oluşan SLL2'deki elemanların SLL1'de eşi olmayanların silinmesi
        for(int j =0; j< tempSLL.size();j++){
            Boolean flag = SLL2.contains(tempSLL.get(j));

            if (!flag){
                SLL1.delete(tempSLL.get(j));
            }

        }
        //Oyun loopu
        while (SLL1.size() != 0)
        {
            //Tahminleri bilgisayardan alıp otomatik olarak oyunu oynatma ve yazdırma
            int guess1 = rnd.nextInt(PairNumber);
            int guess2 = rnd.nextInt(PairNumber);
            String dataSLL1 = (String) SLL1.get(guess1);
            String dataSLL2 = (String) SLL2.get(guess2);
            guess1 += 1;
            guess2 += 1;
            System.out.print("SLL-1:  ");
            SLL1.display();
            System.out.println("   Score:" + getScore());
            System.out.println(" ");
            System.out.print("SLL-2:  ");
            SLL2.display();
            System.out.println(" ");
            System.out.println(" ");
            System.out.print("Randomly generated numbers:  " + guess1 + " " + guess2);
            System.out.println("   Step:" + getStepNumber());
            int tempScore =getScore();
            //Eğer bulunursa SLL1 ve SLL2'den silme ve score ayarlaması
            if(dataSLL1.equalsIgnoreCase(dataSLL2)){
                SLL1.delete(dataSLL1);
                SLL2.delete(dataSLL2);
                tempScore +=20;
                PairNumber -=1;
                setScore(tempScore);
            }
            else {
                tempScore -=1;
                setScore(tempScore);
            }
            int tempStep = getStepNumber();
            tempStep+=1;
            setStepNumber(tempStep);

            System.out.println(" ");

        }
        //Son output için loop dışında tekrar yazdırma
        System.out.print("SLL-1:  ");
        SLL1.display();
        System.out.println("   Score:" + getScore());
        System.out.println(" ");
        System.out.print("SLL-2:  ");
        SLL2.display();
        System.out.println(" ");
        System.out.println(" ");

        System.out.println("The game is over.");
        System.out.println(" ");



        //HİGH SCORE TABLE

        String filePath = "highscoretable.txt";
        String lineHigh;
        //Split ile metotları ayırıp sonrasında SLL3 ve SLL4'e atamak
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((lineHigh = br.readLine()) != null) {
                String[] highScoreArr = lineHigh.split(" ");
                SLL3.unsortedAdd(highScoreArr[0]);
                SLL4.unsortedAdd(highScoreArr[1]);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Kullanıcıdan alınan verilerin SLL3 ve SLL4'e atanması
        SLL3.unsortedAdd(name);
        String score = Integer.toString(getScore());
        SLL4.unsortedAdd(score);

        SLL4 = SLL4.sort();
        //High Score Table ayarlanması ve yazdırılması
        System.out.println();
        System.out.println("High Score Table:");
        System.out.println();
        displayHighScore();

        //HighScoreTable.txt'e gerekli eklemelerin yapılması
        String fileName = "highscoretable.txt";

        try {
            FileWriter writer = new FileWriter(fileName, true);
            writer.write( "\n");
            writer.write( name + " "+score);
            writer.close();

        } catch (IOException e) {
            System.out.println("Dosya yazılırken bir hata oluştu: " + e.getMessage());
        }

    }


    //High Score Table yazdırılması
    public void displayHighScore(){
        int counter = 0;
        if (SLL4.head ==null){
            System.out.println("Linked list is empty");
        }
        else {
            Node temp = SLL4.head;
            Node temp2 = SLL3.head;
            while (temp != null && counter <13){
                counter++;
                if (temp2.getData() == "" || temp.getData() ==""){
                    System.out.println(" ");
                    break;
                }
                System.out.println(temp2.getData()+ "        " + temp.getData());
                temp = temp.getLink();
                temp2 = temp2.getLink();
            }
        }
    }

}
