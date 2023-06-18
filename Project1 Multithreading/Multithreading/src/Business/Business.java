package Business;


import dataAccess.ReadingExcel;
import Frontend.MainMenu;
import entities.Data;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Business {
    public static double sure=0;
    ReadingExcel excelOkuma = new ReadingExcel();
    ToCompare karsilastirma = new ToCompare();
    
    public ArrayList<Data> veriCek(){
        return excelOkuma.verileriCek();
    }
        
    public ArrayList<Data> AramaId(ArrayList<Data> veriler, int id, int sutun, int miktar) {
        ArrayList<Data> veriSeti = veriler;
        ArrayList<Data> ornek = new ArrayList<Data>();
        Data veri = null;

        for (Data data : veriSeti) {
            if (data.getComplaintId() == id) {
                veri = data;
                ornek.add(data);
                break;
            }
        }
        
        if(veri == null){
            return ornek;
        }
        
        long starttime = System.currentTimeMillis();
        
        switch (sutun) {
            case 2:
                for (Data data : veriSeti) {
                    double benzerlik = karsilastirma.fonk(data.getProduct(),veri.getProduct());
                    if(benzerlik >= miktar){
                        ornek.add(data);
                    }
                }
                break;
            case 3:
                for (Data data : veriSeti) {
                    double benzerlik = karsilastirma.fonk(data.getIssue(),veri.getIssue());
                    if(benzerlik >= miktar){
                        ornek.add(data);
                    }
                }
                break;
            case 4:
                for (Data data : veriSeti) {
                    double benzerlik = karsilastirma.fonk(data.getCompany(),veri.getCompany());
                    if(benzerlik >= miktar){
                        ornek.add(data);
                    }
                }
                break;
            case 5:
                for (Data data : veriSeti) {
                    double benzerlik = karsilastirma.fonk(data.getState(),veri.getState());
                    if(benzerlik >= miktar){
                        ornek.add(data);
                    }
                }
                break;
            default:
                throw new AssertionError();
        }
        
        long endtime = System.currentTimeMillis();
        sure = (double)(endtime-starttime)/1000;
        ornek.remove(veri);
        return ornek;
    }

    
    public void AramaGenel(ArrayList<Data> veriler, int threadSayisi,int miktar,int sutun){
        long starttime = System.currentTimeMillis();
        int dongu = veriler.size() / threadSayisi;
        long endtime = 0;
        
        Thread[] threads = new Thread [threadSayisi];
        
        for (int i = 0; i < threadSayisi; i++) {
            ArrayList<Data> ornek = new ArrayList<Data>();
            for(int j = i*dongu ; j<(i+1)*dongu ; j++){
                ornek.add(veriler.get(j));
            }
            Thread thread = new Thread(new ThreadOrnek(ornek, miktar, sutun));
            thread.start();
        }
        
        for(int i = 0 ; i<1000 ; i++){
            try {
                int num1 = ThreadOrnek.result.size();
                Thread.sleep(100);
                int num2 = ThreadOrnek.result.size();

                if(num2-num1 == 0){
                    endtime = System.currentTimeMillis();
                    break;
                }
                num1 = 0;
                num2 = 0;
            } catch (InterruptedException ex) {
                Logger.getLogger(Business.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        sure += (double)(endtime-starttime)/1000;        
    }
        
    
    
}
