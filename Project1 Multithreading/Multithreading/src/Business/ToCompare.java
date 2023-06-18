package Business;


public class ToCompare {
    
    public double fonk(String word1,String word2){
        word1 = word1.toLowerCase().trim();
        word2 = word2.toLowerCase().trim();
        
        String[] array = word1.split(" ");
        String[] array2 = word2.split(" ");
        
        int benzerlik = 0;
        
        for(String kelime : array){
            if(word2.contains(kelime)){
                benzerlik ++;
            }
        }
        
        int uzunluk = 1;
        
        if(array.length > array2.length){
            uzunluk = array.length;
        }else{
            uzunluk = array2.length;
        }
        
        double hesap = 100.0*benzerlik/uzunluk;
        
        return hesap;
    }
    
}
