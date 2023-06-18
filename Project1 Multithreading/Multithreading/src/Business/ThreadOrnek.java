package Business;


import dataAccess.ReadingExcel;
import Business.Business;
import entities.Data;
import java.util.ArrayList;

public class ThreadOrnek implements Runnable{
    Business business = new Business();
    ReadingExcel excelOkuma = new ReadingExcel();
    ToCompare karsilastirma = new ToCompare();
    
 
    public static ArrayList<Data> result = new ArrayList<Data>();
    
    private int sutun;
    private int miktar;
    private ArrayList<Data> veriler;
    
    
    public ThreadOrnek(ArrayList<Data> veriler,int miktar,int sutun) {
        this.veriler = veriler;
        this.miktar = miktar;
        this.sutun = sutun;
    }
    
    
    
    @Override
    public void run() {
        int kontrol;
    
        switch (sutun) {
            case 2:
                for(int i = 0 ; i<veriler.size() ; i++){
                    kontrol = 0;
                    for(int j = i+1 ; j<veriler.size() ; j++){
                        double benzerlik = karsilastirma.fonk(veriler.get(i).getProduct(), veriler.get(j).getProduct());
                        if(benzerlik>=miktar){
                            kontrol++;
                            synchronized (result) {
                                result.add(veriler.get(j));
                            }
                            veriler.remove(j);
                            j--;
                        }
                    }
                    if(kontrol != 0){
                        synchronized (result) {
                            result.add(veriler.get(i));
                        }
                        veriler.remove(i);
                        i--;
                    }
                } 
                break;
            case 3:
                for(int i = 0 ; i<veriler.size() ; i++){
                    kontrol = 0;
                    for(int j = i+1 ; j<veriler.size() ; j++){
                        double benzerlik = karsilastirma.fonk(veriler.get(i).getIssue(), veriler.get(j).getIssue());
                        if(benzerlik>=miktar){
                            kontrol++;
                            synchronized (result) {
                                result.add(veriler.get(j));
                            }
                            veriler.remove(j);
                            j--;
                        }
                    }
                    if(kontrol != 0){
                        synchronized (result) {
                            result.add(veriler.get(i));
                        }
                        veriler.remove(i);
                        i--;
                    }
                }
                break;
                
            case 4:
                for(int i = 0 ; i<veriler.size() ; i++){
                    kontrol = 0;
                    for(int j = i+1 ; j<veriler.size() ; j++){
                        double benzerlik = karsilastirma.fonk(veriler.get(i).getCompany(), veriler.get(j).getCompany());
                        if(benzerlik>=miktar){
                            kontrol++;
                            synchronized (result) {
                                result.add(veriler.get(j));
                            }
                            veriler.remove(j);
                            j--;
                        }
                    }
                    if(kontrol != 0){
                        synchronized (result) {
                            result.add(veriler.get(i));
                        }
                        veriler.remove(i);
                        i--;
                    }
                }
                break;
            default:
                throw new AssertionError();
        }
        
        
    }
}

