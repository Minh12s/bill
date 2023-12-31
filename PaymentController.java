package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Order;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {
    public TextField txtchange;
    public TextField txtpay;
    public Button txt10k;
    @FXML
    public TextField txttotal;
    public Button btnVis;
    public Button btnVnpay;
    public Button btnBack;
    public TextField txtsubtotal;
    private String previousPayValue; // Biến tạm để lưu giá trị txtpay trước khi cập nhật
    private TableView<Order> tbv;
    private String lastPayValue = "";
    @FXML
    private TextArea billTextArea;
    // Date
    public Text txtHours, txtMin, txtSecond, txtDay, txtMonth, txtYear;
    public final LocalDateTime dateTime = LocalDateTime.now();
    int day = dateTime.getDayOfMonth();
    int month = dateTime.getMonthValue();
    int year = dateTime.getYear();
    int hours = dateTime.getHour();
    int minute = dateTime.getMinute();
    int second = dateTime.getSecond();
    //

    public void setTotal(String total) {
        txttotal.setText(total);
        txtsubtotal.setText(total);
    }

    public void btnDeletes(MouseEvent mouseEvent) {
        if(txtpay!=null){
            txtpay.setText("");
        }
    }

    public void btnMoney1(MouseEvent mouseEvent) {
        txtpay.setText("$1");
    }

    public void btnMoney2(MouseEvent mouseEvent) {
        txtpay.setText("$2");
    }

    public void btnMoney3(MouseEvent mouseEvent) {
        txtpay.setText("$5");
    }

    public void btnMoney4(MouseEvent mouseEvent) {
        txtpay.setText("$10");
    }

    public void btnMoney5(MouseEvent mouseEvent) {
        txtpay.setText("$20");
    }

    public void btnMoney6(MouseEvent mouseEvent) {
        txtpay.setText("$50");
    }

    public void btnMoney7(MouseEvent mouseEvent) {
        txtpay.setText("$100");
    }

    public void pay(MouseEvent mouseEvent) {
        String changeText = txtchange.getText();
        String payText = txtpay.getText();
        if (changeText.isEmpty() && payText.isEmpty()) {
            // Hiển thị thông báo khi cả `txtchange` và `txtpay` không có giá trị
            showAlert(Alert.AlertType.WARNING, "Payment Error", "Empty Payment",
                    "Please enter the payment amount.");
        } else if (changeText.isEmpty() && !payText.isEmpty()) {
            // Hiển thị thông báo khi chỉ `txtchange` không có giá trị nhưng `txtpay` có giá trị
            showAlert(Alert.AlertType.WARNING, "Payment Error", "Incomplete Payment",
                    "Let's calculate the excess.");
        } else {
            if (txtpay != null && txttotal != null) {
                String payAmountText = payText.replaceAll("[^\\d.]", "");
                String totalText = txttotal.getText().replaceAll("[^\\d.,]", "").replace(',', '.');

                try {
                    double payAmount = Double.parseDouble(payAmountText);
                    double totalAmount = Double.parseDouble(totalText);

                    if (payAmount < totalAmount) {
                        // Hiển thị cảnh báo nếu số tiền thanh toán không đủ
                        showAlert(Alert.AlertType.WARNING, "Payment Error", "Insufficient Payment",
                                "The amount paid is less than the total amount.");
                    } else {
                        double changeAmount = payAmount - totalAmount;
                        txtchange.setText(String.format("%.2f", changeAmount) + "$");

                        // Hiển thị thông báo thành công
                        showAlert(Alert.AlertType.INFORMATION, "Payment Success", "Payment completed successfully.",
                                "Change amount: $" + String.format("%.2f", changeAmount));
                        // Lưu giá trị của txtpay vào biến tạm khi hoàn tất giao dịch
                        previousPayValue = txtpay.getText();

                        // Chuyển đến trang homepos.fxml
//                        try {
//                            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/homepos.fxml"));
//                            Parent homeParent = loader.load();
//                            Scene homeScene = new Scene(homeParent);
//
//                            Stage window = (Stage) txtpay.getScene().getWindow();
//                            window.setScene(homeScene);
//                            window.show();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }


                        // Lưu giá trị của txtpay vào biến tạm khi hoàn tất giao dịch
                        previousPayValue = txtpay.getText();
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void Number(ActionEvent ae) {
        String no = ((Button) ae.getSource()).getText();
        txtpay.appendText(no);
    }


    public void complete(ActionEvent actionEvent) {
        if (txtpay != null && txttotal != null) {
            String payText = txtpay.getText().replaceAll("[^\\d.]", "");
            String totalText = txttotal.getText().replaceAll("[^\\d.,]", "").replace(',', '.');

            try {
                double payAmount = Double.parseDouble(payText);
                double totalAmount = Double.parseDouble(totalText);
                double changeAmount = payAmount - totalAmount;

                txtchange.setText(String.format("%.2f", changeAmount) + "$");

                // Lưu giá trị của txtpay vào biến lastPayValue khi hoàn tất giao dịch
                lastPayValue = txtpay.getText();

                // Cập nhật giá trị của txtpay
                txtpay.setText(lastPayValue);
                txtpay.setText(lastPayValue + "$");
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }


    public void Vis(ActionEvent actionEvent) {

        btnVis.setStyle("-fx-background-color: #4e2a84; -fx-border-color: #D3D3D3; -fx-border-radius: 6px; -fx-text-fill: white;");
        btnVnpay.setStyle("-fx-background-color: white; -fx-border-color: #D3D3D3; -fx-border-radius: 6px;-fx-text-fill: black;");


    }

    public void Vnpay(ActionEvent actionEvent) {
        btnVnpay.setStyle("-fx-background-color: #4e2a84; -fx-border-color: #D3D3D3; -fx-border-radius: 6px; -fx-text-fill: white;");
        btnVis.setStyle("-fx-background-color:white ; -fx-border-color: #D3D3D3; -fx-border-radius: 6px; -fx-text-fill: black;");
    }


    public void btnDelete(MouseEvent mouseEvent) {
        String currentText = txtpay.getText();
        if (!currentText.isEmpty()) {
            txtpay.setText(currentText.substring(0, currentText.length() - 1));
            lastPayValue = txtpay.getText(); // Cập nhật giá trị của lastPayValue
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Date
        new Thread(()-> {
            boolean flag = true;
            while (flag) {
                txtDay.setText(day + "/");
                txtMonth.setText(month + "/");
                txtYear.setText(String.valueOf(year));
                txtHours.setText(hours + ":");
                txtMin.setText(minute + ":");
                txtSecond.setText(String.valueOf(second));
                second ++;
                if (second > 59) {
                    second = 0;
                    minute++;
                }if(minute > 59) {
                    minute = 0;
                    hours ++;
                }if (hours > 23) {
                    hours = 0;
                    day ++;
                } switch (month) {
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 9:
                    case 11:
                        if (day > 31) {
                            day = 1;
                            month ++;
                        }
                    case 2:
                        if (year % 4 == 0 && year % 100 != 0) {
                            if (day > 29) {
                                day = 1;
                                month ++;
                            }
                        } else {
                            if (day > 28) {
                                day = 1;
                                month ++;
                            }
                        }
                    case 4:
                    case 6:
                    case 8:
                    case 10:
                        if (day > 30) {
                            day = 1;
                            month ++;
                        }
                    case 12:
                        if (day > 30) {
                            day = 1;
                            month = 1;
                            year ++;
                        }
                    default:
                }
                try {
                    Thread.sleep(1000); // 1000 milliseconds
                } catch (Exception e) {
                    System.out.println("Thread Error: " + e.getMessage());
                }
            }
        }).start();
        //

    }
    public void billprint(MouseEvent mouseEvent) {
        String billText = "                              POS Market \n";
        billText += "                    \tSố 18 Tôn Thất Thuyết \n";
        billText += "                       \t+84 123456789 \n";

        // Thêm thông tin ngày giờ
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        billText += "Date/Time:" + formattedDateTime + "\n";
        billText += "----------------------------------------------------------------\n";
        billText += " Name                      \tQty                 \tPrice \n";
        billText += "----------------------------------------------------------------\n";
        // Nhập dữ liệu từ db
   /*   ObservableList<Order> data = tbv.getItems();
      for (int i = 0; i < data.size(); i++) {
          billText += data.get(i).getName() + "\t\t"+ data.get(i).getQty()+ "\t\t\t"+ data.get(i).getPrice() + " \n";
      }*/

        billText += "----------------------------------------------------------------\n";
        billText += "Total :\t" + txttotal.getText() + "\n";
        billText += "Cash :\t" + txtpay.getText() + "\n";
        billText += "Ballance :\t" + txtchange.getText() + "\n";
/*
        // Thêm thông tin tên và ghi chú
        if (name != null && !name.isEmpty()) {
            billText += "Name:\t" + name + "\n";
        }
        if (note != null && !note.isEmpty()) {
            billText += "Note:\t" + note + "\n";
        }
*/

        billText += "======================================\n";
        billText += "                    Thanks For Your Business...!" + "\n";

        // Hiển thị hóa đơn trong TextArea
        billTextArea.setText(billText);

        // In hóa đơn
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null) {
            boolean success = printerJob.printPage(billTextArea);
            if (success) {
                printerJob.endJob();
            }
        }
    }


}