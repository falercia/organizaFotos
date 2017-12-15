package br.com.groupg.view;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author fabiog
 */
public class FormConfiguracao extends javax.swing.JFrame {

   private final SimpleDateFormat sdfMonthDescription;
   private final SimpleDateFormat sdfYear;
   private final SimpleDateFormat sdfFormatEnglishDate;
   private final int PATH_SOURCE = 1, PATH_DESTINY = 2;

   public FormConfiguracao() {
      initComponents();

      this.setLocationRelativeTo(null);
      this.sdfMonthDescription = new SimpleDateFormat("M.MMMM");
      this.sdfYear = new SimpleDateFormat("yyyy");
      this.sdfFormatEnglishDate = new SimpleDateFormat("yyyy-MM-dd");

      jProgressBarTotal.setVisible(false);
   }

   @Override
   public String getTitle() {
      return "Organizador de Arquivos";
   }

   private String getMonthDescription(Date data) {
      return sdfMonthDescription.format(data);
   }

   private String getYear(Date data) {
      return sdfYear.format(data);
   }

   private void readDir(String path) throws IOException, ParseException {
      String sourceDirName = path.trim().replace("\\", File.separator).replace("/", File.separator);
      String destinyDirName = jTextFieldDestiny.getText().trim().replace("\\", File.separator).replace("/", File.separator);

      File dir = new File(sourceDirName);
      int copied = 0;
      jProgressBarTotal.setMaximum(countFiles(sourceDirName));
      jProgressBarTotal.setVisible(true);

      for (File obj : dir.listFiles()) {
         if (obj.isDirectory()) {
            readDir(obj.getAbsolutePath());
         } else if (obj.isFile()) {
            Path p = Paths.get(obj.getAbsolutePath());
            BasicFileAttributes attrs = Files.readAttributes(p, BasicFileAttributes.class);

            //Como so interessa mes e ano, corta fora o restante da informação...n esta utilizando um SimpleDateFormat para o timezone... achei desnecessário
            String dateOnly = String.valueOf(attrs.lastModifiedTime()).substring(0, String.valueOf(attrs.lastModifiedTime()).indexOf("T"));
            String targetPath = destinyDirName + File.separator + getYear(sdfFormatEnglishDate.parse(dateOnly)) + File.separator + getMonthDescription(sdfFormatEnglishDate.parse(dateOnly)) + File.separator + obj.getName();
            FileUtils.copyFile(obj, new File(targetPath));
            jProgressBarTotal.setValue(++copied);
         }
      }
      jProgressBarTotal.setVisible(false);
   }

   private int countFiles(String path) {
      int total = 0;
      File dir = new File(path);

      for (File obj : dir.listFiles()) {
         if (obj.isDirectory()) {
            countFiles(obj.getAbsolutePath());
         } else if (obj.isFile()) {
            total++;
         }
      }
      return total;
   }

   private void setPath(int filePath) {
      JFileChooser chooserPath;
      if (System.getProperty("os.name").toLowerCase().contains("windows")) {
         chooserPath = new JFileChooser(new File(System.getProperty("user.home") + File.separator + "Desktop"));
      } else {
         chooserPath = new JFileChooser(new File(System.getProperty("user.dir")));
      }

      chooserPath.setDialogTitle("Selecione o diretório");
      chooserPath.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      String path;
      if (chooserPath.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
         path = chooserPath.getSelectedFile().getAbsolutePath();
         File fileTemp = new File(path + File.separator + "tempFile.txt");
         fileTemp.deleteOnExit();
         switch (filePath) {
            case PATH_SOURCE:
               jTextFieldSource.setText(path);
               break;
            case PATH_DESTINY:
               jTextFieldDestiny.setText(path);
               break;
         }
      }
   }

   private void controlComponents(boolean status) {
      jTextFieldSource.setEnabled(status);
      jToggleButtonSource.setEnabled(status);
      jTextFieldDestiny.setEnabled(status);
      jToggleButtonDestiny.setEnabled(status);
      jButtonProcess.setEnabled(status);
   }

   @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      jLabelSource = new javax.swing.JLabel();
      jTextFieldSource = new javax.swing.JTextField();
      jLabelDestiny = new javax.swing.JLabel();
      jTextFieldDestiny = new javax.swing.JTextField();
      jButtonProcess = new javax.swing.JButton();
      jProgressBarTotal = new javax.swing.JProgressBar();
      jToggleButtonDestiny = new javax.swing.JToggleButton();
      jToggleButtonSource = new javax.swing.JToggleButton();

      setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

      jLabelSource.setText("Diretório origem:");

      jLabelDestiny.setText("Diretório destino:");

      jButtonProcess.setText("Processar");
      jButtonProcess.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonProcessActionPerformed(evt);
         }
      });

      jToggleButtonDestiny.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/groupg/resources/search.png"))); // NOI18N
      jToggleButtonDestiny.setBorder(null);
      jToggleButtonDestiny.setBorderPainted(false);
      jToggleButtonDestiny.setContentAreaFilled(false);
      jToggleButtonDestiny.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jToggleButtonDestinyActionPerformed(evt);
         }
      });

      jToggleButtonSource.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/groupg/resources/search.png"))); // NOI18N
      jToggleButtonSource.setBorder(null);
      jToggleButtonSource.setBorderPainted(false);
      jToggleButtonSource.setContentAreaFilled(false);
      jToggleButtonSource.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jToggleButtonSourceActionPerformed(evt);
         }
      });

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(jLabelSource)
               .addComponent(jLabelDestiny))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                  .addComponent(jProgressBarTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                  .addComponent(jButtonProcess))
               .addComponent(jTextFieldDestiny, javax.swing.GroupLayout.Alignment.TRAILING)
               .addComponent(jTextFieldSource))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(jToggleButtonDestiny, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(jToggleButtonSource, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addGroup(layout.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                     .addComponent(jToggleButtonSource, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                     .addComponent(jTextFieldSource, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                     .addComponent(jToggleButtonDestiny, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                     .addComponent(jTextFieldDestiny, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                     .addComponent(jButtonProcess)
                     .addComponent(jProgressBarTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
               .addGroup(layout.createSequentialGroup()
                  .addGap(14, 14, 14)
                  .addComponent(jLabelSource))
               .addGroup(layout.createSequentialGroup()
                  .addGap(40, 40, 40)
                  .addComponent(jLabelDestiny)))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );

      pack();
   }// </editor-fold>//GEN-END:initComponents

   private void jButtonProcessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonProcessActionPerformed
      try {
         new Thread("process") {
            @Override
            public void run() {
               try {
                  controlComponents(false);
                  readDir(jTextFieldSource.getText());
                  JOptionPane.showMessageDialog(FormConfiguracao.this, "Concluído!");
                  controlComponents(true);
               } catch (Exception e) {
                  JOptionPane.showMessageDialog(rootPane, "Erro no processamento:\n" + e, "", JOptionPane.WARNING_MESSAGE);
               }
            }
         }.start();
      } catch (Exception e) {
         JOptionPane.showMessageDialog(this, "Ocorreu uma exceção ao tentar copiar os arquivos:\n" + e.getMessage());
      }
   }//GEN-LAST:event_jButtonProcessActionPerformed

   private void jToggleButtonSourceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonSourceActionPerformed
      setPath(PATH_SOURCE);
   }//GEN-LAST:event_jToggleButtonSourceActionPerformed

   private void jToggleButtonDestinyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonDestinyActionPerformed
      setPath(PATH_DESTINY);
   }//GEN-LAST:event_jToggleButtonDestinyActionPerformed

   /**
    * @param args the command line arguments
    */
   public static void main(String args[]) {
      /* Set the Nimbus look and feel */
      //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
      /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
       */
      try {
         for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
               javax.swing.UIManager.setLookAndFeel(info.getClassName());
               break;
            }
         }
      } catch (ClassNotFoundException ex) {
         java.util.logging.Logger.getLogger(FormConfiguracao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (InstantiationException ex) {
         java.util.logging.Logger.getLogger(FormConfiguracao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (IllegalAccessException ex) {
         java.util.logging.Logger.getLogger(FormConfiguracao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (javax.swing.UnsupportedLookAndFeelException ex) {
         java.util.logging.Logger.getLogger(FormConfiguracao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      }
      //</editor-fold>

      /* Create and display the form */
      java.awt.EventQueue.invokeLater(new Runnable() {
         public void run() {
            new FormConfiguracao().setVisible(true);
         }
      });
   }

   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JButton jButtonProcess;
   private javax.swing.JLabel jLabelDestiny;
   private javax.swing.JLabel jLabelSource;
   private javax.swing.JProgressBar jProgressBarTotal;
   private javax.swing.JTextField jTextFieldDestiny;
   private javax.swing.JTextField jTextFieldSource;
   private javax.swing.JToggleButton jToggleButtonDestiny;
   private javax.swing.JToggleButton jToggleButtonSource;
   // End of variables declaration//GEN-END:variables
}
