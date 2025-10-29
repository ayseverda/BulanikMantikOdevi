package hesaplama;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.rule.Variable;
import java.util.Scanner;

public class SinirlilikSeviyesiHesaplayici {

    public static void main(String[] args) {
        String fileName = "sinirlilik_modeli.fcl"; 
        FIS fis = FIS.load(fileName, true);

        if (fis == null) {
            System.err.println("Hata: FCL dosyası yüklenemedi. Program sonlandırılıyor.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        String choice;

        do {
            System.out.println("\n--- YENİ HESAPLAMA BAŞLIYOR ---");

            // Kullanıcıdan girdileri al
            System.out.print("1. Şeker miktarı (0-30 gr/gün) girin: ");
            double sugar = scanner.nextDouble();

            System.out.print("2. Yaş (0-18 yıl) girin: ");
            double age = scanner.nextDouble();

            System.out.print("3. Cinsiyet (0=Erkek, 1=Kız) girin: ");
            double gender = scanner.nextDouble();
            
            // Satır sonunu temizle (Döngü için önemli)
            scanner.nextLine(); 

            // Fuzzy sisteme değerleri ata
            fis.setVariable("sugar", sugar); 
            fis.setVariable("age", age);
            fis.setVariable("gender", gender);

            // Hesaplama yap
            fis.evaluate();

            // Sonucu al ve ekrana yazdır
            Variable irritabilityVar = fis.getVariable("irritability");
            double irritability = irritabilityVar.getLatestDefuzzifiedValue();
            
            System.out.println("\n--- SONUÇLAR ---");
            System.out.printf("Hesaplanan Sinirlilik Seviyesi: %.2f %%\n", irritability);

            // Çıkış seçeneği
            System.out.println("\nYeni hesaplama yapmak ister misiniz?");
            System.out.print("Yeni hesaplama için 'e', çıkış için 'h' tuşlayın: ");
            choice = scanner.nextLine().trim().toLowerCase();

        } while (choice.equals("e"));

        System.out.println("Program sonlandırıldı. İyi çalışmalar!");
        scanner.close();
    }
}